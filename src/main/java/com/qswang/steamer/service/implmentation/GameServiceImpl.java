package com.qswang.steamer.service.implmentation;

import com.qswang.steamer.model.Game;
import com.qswang.steamer.repository.GameRepository;
import com.qswang.steamer.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {

    private GameRepository gameRepository;

    @Override
    public int insertGame(Game game) {
        gameRepository.saveAndFlush(game);
        return HttpStatus.OK.value();
    }

    @Override
    public int deleteGame(int id) {
        Optional<Game> gameOptional = gameRepository.findById(id);
        if(gameOptional.isEmpty())
            return HttpStatus.NOT_FOUND.value();
        gameRepository.deleteById(id);
        return HttpStatus.OK.value();
    }

    @Override
    public Optional<Game> findById(int id) {
        return gameRepository.findById(id);
    }

    public GameRepository getGameRepository() {
        return gameRepository;
    }

    @Autowired
    public void setGameRepository(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }
}
