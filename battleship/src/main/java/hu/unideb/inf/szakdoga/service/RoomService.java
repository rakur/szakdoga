package hu.unideb.inf.szakdoga.service;

import org.springframework.stereotype.Service;

@Service
public interface RoomService {
    void createRoom(Long ownerId);
    void joinRoom(Long roomId, Long userId);
    void quit(Long roomId, Long userId);
    void toggleReady(Long roomId, Long userId, Boolean isReady);
    void startGame(Long roomId);
}
