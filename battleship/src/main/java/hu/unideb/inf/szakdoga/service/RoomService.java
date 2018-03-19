package hu.unideb.inf.szakdoga.service;

import hu.unideb.inf.szakdoga.model.Room;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoomService {
    void createRoom();
    void joinRoom(Long roomId);
    void quit();
    void toggleReady(Long roomId, Long userId, Boolean isReady);
    void startGame(Long roomId);
    Room getRoom();
    List<Room> getAllRooms();
}
