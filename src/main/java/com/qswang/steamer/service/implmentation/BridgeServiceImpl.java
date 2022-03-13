package com.qswang.steamer.service.implmentation;

import com.qswang.steamer.model.Game;
import com.qswang.steamer.model.Level;
import com.qswang.steamer.model.Player;
import com.qswang.steamer.model.PlayerGameLevelBridge;
import com.qswang.steamer.repository.BridgeRepository;
import com.qswang.steamer.repository.GameRepository;
import com.qswang.steamer.repository.PlayerRepository;
import com.qswang.steamer.service.BridgeService;
import com.qswang.steamer.service.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BridgeServiceImpl implements BridgeService {

    @Autowired
    private BridgeRepository bridgeRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private LevelService levelService;

    @Value("${steamer.initial-score}")
    private int initialScore;

    @Override
    public int insertRecord(Player player, Game game) {
        List<PlayerGameLevelBridge> pglb = findRecordByGamePlayer(game.getGameId(),player.getPlayerId());
        if(!pglb.isEmpty())
            return HttpStatus.EXPECTATION_FAILED.value();
        PlayerGameLevelBridge playerGameLevelBridge = new PlayerGameLevelBridge();
        playerGameLevelBridge.setPlayerId(player.getPlayerId());
        playerGameLevelBridge.setGameId(game.getGameId());
        playerGameLevelBridge.setScore(initialScore);
        playerGameLevelBridge.setLevelId(levelService.getLevel(initialScore).getLevelId());
        bridgeRepository.saveAndFlush(playerGameLevelBridge);
        return HttpStatus.OK.value();
    }


    public int insertRecord(Player player, Game game, Level level, int score) {
        List<PlayerGameLevelBridge> pglb = findRecordByGamePlayer(game.getGameId(),player.getPlayerId());
        if(!List.of().isEmpty())
            return HttpStatus.EXPECTATION_FAILED.value();
        PlayerGameLevelBridge playerGameLevelBridge = new PlayerGameLevelBridge();
        playerGameLevelBridge.setPlayerId(player.getPlayerId());
        playerGameLevelBridge.setGameId(game.getGameId());
        playerGameLevelBridge.setScore(score);
        playerGameLevelBridge.setLevelId(level.getLevelId());
        bridgeRepository.saveAndFlush(playerGameLevelBridge);
        return HttpStatus.OK.value();
    }

    @Override
    public int updateScore(Player player, Game game, int score) {
        return HttpStatus.OK.value();
    }

    @Override
    public int deleteRecordById(Long id) {
        bridgeRepository.deleteById(id);
        return HttpStatus.OK.value();
    }

    @Override
    public List<PlayerGameLevelBridge> findRecordByGamePlayer(int gameId, long playerId) {
        Optional<Game> game = gameRepository.findById(gameId);
        if(game.isEmpty())
            return new ArrayList<PlayerGameLevelBridge>();
        Optional<Player> player = playerRepository.findById(playerId);
        if(player.isEmpty())
            return new ArrayList<PlayerGameLevelBridge>();
        return bridgeRepository.findByPlayerIdAndGameId(player.get().getPlayerId(), game.get().getGameId());
    }

    @Override
    public void deletePlayerGameLevelBridgeByPlayerId(long playerId) {
        bridgeRepository.deletePlayerGameLevelBridgeByPlayerId(playerId);
    }

}
