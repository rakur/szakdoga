package hu.unideb.inf.szakdoga.service;

import hu.unideb.inf.szakdoga.exceptions.InvalidPlacingPositionException;
import hu.unideb.inf.szakdoga.exceptions.InvalidShootingPositionException;
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
