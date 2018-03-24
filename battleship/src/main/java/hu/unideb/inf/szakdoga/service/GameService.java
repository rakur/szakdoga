package hu.unideb.inf.szakdoga.service;

import hu.unideb.inf.szakdoga.model.Game;
import hu.unideb.inf.szakdoga.model.GameEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GameService {
    List<GameEntity> getAll();
    void create();
}
