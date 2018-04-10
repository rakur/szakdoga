package hu.unideb.inf.szakdoga.service;

import hu.unideb.inf.szakdoga.converters.GameEntityToGameConverter;
import hu.unideb.inf.szakdoga.converters.GameToGameEntityConverter;
import hu.unideb.inf.szakdoga.exceptions.InvalidPlacingPositionException;
import hu.unideb.inf.szakdoga.exceptions.InvalidShootingPositionException;
import hu.unideb.inf.szakdoga.model.*;
import hu.unideb.inf.szakdoga.model.Game;
import hu.unideb.inf.szakdoga.repository.GameRepository;
import hu.unideb.inf.szakdoga.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Component
public class GameServiceImpl implements GameService{
    @Autowired
    GameRepository gameRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    UserService userService;

    @Autowired
    RoomService roomService;

    @Override
    public List<GameEntity> getAll() {
        return gameRepository.findAll();
    }

    @Override
    public void create() {

        LinkedList<ShipType> unplacedShips = new LinkedList<>();
        unplacedShips.push(ShipType.CARRIER);
        unplacedShips.push(ShipType.BATTLESHIP);
        unplacedShips.push(ShipType.CRUISER);
        unplacedShips.push(ShipType.SUBMARINE);
        unplacedShips.push(ShipType.DESTROYER);

        Game game = Game.builder()
                .gameState(GameState.PLAYERS_PLACING)
                .playerOneField(new Field())
                .playerTwoField(new Field())
                .playerOneUnplacedShips(new LinkedList<>(Arrays.asList(
                        ShipType.CARRIER,
                        ShipType.BATTLESHIP,
                        ShipType.CRUISER,
                        ShipType.SUBMARINE,
                        ShipType.DESTROYER
                        //unplacedShips
                )))
                .playerTwoUnplacedShips(new LinkedList<>(Arrays.asList(
                        ShipType.CARRIER,
                        ShipType.BATTLESHIP,
                        ShipType.CRUISER,
                        ShipType.SUBMARINE,
                        ShipType.DESTROYER
                        //unplacedShips
                ))).build();

        GameToGameEntityConverter converter = new GameToGameEntityConverter();
        GameEntity gameEntity = gameRepository.save(converter.convert(game));
        String userName = userService.getCurrentUser().getUsername();
        Room room = roomRepository.findRoomByOwnerNameOrUserName(userName,userName);
        roomRepository.updateGameIdByRoomId(gameEntity.getId(), room.getId());
    }

    @Override
    public Game getGame() throws NullPointerException {
        GameEntityToGameConverter gameEntityToGameConverter = new GameEntityToGameConverter();
        String userName = userService.getCurrentUser().getUsername();
        Room room = roomRepository.findRoomByOwnerNameOrUserName(userName,userName);
        GameEntity gameEntity = gameRepository.getGameById(room.getGameId());
        if (gameEntity.getGameState() == GameState.PLAYERS_PLACING) {
            if (room.getOwnerName().equals(userName))
                return gameEntityToGameConverter.convertToPlayerOnePlacing(gameEntity);
            else
                return gameEntityToGameConverter.convertToPlayerTwoPlacing(gameEntity);
        }
        else {
            if (room.getOwnerName().equals(userName))
                return gameEntityToGameConverter.convertToPlayerOneShooting(gameEntity);
            else
                return gameEntityToGameConverter.convertToPlayerTwoShooting(gameEntity);
        }
    }

    @Override
    public void placeShip(int x, int y, boolean isVertical) throws InvalidPlacingPositionException {
        GameEntityToGameConverter gameEntityToGameConverter = new GameEntityToGameConverter();
        GameToGameEntityConverter gameToGameEntityConverter = new GameToGameEntityConverter();
        String userName = userService.getCurrentUser().getUsername();
        Room room = roomRepository.findRoomByOwnerNameOrUserName(userName, userName);
        Game game = gameEntityToGameConverter.convert(gameRepository.getGameById(room.getGameId()));
        if (room.getOwnerName().equals(userName)) {
            System.out.println(game.getPlayerOneUnplacedShips());
            game.placeShip(x, y, isVertical, true);
            GameEntity gameEntity = gameToGameEntityConverter.convert(game);
            System.out.println(gameEntity.getPlayerOneUnplacedShips());
            gameRepository.updatePlayerOneField(gameEntity.getId(), gameEntity.getPlayerOneField(),
                    gameEntity.getPlayerOneUnplacedShips(), gameEntity.getGameState());
        }
        else {
            game.placeShip(x, y, isVertical, false);
            GameEntity gameEntity = gameToGameEntityConverter.convert(game);
            gameRepository.updatePlayerTwoField(gameEntity.getId(), gameEntity.getPlayerTwoField(),
                    gameEntity.getPlayerTwoUnplacedShips(), gameEntity.getGameState());
        }
    }

    @Override
    public void shoot(int x, int y) throws InvalidShootingPositionException {
        GameEntityToGameConverter gameEntityToGameConverter = new GameEntityToGameConverter();
        GameToGameEntityConverter gameToGameEntityConverter = new GameToGameEntityConverter();
        String userName = userService.getCurrentUser().getUsername();
        Room room = roomRepository.findRoomByOwnerNameOrUserName(userName, userName);
        Game game = gameEntityToGameConverter.convert(gameRepository.getGameById(room.getGameId()));
        if (room.getOwnerName().equals(userName) && game.getGameState() == GameState.PLAYER_ONE_SHOOTING) {
            game.shoot(x, y);
            GameEntity gameEntity = gameToGameEntityConverter.convert(game);
            gameRepository.updatePlayerTwoField2(gameEntity.getId(), gameEntity.getPlayerTwoField(),
                    gameEntity.getGameState());
        }
        if (room.getUserName().equals(userName) && game.getGameState() == GameState.PLAYER_TWO_SHOOTING){
            game.shoot(x, y);
            GameEntity gameEntity = gameToGameEntityConverter.convert(game);
            gameRepository.updatePlayerOneField2(gameEntity.getId(), gameEntity.getPlayerOneField(),
                    gameEntity.getGameState());
        }
    }

    @Override
    public void finish() {
        String userName = userService.getCurrentUser().getUsername();
        Room room = roomRepository.findRoomByOwnerNameOrUserName(userName, userName);
        GameEntity gameEntity = gameRepository.getGameById(room.getGameId());
        if (gameEntity.getGameState() != GameState.PLAYER_ONE_WON && gameEntity.getGameState() != GameState.PLAYER_TWO_WON) {
            if (room.getOwnerName().equals(userName)) {
                gameRepository.updateGameState(gameEntity.getId(), GameState.PLAYER_TWO_WON);
            }
            else {
                gameRepository.updateGameState(gameEntity.getId(), GameState.PLAYER_ONE_WON);
            }
        }
        else {
            gameRepository.delete(gameEntity.getId());
            roomRepository.delete(room.getId());
        }
    }
}