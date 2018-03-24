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
        String[] playerOneFieldEntity = gameEntity.getPlayerOneField().split(",");
        String[] playerTwoFieldEntity = gameEntity.getPlayerTwoField().split(",");
        String playerOneUnplacedShipsEntity = gameEntity.getPlayerOneUnplacedShips();
        String playerTwoUnplacedShipsEntity = gameEntity.getPlayerTwoUnplacedShips();
        CellType[][] playerOneField = new CellType[10][10];
        CellType[][] playerTwoField = new CellType[10][10];
        LinkedList<ShipType> playerOneUnplacedShip = new LinkedList<>();
        LinkedList<ShipType> playerTwoUnplacedShip = new LinkedList<>();
        Field fieldOne = new Field();
        Field fieldTwo = new Field();

        for (String ship : playerOneUnplacedShipsEntity.split(",")) {
            playerOneUnplacedShip.push(ShipType.valueOf(ship));
        }

        for (String ship : playerTwoUnplacedShipsEntity.split(",")) {
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
}
