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
    private UserService userService;

    @Autowired
    private GameService gameService;


    @Override
    public void createRoom() {
        String ownerName = userService.getCurrentUser().getUsername();
        roomRepository.save(Room.builder().ownerName(ownerName).ownerReady(false).userReady(false).roomState(NEW).build());
    }

    @Override
    public void joinRoom(Long roomId) {
        Room room = roomRepository.getRoomById(roomId);
        if (room.getRoomState() == NEW) {
            String userName = userService.getCurrentUser().getUsername();
            roomRepository.updateUserNameByRoomId(roomId, userName, FULL);
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
        Room room = roomRepository.findRoomByOwnerNameOrUserName(users.getUsername(), users.getUsername());
        log.info("room which they want to quit from "+room.getId());
        if (users.getUsername().equals(room.getUserName())){
            roomRepository.updateUserNameByRoomId(room.getId(), null, NEW);
        }
        if (users.getUsername().equals(room.getOwnerName())){
            roomRepository.delete(room.getId());
        }
    }

    @Override
    public Room getRoom() {
        String name = userService.getCurrentUser().getUsername();
        return roomRepository.findRoomByOwnerNameOrUserName(name,name);
    }

    @Override
    public void toggleReady() {
        Users users = userService.getCurrentUser();
        Room room = roomRepository.findRoomByOwnerNameOrUserName(users.getUsername(), users.getUsername());
        if (users.getUsername().equals(room.getUserName())) {
            roomRepository.updateUserReadyByRoomId(room.getId(), !room.getUserReady());
        }
        else {
            roomRepository.updateOwnerReadyByRoomId(room.getId(), !room.getOwnerReady());
        }
        room = roomRepository.findRoomByOwnerNameOrUserName(users.getUsername(), users.getUsername());
        if (room.getUserReady() && room.getOwnerReady() && room.getRoomState() == FULL) {
            startGame();
        }
    }

    private void startGame() {
        Users users = userService.getCurrentUser();
        Room room = roomRepository.findRoomByOwnerNameOrUserName(users.getUsername(), users.getUsername());
        roomRepository.updateRoomStateByRoomId(room.getId());
        gameService.create();
    }
}
