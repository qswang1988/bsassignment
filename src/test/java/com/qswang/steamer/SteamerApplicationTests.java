package com.qswang.steamer;

import com.alibaba.fastjson.JSON;
import com.qswang.steamer.memcached.MemcachedService;
import com.qswang.steamer.memcached.structure.OnlinePlayer;
import com.qswang.steamer.model.*;
import com.qswang.steamer.service.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class SteamerApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(SteamerApplicationTests.class);

    @Autowired
    private MockMvc mvc;
    @Autowired
    private BridgeService bridgeService;
    @Autowired
    private GameService gameService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private MemcachedService memcachedService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private LevelService levelService;

    @BeforeEach
    void clean(){
        logger.info("cleaning before test");
        List<Player> allPlayers = playerService.findAllPlayers();
        for(Player p:allPlayers){
            bridgeService.deletePlayerGameLevelBridgeByPlayerId(p.getPlayerId());
            p.setLastLinkGame(-1);
            p.setLastLinkDate(null);
            playerService.updatePlayer(p);
            //memcachedService.deleteRecord(String.valueOf(p.getPlayerId()));
        }
        memcachedService.clear();
    }


    // link gamer to a game
    @Test
    void testLinkingToGame() throws Exception {
        Random random = new Random();
        Optional<Player> p = playerService.findById(random.nextInt(300)+1);
        long playerId = p.get().getPlayerId();
        Assertions.assertTrue(p.isPresent());
        Assertions.assertTrue(p.get().getLastLinkGame() == -1);
        Assertions.assertNull(p.get().getLastLinkDate());
        Optional<OnlinePlayer> onlinePlayer = (Optional<OnlinePlayer>) memcachedService.fetchRecord(String.valueOf(playerId));
        Assertions.assertTrue(onlinePlayer.isEmpty());
        int gameId = 2;
        Assertions.assertTrue(bridgeService.findRecordByGamePlayer(gameId,playerId).isEmpty());
        String linkRequest = "/gaming/linkgame/"+gameId+"?player="+playerId;
        mvc.perform(post(linkRequest));
        Assertions.assertFalse(bridgeService.findRecordByGamePlayer(gameId,playerId).isEmpty());
        onlinePlayer = (Optional<OnlinePlayer>) memcachedService.fetchRecord(String.valueOf(playerId));
        Assertions.assertFalse(onlinePlayer.isEmpty());
        Assertions.assertEquals(onlinePlayer.get().getPlayerId(),playerId);
        Assertions.assertEquals(onlinePlayer.get().getGameId(),gameId);
    }

    // search for online players who are waiting for matching based on region, level, game
    @Test
    void testFindingOnlineGamersByGameRegionLevel() throws Exception {
        Random random = new Random();
        Optional<Game> game = gameService.findById(random.nextInt(3)+1);
        Optional<Region> region = regionService.findById(random.nextInt(5)+1);
        int gameId = game.get().getGameId();
        int regionId = region.get().getRegionId();
        Level level = null;
        List<Player> players = new ArrayList<>();
        Set<Long> inserted = new HashSet<>();
        for(int i = 0;i<50;i++){
            Optional<Player> p = playerService.findById(random.nextInt(300)+1);
            if(p.isEmpty() || inserted.contains(p.get().getPlayerId()))
                continue;
            int score = 1000 + random.nextInt(1000);
            level = levelService.getLevel(score);
            bridgeService.insertRecord(p.get(),game.get(),level,score);
            String linkRequest = "/gaming/linkgame/"+gameId+"?player="+p.get().getPlayerId();
            mvc.perform(post(linkRequest));
            players.add(p.get());
            inserted.add(p.get().getPlayerId());
        }
        String request = String.format("/gaming/%s/onlineplayers/%s?level=%s",gameId,regionId,level.getLevelId());
        String result = mvc.perform(get(request)).andReturn().getResponse().getContentAsString();
        logger.info(result);
        List<Player> foundPlayers = JSON.parseArray(result,Player.class);
        Assertions.assertTrue(foundPlayers.size()<=players.size()); // region filter
        for(Player fp:foundPlayers){
            Assertions.assertEquals(fp.getRegionId(),regionId);
            Assertions.assertEquals(fp.getLastLinkGame(),gameId);
            PlayerGameLevelBridge pglb = bridgeService.findRecordByGamePlayer(gameId, fp.getPlayerId()).get(0);
            Assertions.assertEquals(pglb.getLevelId(),level.getLevelId());
        }
        int numOfPlayersWithinRegion = 0;
        for(Player fp:players){
            if(fp.getRegionId() == regionId) {
                logger.info(fp.getAccountName());
                numOfPlayersWithinRegion++;
            }
        }
        Assertions.assertEquals(numOfPlayersWithinRegion,foundPlayers.size());
    }


    // search for players based on specific level and game.
    @Test
    void testFindingGamersByGameAndLevel() throws Exception {
        Random random = new Random();
        Optional<Game> game = gameService.findById(random.nextInt(3)+1);
        int gameId = game.get().getGameId();
        Level level = null;
        List<Player> players = new ArrayList<>();
        Set<Long> inserted = new HashSet<>();
        for(int i = 0;i<50;i++){
            Optional<Player> p = playerService.findById(random.nextInt(300)+1);
            if(p.isEmpty() || inserted.contains(p.get().getPlayerId()))
                continue;
            int score = 1000 + random.nextInt(1000);
            level = levelService.getLevel(score);
            bridgeService.insertRecord(p.get(),game.get(),level,score);
            String linkRequest = "/gaming/linkgame/"+gameId+"?player="+p.get().getPlayerId();
            mvc.perform(post(linkRequest));
            players.add(p.get());
            inserted.add(p.get().getPlayerId());
        }
        String request = String.format("/gaming/%s/players?level=%s",gameId,level.getLevelId());
        String result = mvc.perform(get(request)).andReturn().getResponse().getContentAsString();
        logger.info(result);
        List<Player> foundPlayers = JSON.parseArray(result,Player.class);
        Assertions.assertEquals(players.size(),foundPlayers.size()); // region filter
        for(Player fp:foundPlayers){
            Assertions.assertEquals(fp.getLastLinkGame(),gameId);
            PlayerGameLevelBridge pglb = bridgeService.findRecordByGamePlayer(gameId, fp.getPlayerId()).get(0);
            Assertions.assertEquals(pglb.getLevelId(),level.getLevelId());
        }
    }

}
