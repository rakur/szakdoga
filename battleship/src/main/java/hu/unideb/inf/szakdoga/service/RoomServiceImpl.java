package hu.unideb.inf.szakdoga.service;

import hu.unideb.inf.szakdoga.model.Room;
import hu.unideb.inf.szakdoga.model.RoomState;
import hu.unideb.inf.szakdoga.model.Users;
import hu.unideb.inf.szakdoga.repository.RoomRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static hu.unideb.inf.szakdoga.model.RoomState.FULL;
import static hu.unideb.inf.szakdoga.model.RoomState.NEW;

@Log4j
@Component
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UsersService usersService;

    @Autowired
    private UserService userService;

    @Autowired
    private GameService gameService;


    @Override
    public void createRoom() {
        Long ownerId = userService.getCurrentUser().getId();
        roomRepository.save(Room.builder().ownerId(ownerId).ownerReady(false).userReady(false).roomState(NEW).build());
    }

    @Override
    public void joinRoom(Long roomId) {
        Room room = roomRepository.getRoomById(roomId);
        if (room.getRoomState() == NEW) {
            Long userId = userService.getCurrentUser().getId();
            roomRepository.updateUserIdByRoomId(roomId, userId, FULL);
        }
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAllByRoomState(NEW);
    }

    @Override
    public void quit() {
        Users users = userService.getCurrentUser();
        log.info("user that wants to exit: " + users.getUsername());
        Room room = roomRepository.findRoomByOwnerIdOrUserId(users.getId(), users.getId());
        log.info("room which they want to quit from "+room.getId());
        if (users.getId().equals(room.getUserId())){
            roomRepository.updateUserIdByRoomId(room.getId(), null, NEW);
        }
        if (users.getId().equals(room.getOwnerId())){
            roomRepository.delete(room.getId());
        }
    }

    @Override
    public Room getRoom() {
        Long id = userService.getCurrentUser().getId();
        return roomRepository.findRoomByOwnerIdOrUserId(id,id);
    }

    @Override
    public void toggleReady() {
        Users users = userService.getCurrentUser();
        Room room = roomRepository.findRoomByOwnerIdOrUserId(users.getId(), users.getId());
        if (users.getId().equals(room.getUserId())) {
            roomRepository.updateUserReadyByRoomId(room.getId(), !room.getUserReady());
        }
        else {
            roomRepository.updateOwnerReadyByRoomId(room.getId(), !room.getOwnerReady());
        }
        room = roomRepository.findRoomByOwnerIdOrUserId(users.getId(), users.getId());
        if (room.getUserReady() && room.getOwnerReady() && room.getRoomState() == FULL) {
            startGame();
        }
    }

    private void startGame() {
        Users users = userService.getCurrentUser();
        Room room = roomRepository.findRoomByOwnerIdOrUserId(users.getId(), users.getId());
        roomRepository.updateRoomStateByRoomId(room.getId());
        gameService.create();
    }
}
