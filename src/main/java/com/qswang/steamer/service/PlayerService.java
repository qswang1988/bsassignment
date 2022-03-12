package com.qswang.steamer.service;

import com.qswang.steamer.model.Game;
import com.qswang.steamer.model.Level;
import com.qswang.steamer.model.Player;
import com.qswang.steamer.model.Region;

import java.util.List;
import java.util.Optional;

public interface PlayerService {
    public int insertPlayer(Player player);
    public int updatePlayer(Player player);
    public int deletePlayer(Long id);
    public Optional<Player> findById(long id);
    public int updateLastLink(Long playerId,int gameId);
    List<Player> findOnlinePlayerByRegionLevelGame(Game game, Region region, Level level);
    List<Player> findPlayerByLevelGame(Game game, Level level);
    List<Player> findAllPlayers();

}
