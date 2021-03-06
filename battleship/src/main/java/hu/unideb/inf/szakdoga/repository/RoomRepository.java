package hu.unideb.inf.szakdoga.repository;

import hu.unideb.inf.szakdoga.model.Room;
import hu.unideb.inf.szakdoga.model.RoomState;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RoomRepository extends CrudRepository<Room, Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Room r SET r.userName = :userName, r.userReady = false, r.roomState = :roomState WHERE r.id = :roomId")
    void updateUserNameByRoomId(@Param("roomId") Long roomId, @Param ("userName") String userName, @Param("roomState") RoomState roomState);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Room r SET r.ownerReady = :isReady WHERE r.id = :roomId")
    void updateOwnerReadyByRoomId(@Param("roomId") Long roomId, @Param ("isReady") Boolean isReady);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Room r SET r.userReady = :isReady WHERE r.id = :roomId")
    void updateUserReadyByRoomId(@Param("roomId") Long roomId, @Param ("isReady")Boolean isReady);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Room r SET r.roomState = 'ONGOING' where r.id = :roomId")
    void updateRoomStateByRoomId(@Param("roomId") Long roomId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Room r SET r.gameId = :gameId where r.id = :roomId")
    void updateGameIdByRoomId(@Param("gameId") Long gameId, @Param("roomId") Long roomId);

    Room findRoomByOwnerNameOrUserName(String ownerName, String userName);

    List<Room> findAllByRoomState(RoomState roomState);

    Room getRoomById(Long roomId);
}
