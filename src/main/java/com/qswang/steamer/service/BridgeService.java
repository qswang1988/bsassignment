package com.qswang.steamer.service;

import com.qswang.steamer.model.Game;
import com.qswang.steamer.model.Level;
import com.qswang.steamer.model.Player;
import com.qswang.steamer.model.PlayerGameLevelBridge;

import java.util.List;
import java.util.Optional;

public interface BridgeService {
    public int insertRecord(Player player,Game game);
    public int insertRecord(Player player, Game game, Level level, int score);
    public int updateScore(Player player, Game game, int score);
    public int deleteRecordById(Long id);
    public List<PlayerGameLevelBridge> findRecordByGamePlayer(int gameId, long playerId);
    public void deletePlayerGameLevelBridgeByPlayerId(long playerId);
}
