package hu.unideb.inf.szakdoga.service;

import hu.unideb.inf.szakdoga.game.InvalidPlacingPositionException;
import hu.unideb.inf.szakdoga.game.InvalidShootingPositionException;
import hu.unideb.inf.szakdoga.model.Game;
import hu.unideb.inf.szakdoga.model.GameEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GameService {
    List<GameEntity> getAll();
    void create();
    Game getGame();
    void placeShip(int x, int y, boolean isVertical) throws InvalidPlacingPositionException;
    void shoot(int x, int y) throws InvalidShootingPositionException;
    void finish();
}
