package hu.unideb.inf.szakdoga.repository;

import hu.unideb.inf.szakdoga.model.GameState;
import hu.unideb.inf.szakdoga.model.GameEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GameRepository extends CrudRepository<GameEntity, Long> {
    GameEntity getGameById(Long id);
    List<GameEntity> findAll();

    @Transactional
    void deleteGameById(long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("Update GameEntity g set g.playerOneField = :playerOneField," +
            " g.playerOneUnplacedShips = :playerOneUnplacedShips, g.gameState = :gameState WHERE g.id = :gameId")
    void updatePlayerOneField(@Param("gameId") Long gameId, @Param("playerOneField") String playerOneField,
                              @Param("playerOneUnplacedShips") String playerOneUnplacedShips,
                              @Param("gameState") GameState gameState);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("Update GameEntity g set g.playerTwoField = :playerTwoField," +
            " g.playerTwoUnplacedShips = :playerTwoUnplacedShips, g.gameState = :gameState WHERE g.id = :gameId")
    void updatePlayerTwoField(@Param("gameId") Long gameId, @Param("playerTwoField") String playerTwoField,
                              @Param("playerTwoUnplacedShips") String playerTwoUnplacedShips,
                              @Param("gameState") GameState gameState);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("Update GameEntity g set g.playerOneField = :playerOneField, g.gameState = :gameState WHERE g.id = :gameId")
    void updatePlayerOneField2(@Param("gameId") Long gameId, @Param("playerOneField") String playerOneField,
                              @Param("gameState") GameState gameState);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("Update GameEntity g set g.playerTwoField = :playerTwoField, g.gameState = :gameState WHERE g.id = :gameId")
    void updatePlayerTwoField2(@Param("gameId") Long gameId, @Param("playerTwoField") String playerOneField,
                              @Param("gameState") GameState gameState);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("Update GameEntity g set g.gameState = :gameState where g.id = :gameId")
    void updateGameState(@Param("gameId") Long gameId, @Param("gameState") GameState gameState);

}
