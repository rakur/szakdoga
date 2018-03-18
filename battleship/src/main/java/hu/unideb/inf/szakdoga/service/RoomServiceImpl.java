package hu.unideb.inf.szakdoga.service;

import hu.unideb.inf.szakdoga.model.Room;
import hu.unideb.inf.szakdoga.model.Users;
import hu.unideb.inf.szakdoga.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UsersService usersService;

    @Override
    public void createRoom(Long ownerId) {
        roomRepository.save(Room.builder().ownerId(ownerId).build());
    }

    @Override
    public void joinRoom(Long roomId, Long userId) {
        roomRepository.updateUserIdByRoomId(roomId, userId);
    }

    @Override
    public void quit(Long roomId, Long userId) {
        Users users = usersService.getUserById(userId);
        Room room = roomRepository.findOne(roomId);
        if (users.getId().equals(room.getUserId())){
            roomRepository.updateUserIdByRoomId(roomId, null);
        }
        if (users.getId().equals(room.getOwnerId())){
            roomRepository.delete(roomId);
        }
    }

    @Override
    public void toggleReady(Long roomId, Long userId, Boolean isReady) {
        Users users = usersService.getUserById(userId);
        Room room = roomRepository.findOne(roomId);
        if (users.getId().equals(room.getUserId())) {
            roomRepository.updatUserReadyByRoomId(roomId, isReady);
        }
        else {
            roomRepository.updatOwnerReadyByRoomId(roomId, isReady);
        }
    }

    @Override
    public void startGame(Long roomId) {
        if (roomRepository.existsRoomByIdAndOwnerReadyAndUserReady(roomId, true, true)) {
            roomRepository.updateRoomStateByRoomId(roomId);
        }
    }
}
