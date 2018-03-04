package hu.unideb.inf.szakdoga.service;

import hu.unideb.inf.szakdoga.game.GameState;
import hu.unideb.inf.szakdoga.model.Game;
import hu.unideb.inf.szakdoga.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class GameServiceImpl implements GameService{
    @Autowired
    GameRepository gameRepository;

    @Override
    public List<Game> getAll() {
        return gameRepository.findAll();
    }

    @Override
    public void create() {
        gameRepository.save(new Game(GameState.PLAYER_ONE_PLACING));
    }

}
