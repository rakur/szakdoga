package hu.unideb.inf.szakdoga.model;

import hu.unideb.inf.szakdoga.exceptions.InvalidPlacingPositionException;
import hu.unideb.inf.szakdoga.exceptions.InvalidShootingPositionException;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.LinkedList;

@Builder
@Data
public class Game implements Serializable {

    private Long id;
    private GameState gameState;
    private Field playerOneField;
    private Field playerTwoField;
    private LinkedList<ShipType> playerOneUnplacedShips;
    private LinkedList<ShipType> playerTwoUnplacedShips;

    public void placeShip(int x, int y, boolean vertical, boolean isFirstPlayerPlacing) throws InvalidPlacingPositionException {
        if (gameState == GameState.PLAYERS_PLACING) {
            if (isFirstPlayerPlacing) {
                playerOneField.place(playerOneUnplacedShips.getFirst(), x, y, vertical);
                playerOneUnplacedShips.removeFirst();
            } else {
                playerTwoField.place(playerTwoUnplacedShips.getFirst(), x, y, vertical);
                playerTwoUnplacedShips.removeFirst();
            }
        }
        this.switchTurn();
    }

    public boolean shoot(int x, int y) throws InvalidShootingPositionException {
        boolean shootResult;
        if (gameState == GameState.PLAYER_ONE_SHOOTING) {
            shootResult = playerTwoField.shoot(x, y);
        } else {
            shootResult = playerOneField.shoot(x, y);
        }
        this.switchTurn();
        return shootResult;
    }

    private void switchTurn() {
        switch (this.gameState) {
            case PLAYERS_PLACING:
                if (playerOneUnplacedShips.isEmpty() && playerTwoUnplacedShips.isEmpty()) {
                    gameState = GameState.PLAYER_ONE_SHOOTING;
                }
                break;
            case PLAYER_ONE_SHOOTING:
                if (playerTwoField.isAllBoatsSunken()) {
                    gameState = GameState.PLAYER_ONE_WON;
                } else {
                    gameState = GameState.PLAYER_TWO_SHOOTING;
                }
                break;
            case PLAYER_TWO_SHOOTING:
                if (playerOneField.isAllBoatsSunken()) {
                    gameState = GameState.PLAYER_TWO_WON;
                } else {
                    gameState = GameState.PLAYER_ONE_SHOOTING;
                }
                break;
        }
    }

}