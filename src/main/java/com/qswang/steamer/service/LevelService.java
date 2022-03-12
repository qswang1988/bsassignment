package com.qswang.steamer.service;

import com.qswang.steamer.model.Level;

import java.util.Optional;

public interface LevelService {
    public int insertLevel(Level level);
    public int updateLevel(Level level);
    public int deleteLevel(int id);
    public Optional<Level> findById(int id);
    public Level getLevel(int score);
}
