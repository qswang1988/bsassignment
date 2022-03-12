package com.qswang.steamer.config;

import com.qswang.steamer.controller.GamingController;
import com.qswang.steamer.memcached.MemcachedService;
import com.qswang.steamer.model.Game;
import com.qswang.steamer.model.Level;
import com.qswang.steamer.model.Player;
import com.qswang.steamer.model.Region;
import com.qswang.steamer.service.*;
import com.qswang.steamer.service.config.LevelsConfig;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.Random;

@Configuration
public class InitializeSteamer {

    @Autowired
    MemcachedService memcachedService;
    @Autowired
    BridgeService bridgeService;
    @Autowired
    GameService gameService;
    @Autowired
    LevelService levelService;
    @Autowired
    PlayerService playerService;
    @Autowired
    RegionService regionService;
    @Autowired
    LevelsConfig levelsConfig;
    @Autowired
    GamingController gamingController;

    @PostConstruct
    public String init() {
        insertLevels();
        inserRegions();
        insertGames();
        insertPlayers();
        insertBridges();
        insertOnlinePlayers();
        return "INIT SUCCESS";
    }

    public void insertLevels(){
        levelService.insertLevel(new Level("noob",0,1000));
        levelService.insertLevel(new Level("pro",1000,2000));
        levelService.insertLevel(new Level("invincible",2000,3000));
        levelService.insertLevel(new Level("god",3000,Integer.MAX_VALUE));
    }

    public void inserRegions(){
        regionService.insertRegion(new Region("Sweden"));
        regionService.insertRegion(new Region("Finland"));
        regionService.insertRegion(new Region("Denmark"));
        regionService.insertRegion(new Region("Norway"));
        regionService.insertRegion(new Region("Iceland"));
    }

    public void insertGames(){
        gameService.insertGame(new Game("Age Of Empire II",2,8));
        gameService.insertGame(new Game("FIFA 2021",2,2));
        gameService.insertGame(new Game("Mahjong",4,4));
    }

    public void insertPlayers(){
        for(int i = 0;i<=300;i++){
            String accountName = RandomStringUtils.randomAlphanumeric(10);
            int index = new Random().nextInt(5)+1;
            Player p = new Player(accountName,index);
            playerService.insertPlayer(p);
        }
    }

    public void insertBridges(){
        Random random = new Random();
        for(int i = 0;i<500;i++){
            Optional<Player> p = playerService.findById(random.nextInt(300)+1);
            Optional<Game> g = gameService.findById(random.nextInt(4));
            if(p.isEmpty()||g.isEmpty())
                continue;
            int score = random.nextInt(4000);
            Level level = levelService.getLevel(score);
            bridgeService.insertRecord(p.get(),g.get(),level,score);
        }
    }

    public void insertOnlinePlayers(){
        memcachedService.clear();
        Random random = new Random();
        for(int i = 0;i<300;i++){
            int gameId = random.nextInt(3)+1;
            gamingController.linkToGame(gameId,random.nextInt(300)+1);
        }

    }

}
