package hu.unideb.inf.szakdoga.converters;

import hu.unideb.inf.szakdoga.model.CellType;
import hu.unideb.inf.szakdoga.model.Field;
import hu.unideb.inf.szakdoga.model.ShipType;
import hu.unideb.inf.szakdoga.model.Game;
import hu.unideb.inf.szakdoga.model.GameEntity;
import org.springframework.core.convert.converter.Converter;

import java.util.*;

import static hu.unideb.inf.szakdoga.model.CellType.CARRIER;

public class GameEntityToGameConverter implements Converter<GameEntity, Game>{
    @Override
    public Game convert(GameEntity gameEntity) {
        String[] playerOneFieldEntity = gameEntity.getPlayerOneField().replaceAll("(\\[|\\])", "").split(", ");
        String[] playerTwoFieldEntity = gameEntity.getPlayerTwoField().replaceAll("(\\[|\\])", "").split(", ");
        String playerOneUnplacedShipsEntity = gameEntity.getPlayerOneUnplacedShips();
        String playerTwoUnplacedShipsEntity = gameEntity.getPlayerTwoUnplacedShips();
        CellType[][] playerOneField = new CellType[10][10];
        CellType[][] playerTwoField = new CellType[10][10];
        LinkedList<ShipType> playerOneUnplacedShip = new LinkedList<>();
        LinkedList<ShipType> playerTwoUnplacedShip = new LinkedList<>();
        Field fieldOne = new Field();
        Field fieldTwo = new Field();

        for (String ship : playerOneUnplacedShipsEntity.replaceAll("(\\[|\\])", "").split(", ")) {
            if (!ship.equals(""))
                playerOneUnplacedShip.add(ShipType.valueOf(ship));
        }

        for (String ship : playerTwoUnplacedShipsEntity.replaceAll("(\\[|\\])", "").split(", ")) {
            if (!ship.equals(""))
                playerTwoUnplacedShip.add(ShipType.valueOf(ship));
        }

        for (int i=0;i<10;i++) {
            for (int j=0;j<10;j++) {
                playerOneField[i][j] = CellType.valueOf(playerOneFieldEntity[i*10+j]);
            }
        }

        fieldOne.setMap(playerOneField);

        for (int i=0;i<10;i++) {
            for (int j=0;j<10;j++) {
                playerTwoField[i][j] = CellType.valueOf(playerTwoFieldEntity[i*10+j]);
            }
        }

        fieldTwo.setMap(playerTwoField);

        return Game.builder()
                .id(gameEntity.getId())
                .gameState(gameEntity.getGameState())
                .playerOneField(fieldOne)
                .playerTwoField(fieldTwo)
                .playerOneUnplacedShips(playerOneUnplacedShip)
                .playerTwoUnplacedShips(playerTwoUnplacedShip)
                .build();
    }

    public Game convertToPlayerOnePlacing(GameEntity gameEntity) {
        Game rawGame = this.convert(gameEntity);
        return Game.builder()
                .gameState(rawGame.getGameState())
                .playerOneUnplacedShips(rawGame.getPlayerOneUnplacedShips())
                .playerOneField(rawGame.getPlayerOneField())
                .playerTwoUnplacedShips(rawGame.getPlayerTwoUnplacedShips())
                .build();
    }

    public Game convertToPlayerTwoPlacing(GameEntity gameEntity) {
        Game rawGame = this.convert(gameEntity);
        return Game.builder()
                .gameState(rawGame.getGameState())
                .playerOneUnplacedShips(rawGame.getPlayerOneUnplacedShips())
                .playerTwoUnplacedShips(rawGame.getPlayerTwoUnplacedShips())
                .playerTwoField(rawGame.getPlayerTwoField())
                .build();
    }

