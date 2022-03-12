package com.qswang.steamer.service.implmentation;

import com.qswang.steamer.model.Level;
import com.qswang.steamer.repository.LevelRepository;
import com.qswang.steamer.service.LevelService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Setter
@Getter
@Service
public class LevelServiceImpl implements LevelService {

    private LevelRepository levelRepository;

    @Autowired
    public LevelServiceImpl(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    @Override
    public int insertLevel(Level level) {
        levelRepository.saveAndFlush(level);
        return HttpStatus.OK.value();
    }

    @Override
    public int updateLevel(Level level) {
        levelRepository.saveAndFlush(level);
        return HttpStatus.OK.value();
    }

    @Override
    public int deleteLevel(int level) {
        Optional<Level> optionalLevel = levelRepository.findById(level);
        if(optionalLevel.isEmpty())
            return HttpStatus.NOT_FOUND.value();
        levelRepository.delete(optionalLevel.get());
        return HttpStatus.OK.value();
    }

    @Override
    public Optional<Level> findById(int id) {
        return levelRepository.findById(id);
    }

    @Override
    public Level getLevel(int score) {
        List<Level> levels = levelRepository.findAll();
        for(Level level:levels){
            if(score>=level.getScoreBegin()&&score<level.getScoreEnd()){
                return level;
            }
        }
        return levels.get(0);
    }


}
