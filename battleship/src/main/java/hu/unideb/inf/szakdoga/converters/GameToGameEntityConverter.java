package hu.unideb.inf.szakdoga.converters;

import hu.unideb.inf.szakdoga.model.Game;
import hu.unideb.inf.szakdoga.model.GameEntity;
import org.springframework.core.convert.converter.Converter;

import java.util.Arrays;

public class GameToGameEntityConverter implements Converter<Game,GameEntity>{
    @Override
    public GameEntity convert(Game game) {
        String playerOneField = Arrays.deepToString(game.getPlayerOneField().getMap());
        String playerTwoField = Arrays.deepToString(game.getPlayerTwoField().getMap());
        String playerOneUnplacedShips = Arrays.toString(game.getPlayerOneUnplacedShips().toArray());
        String playerTwoUnplacedShips = Arrays.toString(game.getPlayerTwoUnplacedShips().toArray());
        return GameEntity.builder()
                .id(game.getId())
                .gameState(game.getGameState())
                .playerOneField(playerOneField)
                .playerTwoField(playerTwoField)
                .playerOneUnplacedShips(playerOneUnplacedShips)
                .playerTwoUnplacedShips(playerTwoUnplacedShips)
                .build();

    }
}