    public Game convertToPlayerOneShooting(GameEntity gameEntity) {
        Game rawGame = this.convert(gameEntity);
        CellType[][] map = rawGame.getPlayerTwoField().getMap();
        CellType[][] otherMap = rawGame.getPlayerOneField().getMap();
        LinkedHashSet<String> playerOneRemaining = new LinkedHashSet<>();
        LinkedHashSet<String> playerTwoRemaining = new LinkedHashSet<>();
        for (int i = 0; i < 10; i++) {
            for (int j=0; j < 10; j++) {
                switch (map[i][j]) {
                    case CARRIER:
                        playerTwoRemaining.add("Carrier");
                        break;
                    case BATTLESHIP:
                        playerTwoRemaining.add("BattleShip");
                        break;
                    case CRUISER:
                        playerTwoRemaining.add("Cruiser");
                        break;
                    case SUBMARINE:
                        playerTwoRemaining.add("Submarine");
                        break;
                    case DESTROYER:
                        playerTwoRemaining.add("Destroyer");
                        break;
                }
                switch (otherMap[i][j]) {
                    case CARRIER:
                        playerOneRemaining.add("Carrier");
                        break;
                    case BATTLESHIP:
                        playerOneRemaining.add("BattleShip");
                        break;
                    case CRUISER:
                        playerOneRemaining.add("Cruiser");
                        break;
                    case SUBMARINE:
                        playerOneRemaining.add("Submarine");
                        break;
                    case DESTROYER:
                        playerOneRemaining.add("Destroyer");
                        break;
                }
                if (map[i][j] != CellType.WATER && map[i][j] != CellType.MISS && map[i][j] != CellType.HIT) {
                    map[i][j] = CellType.WATER;
                }
            }
        }

        rawGame.getPlayerTwoField().setMap(map);
        return Game.builder()
                .gameState(rawGame.getGameState())
                .playerOneField(rawGame.getPlayerOneField())
                .playerTwoField(rawGame.getPlayerTwoField())
                .playerOneRemainingShips(playerOneRemaining)
                .playerTwoRemainingShips(playerTwoRemaining)
                .build();
    }

    public Game convertToPlayerTwoShooting(GameEntity gameEntity) {
        Game rawGame = this.convert(gameEntity);
        CellType[][] map = rawGame.getPlayerOneField().getMap();
        CellType[][] otherMap = rawGame.getPlayerTwoField().getMap();
        int playerOneHits = 0;
        int playerTwoHits = 0;
        LinkedHashSet<String> playerOneRemaining = new LinkedHashSet<>();
        LinkedHashSet<String> playerTwoRemaining = new LinkedHashSet<>();
        for (int i = 0; i < 10; i++) {
            for (int j=0; j < 10; j++) {switch (map[i][j]) {
                case CARRIER:
                    playerOneRemaining.add("Carrier");
                    break;
                case BATTLESHIP:
                    playerOneRemaining.add("BattleShip");
                    break;
                case CRUISER:
                    playerOneRemaining.add("Cruiser");
                    break;
                case SUBMARINE:
                    playerOneRemaining.add("Submarine");
                    break;
                case DESTROYER:
                    playerOneRemaining.add("Destroyer");
                    break;
            }
                switch (otherMap[i][j]) {
                    case CARRIER:
                        playerTwoRemaining.add("Carrier");
                        break;
                    case BATTLESHIP:
                        playerTwoRemaining.add("BattleShip");
                        break;
                    case CRUISER:
                        playerTwoRemaining.add("Cruiser");
                        break;
                    case SUBMARINE:
                        playerTwoRemaining.add("Submarine");
                        break;
                    case DESTROYER:
                        playerTwoRemaining.add("Destroyer");
                        break;
                }
                if (map[i][j] != CellType.WATER && map[i][j] != CellType.MISS && map[i][j] != CellType.HIT) {
                    map[i][j] = CellType.WATER;
                }
                if (map[i][j] == CellType.HIT)
                    playerTwoHits++;
                if (otherMap[i][j] == CellType.HIT)
                    playerOneHits++;
            }
        }

        rawGame.getPlayerOneField().setMap(map);
        return Game.builder()
                .gameState(rawGame.getGameState())
                .playerOneField(rawGame.getPlayerOneField())
                .playerTwoField(rawGame.getPlayerTwoField())
                .playerOneRemainingShips(playerOneRemaining)
                .playerTwoRemainingShips(playerTwoRemaining)
                .build();
    }
}
