package hu.unideb.inf.szakdoga.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Builder
@Data
@Table(name = "Game")
@AllArgsConstructor
public class GameEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "gameState")
    @Enumerated(EnumType.STRING)
    private GameState gameState;


    @Column(name = "playerOneField", length = 1000)
    private String playerOneField;


    @Column(name = "playerTwoField", length = 1000)
    private String playerTwoField;

    @Column(name = "playerOneUnplacedShips")
    private String playerOneUnplacedShips;

    @Column(name = "playerTwoUnplacedShips")
    private String playerTwoUnplacedShips;

    protected GameEntity() {}

    public GameEntity(GameState gameState) {
        this.gameState = gameState;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

}