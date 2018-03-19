package hu.unideb.inf.szakdoga.service;

import hu.unideb.inf.szakdoga.model.Room;
import hu.unideb.inf.szakdoga.model.RoomState;
import hu.unideb.inf.szakdoga.model.Users;
import hu.unideb.inf.szakdoga.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static hu.unideb.inf.szakdoga.model.RoomState.NEW;

@Component
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UsersService usersService;

    @Autowired
    private UserService userService;


    @Override
    public void createRoom() {
        Long ownerId = userService.getCurrentUser().getId();
        roomRepository.save(Room.builder().ownerId(ownerId).ownerReady(false).userReady(false).roomState(NEW).build());
    }

    @Override
    public void joinRoom(Long roomId) {
        Long userId = userService.getCurrentUser().getId();
        roomRepository.updateUserIdByRoomId(roomId, userId);
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAllByRoomState(RoomState.NEW);
    }

    @Override
    public void quit() {
        Users users = userService.getCurrentUser();
        Room room = roomRepository.findRoomByOwnerIdOrUserId(users.getId(), users.getId());
        if (room.getUserId().equals(users.getId())){
            roomRepository.updateUserIdByRoomId(room.getId(), null);
        }
        if (room.getOwnerId().equals(users.getId())){
            roomRepository.delete(room.getId());
        }
    }

    @Override
    public Room getRoom() {
        Long id = userService.getCurrentUser().getId();
        return roomRepository.findRoomByOwnerIdOrUserId(id,id);
    }

    @Override
    public void toggleReady(Long roomId, Long userId, Boolean isReady) {
        Users users = usersService.getUserById(userId);
        Room room = roomRepository.findOne(roomId);
        if (users.getId().equals(room.getUserId())) {
            roomRepository.updateUserReadyByRoomId(roomId, isReady);
        }
        else {
            roomRepository.updateOwnerReadyByRoomId(roomId, isReady);
        }
    }

    @Override
    public void startGame(Long roomId) {
        if (roomRepository.existsRoomByIdAndOwnerReadyAndUserReady(roomId, true, true)) {
            roomRepository.updateRoomStateByRoomId(roomId);
        }
    }
}
