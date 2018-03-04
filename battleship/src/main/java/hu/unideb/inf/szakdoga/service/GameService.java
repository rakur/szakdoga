package hu.unideb.inf.szakdoga.service;

import hu.unideb.inf.szakdoga.model.Game;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GameService {
    List<Game> getAll();
    void create();
}
