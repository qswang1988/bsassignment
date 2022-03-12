package com.qswang.steamer.service;

import com.qswang.steamer.model.Game;

import java.util.Optional;

public interface GameService {
    public int insertGame(Game game);
    public int deleteGame(int id);
    public Optional<Game> findById(int id);
}
