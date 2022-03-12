package com.qswang.steamer.controller;

import com.alibaba.fastjson.JSON;
import com.qswang.steamer.memcached.MemcachedService;
import com.qswang.steamer.memcached.structure.OnlinePlayer;
import com.qswang.steamer.memcached.structure.GamingStatus;
import com.qswang.steamer.model.*;
import com.qswang.steamer.service.*;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Optional;

@Api(tags = "gaming controller")
@RestController
@RequestMapping("/gaming")
public class GamingController {

    private static final Logger logger = LoggerFactory.getLogger(GamingController.class);
    private BridgeService bridgeService;
    private GameService gameService;
    private PlayerService playerService;
    private MemcachedService memcachedService;
    private RegionService regionService;
    private LevelService levelService;

    @ApiOperation("A player requests for linking to a game. It's the 1st step for a user to match mates and play this game")
    @ApiResponses({
            @ApiResponse(code=200,message="success"),
            @ApiResponse(code=204,message="game or player does not exist in system")
    })
    @PostMapping("linkgame/{gameid}")
    public ResponseEntity<String> linkToGame(@PathVariable(value = "gameid") int gameId, @RequestParam(value = "player") long playerId){
        Optional<Game> game = gameService.findById(gameId);
        if(game.isEmpty()){
            logger.error("game not found: "+gameId);
            return new ResponseEntity<>("game not found",HttpStatus.NO_CONTENT);
        }
        Optional<Player> player = playerService.findById(playerId);
        if(player.isEmpty()) {
            logger.error("player not found: "+playerId);
            return new ResponseEntity<>("player not found",HttpStatus.NO_CONTENT);
        }
        List<PlayerGameLevelBridge> bridge = bridgeService.findRecordByGamePlayer(gameId,playerId);

        // in case the user link to a game for the 1st time
        if(bridge.isEmpty()) {
            bridgeService.insertRecord(player.get(),game.get());
            bridge = bridgeService.findRecordByGamePlayer(gameId,playerId);
        }

        Level level = levelService.findById(bridge.get(0).getLevelId()).get();
        // memcahced
        Optional<Region> regionOptional = regionService.findById(player.get().getRegionId());
        if(regionOptional.isEmpty()) {
            logger.info("region not found: "+player.get().getRegionId());
            return new ResponseEntity<>("region not found",HttpStatus.NO_CONTENT);
        }
        Region region = regionOptional.get();
        OnlinePlayer cachedObject = new OnlinePlayer(
                playerId,
                player.get().getAccountName(),
                gameId,
                game.get().getGameName(),
                region.getRegionId(),
                region.getRegionName(),
                level.getLevelId(),
                level.getLevelName(),
                GamingStatus.WAITING_MATCH.toString()
        );
        memcachedService.createRecord(String.valueOf(playerId), cachedObject);
        // update link in db
        playerService.updateLastLink(playerId,gameId);
        logger.info("player game linked: "+playerId + ","+gameId);
        return ResponseEntity.ok("success");
    }

    // for matching
    @ApiOperation("search for players who are currently linked to the game and in waiting for match, search is based on level, region, game")
    @ApiResponses({
            @ApiResponse(code=200,message="Json string of a list containing Players"),
            @ApiResponse(code=204,message="searching conditions are out of range")
    })
    @GetMapping("{game}/onlineplayers/{region}")
    public ResponseEntity<String> searchOnlinePlayers(@PathVariable(name="game") int gameId, @PathVariable(name="region") int regionId, @RequestParam(name="level") int levelId){
        logger.info(String.format("searching online players, gameId: %s, regionId: %s, levelId: %s",gameId,regionId,levelId));
        Optional<Game> game = gameService.findById(gameId);
        Optional<Region> region  = regionService.findById(regionId);
        Optional<Level> level  = levelService.findById(levelId);
        if(level.isEmpty()||game.isEmpty()||region.isEmpty())
            return new ResponseEntity<>("searching conditions are out of range",HttpStatus.NO_CONTENT);
        List<Player> onlinePlayers = playerService.findOnlinePlayerByRegionLevelGame(game.get(),region.get(),level.get());
        return ResponseEntity.ok(JSON.toJSONString(onlinePlayers));
    }

    @ApiOperation("search for players based on level and game no matter if the player is online and which region the user is in")
    @ApiResponses({
            @ApiResponse(code=200,message="Json string of a list containing Players"),
            @ApiResponse(code=204,message="searching condition are out of range")
    })
    @GetMapping("{game}/players")
    public ResponseEntity<String> searchPlayers(@PathVariable(name="game") int gameId, @RequestParam(name="level") int levelId) {
        logger.info(String.format("searching players by game:%s and level:%s",gameId,levelId));
        Optional<Game> game = gameService.findById(gameId);
        Optional<Level> level  = levelService.findById(levelId);
        if(level.isEmpty()||game.isEmpty())
            return new ResponseEntity<>("searching condition are out of range",HttpStatus.NO_CONTENT);
        List<Player> players = playerService.findPlayerByLevelGame(game.get(),level.get());
        return ResponseEntity.ok(JSON.toJSONString(players));
    }

    @ApiIgnore
    @GetMapping("/rejection")
    public int rejection(){
        return HttpStatus.BAD_REQUEST.value();
    }

    @Autowired
    public void setBridgeService(BridgeService bridgeService) {
        this.bridgeService = bridgeService;
    }

    @Autowired
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    @Autowired
    public void setPlayerService(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Autowired
    public void setMemcachedService(MemcachedService memcachedService) {
        this.memcachedService = memcachedService;
    }

    @Autowired
    public void setRegionService(RegionService regionService) {
        this.regionService = regionService;
    }

    @Autowired
    public void setLevelService(LevelService levelService) {
        this.levelService = levelService;
    }
}


