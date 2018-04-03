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
                )))
                .playerTwoUnplacedShips(new LinkedList<>(Arrays.asList(
                        ShipType.CARRIER,
                        ShipType.BATTLESHIP,
                        ShipType.CRUISER,
                        ShipType.SUBMARINE,
                        ShipType.DESTROYER
                ))).build();

        GameToGameEntityConverter converter = new GameToGameEntityConverter();
        GameEntity gameEntity = gameRepository.save(converter.convert(game));
        long id = userService.getCurrentUser().getId();
        Room room = roomRepository.findRoomByOwnerIdOrUserId(id,id);
        roomRepository.updateGameIdByRoomId(gameEntity.getId(), room.getId());
    }

    @Override
    public Game getGame() throws NullPointerException {/*
        Long gameId = roomService.getRoom().getGameId();
        return gameRepository.getGameById(gameId);*/
        GameEntityToGameConverter gameEntityToGameConverter = new GameEntityToGameConverter();
        long userId = userService.getCurrentUser().getId();
        Room room = roomRepository.findRoomByOwnerIdOrUserId(userId,userId);
        GameEntity gameEntity = gameRepository.getGameById(room.getGameId());
        if (gameEntity.getGameState() == GameState.PLAYERS_PLACING) {
            if (room.getOwnerId() == userId)
                return gameEntityToGameConverter.convertToPlayerOnePlacing(gameEntity);
            else
                return gameEntityToGameConverter.convertToPlayerTwoPlacing(gameEntity);
        }
        else {
            if (room.getOwnerId() == userId)
                return gameEntityToGameConverter.convertToPlayerOneShooting(gameEntity);
            else
                return gameEntityToGameConverter.convertToPlayerTwoShooting(gameEntity);
        }
    }

    @Override
    public void placeShip(int x, int y, boolean isVertical) throws InvalidPlacingPositionException {
        GameEntityToGameConverter gameEntityToGameConverter = new GameEntityToGameConverter();
        GameToGameEntityConverter gameToGameEntityConverter = new GameToGameEntityConverter();
        long userId = userService.getCurrentUser().getId();
        Room room = roomRepository.findRoomByOwnerIdOrUserId(userId,userId);
        Game game = gameEntityToGameConverter.convert(gameRepository.getGameById(room.getGameId()));
        if (userId == room.getOwnerId()) {
            game.placeShip(x, y, isVertical, true);
            GameEntity gameEntity = gameToGameEntityConverter.convert(game);
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
        long userId = userService.getCurrentUser().getId();
        Room room = roomRepository.findRoomByOwnerIdOrUserId(userId,userId);
        Game game = gameEntityToGameConverter.convert(gameRepository.getGameById(room.getGameId()));
        if (userId == room.getOwnerId() && game.getGameState() == GameState.PLAYER_ONE_SHOOTING) {
            game.shoot(x, y);
            GameEntity gameEntity = gameToGameEntityConverter.convert(game);
            gameRepository.updatePlayerTwoField2(gameEntity.getId(), gameEntity.getPlayerTwoField(),
                    gameEntity.getGameState());
        }
        if (userId == room.getUserId() && game.getGameState() == GameState.PLAYER_TWO_SHOOTING){
            game.shoot(x, y);
            GameEntity gameEntity = gameToGameEntityConverter.convert(game);
            gameRepository.updatePlayerOneField2(gameEntity.getId(), gameEntity.getPlayerOneField(),
                    gameEntity.getGameState());
        }
    }

    @Override
    public void finish() {
        long userId = userService.getCurrentUser().getId();
        Room room = roomRepository.findRoomByOwnerIdOrUserId(userId,userId);
        GameEntity gameEntity = gameRepository.getGameById(room.getGameId());
        if (gameEntity.getGameState() != GameState.PLAYER_ONE_WON && gameEntity.getGameState() != GameState.PLAYER_TWO_WON) {
            if (room.getOwnerId() == userId) {
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