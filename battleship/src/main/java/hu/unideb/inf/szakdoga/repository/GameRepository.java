package hu.unideb.inf.szakdoga.repository;

import hu.unideb.inf.szakdoga.model.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends CrudRepository<Game, Long> {
    Game findGameById(Long id);
    List<Game> findAll();
}
