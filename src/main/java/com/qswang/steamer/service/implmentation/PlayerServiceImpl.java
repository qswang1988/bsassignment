package com.qswang.steamer.service.implmentation;

import com.alibaba.fastjson.JSON;
import com.qswang.steamer.controller.GamingController;
import com.qswang.steamer.memcached.MemcachedService;
import com.qswang.steamer.memcached.structure.GamingStatus;
import com.qswang.steamer.memcached.structure.OnlinePlayer;
import com.qswang.steamer.model.Game;
import com.qswang.steamer.model.Level;
import com.qswang.steamer.model.Player;
import com.qswang.steamer.model.Region;
import com.qswang.steamer.repository.GameRepository;
import com.qswang.steamer.repository.PlayerRepository;
import com.qswang.steamer.repository.RegionRepository;
import com.qswang.steamer.service.PlayerService;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@Service
public class PlayerServiceImpl implements PlayerService {

    private static final Logger logger = LoggerFactory.getLogger(PlayerServiceImpl.class);

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private MemcachedService memcachedService;

    @Override
    public int insertPlayer(Player player) {
        playerRepository.saveAndFlush(player);
        return HttpStatus.OK.value();
    }

    @Override
    public int updatePlayer(Player player) {
        playerRepository.saveAndFlush(player);
        return HttpStatus.OK.value();
    }

    @Override
    public int deletePlayer(Long id) {
        playerRepository.deleteById(id);
        return HttpStatus.OK.value();
    }

    @Override
    public Optional<Player> findById(long id) {
        return playerRepository.findById(id);
    }

    @Override
    public int updateLastLink(Long playerId, int gameId) {
        Optional<Player> player = findById(playerId);
        if(player.isEmpty())
            return HttpStatus.NOT_FOUND.value();
        Optional<Game> game = gameRepository.findById(gameId);
        if(game.isEmpty())
            return HttpStatus.NOT_FOUND.value();

        Player player1 = player.get();
        player1.setLastLinkGame(game.get().getGameId());
        player1.setLastLinkDate(Calendar.getInstance().getTime());
        playerRepository.saveAndFlush(player1);

        return HttpStatus.OK.value();
    }

    // oneline
    @Override
    public List<Player> findOnlinePlayerByRegionLevelGame(Game game, Region region, Level level) {
        List<Player> players = playerRepository.findByRegionLevelGame(region.getRegionId(), game.getGameId(), level.getLevelId());
        Iterator<Player> it = players.iterator();
        List<Player> list = new ArrayList<>();
        logger.info("size found before filtering by memcached: "+players.size());
        while(it.hasNext()){

            Player p = it.next();
            logger.info("checking with memcached: "+p.getPlayerId());

            Optional<OnlinePlayer> onlinePlayer = (Optional<OnlinePlayer>) memcachedService.fetchRecord(String.valueOf(p.getPlayerId()));
            logger.info("op: \n"+ JSON.toJSONString(onlinePlayer.get()));
            if(onlinePlayer.isEmpty()) {
                logger.info("playerId: "+p.getPlayerId()+" not in memcached");
                continue;
            }else if(onlinePlayer.get().getGameId()!=game.getGameId()) {
                logger.info("playerId: "+p.getPlayerId()+" game id not mached");
                continue;
            }else if(!onlinePlayer.get().getStatus().equals(GamingStatus.WAITING_MATCH.toString())) {
                logger.info("playerId: "+p.getPlayerId()+" game status not matched");
                continue;
            }
            list.add(p);
        }
        logger.info("size found after filter by memcached: "+list.size());
        return list;
    }

    @Override
    public List<Player> findPlayerByLevelGame(Game game, Level level) {
        List<Player> players = playerRepository.findByLevelGame(game.getGameId(),level.getLevelId());
        for(Player p:players){
            Optional<Region> r = regionRepository.findById(p.getRegionId());
            if(r.isPresent())
                p.setRegionName(r.get().getRegionName());
        }
        return players;
    }

    @Override
    public List<Player> findAllPlayers() {
        return playerRepository.findAll();
    }


}
