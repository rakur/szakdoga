package hu.unideb.inf.szakdoga.converters;

import hu.unideb.inf.szakdoga.game.CellType;
import hu.unideb.inf.szakdoga.game.Field;
import hu.unideb.inf.szakdoga.game.ShipType;
import hu.unideb.inf.szakdoga.model.Game;
import hu.unideb.inf.szakdoga.model.GameEntity;
import org.springframework.core.convert.converter.Converter;
import java.util.LinkedList;

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
                playerOneUnplacedShip.push(ShipType.valueOf(ship));
        }

        for (String ship : playerTwoUnplacedShipsEntity.replaceAll("(\\[|\\])", "").split(", ")) {
            if (!ship.equals(""))
                playerTwoUnplacedShip.push(ShipType.valueOf(ship));
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
                .build();
    }

    public Game convertToPlayerTwoPlacing(GameEntity gameEntity) {
        Game rawGame = this.convert(gameEntity);
        return Game.builder()
                .gameState(rawGame.getGameState())
                .playerTwoUnplacedShips(rawGame.getPlayerTwoUnplacedShips())
                .playerTwoField(rawGame.getPlayerTwoField())
                .build();
    }

    public Game convertToPlayerOneShooting(GameEntity gameEntity) {
        Game rawGame = this.convert(gameEntity);
        CellType[][] map = rawGame.getPlayerTwoField().getMap();
        for (int i = 0; i < 10; i++) {
            for (int j=0; j < 10; j++) {
                if (map[i][j] != CellType.WATER && map[i][j] != CellType.MISS && map[i][j] != CellType.HIT) {
                    map[i][j] = CellType.WATER;
                }
            }
        }

        rawGame.getPlayerTwoField().setMap(map);
        return Game.builder()
                .gameState(rawGame.getGameState())
                .playerTwoField(rawGame.getPlayerTwoField())
                .build();
    }

    public Game convertToPlayerTwoShooting(GameEntity gameEntity) {
        Game rawGame = this.convert(gameEntity);
        CellType[][] map = rawGame.getPlayerOneField().getMap();
        for (int i = 0; i < 10; i++) {
            for (int j=0; j < 10; j++) {
                if (map[i][j] != CellType.WATER && map[i][j] != CellType.MISS && map[i][j] != CellType.HIT) {
                    map[i][j] = CellType.WATER;
                }
            }
        }

        rawGame.getPlayerOneField().setMap(map);
        return Game.builder()
                .gameState(rawGame.getGameState())
                .playerOneField(rawGame.getPlayerOneField())
                .build();
    }
}
