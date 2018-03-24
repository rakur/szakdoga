package hu.unideb.inf.szakdoga.service;

import hu.unideb.inf.szakdoga.converters.GameToGameEntityConverter;
import hu.unideb.inf.szakdoga.game.GameState;
import hu.unideb.inf.szakdoga.model.Game;
import hu.unideb.inf.szakdoga.model.GameEntity;
import hu.unideb.inf.szakdoga.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GameServiceImpl implements GameService{
    @Autowired
    GameRepository gameRepository;

    @Override
    public List<GameEntity> getAll() {
        return gameRepository.findAll();
    }

    @Override
    public void create() {
        Game game = new Game(GameState.PLAYERS_PLACING);
        GameToGameEntityConverter converter = new GameToGameEntityConverter();
        gameRepository.save(converter.convert(game));
    }


}