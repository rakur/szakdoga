/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.szakdoga.model;

import hu.unideb.inf.szakdoga.game.InvalidPlacingPositionException;
import hu.unideb.inf.szakdoga.game.InvalidShootingPositionException;
import hu.unideb.inf.szakdoga.game.Field;
import hu.unideb.inf.szakdoga.game.ShipType;
import hu.unideb.inf.szakdoga.game.GameState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CollectionType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;

/**
 *The representation of the board game of Battleship.
 *
 * @author Rakur
 */
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

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", gameState=" + gameState +
                '}';
    }
}