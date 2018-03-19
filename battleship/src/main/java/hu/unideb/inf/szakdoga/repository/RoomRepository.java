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
    @Modifying
    @Query("UPDATE Room r SET r.userId = :userId, r.userReady = false WHERE r.id = :roomId")
    void updateUserIdByRoomId(@Param("roomId") Long roomId, @Param ("userId")Long userId);

    @Transactional
    @Modifying
    @Query("UPDATE Room r SET r.ownerReady = :isReady WHERE r.id = :roomId")
    void updateOwnerReadyByRoomId(@Param("roomId") Long roomId, @Param ("isReady") Boolean isReady);

    @Transactional
    @Modifying
    @Query("UPDATE Room r SET r.userReady = :isReady WHERE r.id = :roomId")
    void updateUserReadyByRoomId(@Param("roomId") Long roomId, @Param ("isReady")Boolean isReady);

    @Transactional
    @Modifying
    @Query("UPDATE Room r SET r.roomState = 'ONGOING' where r.id = :roomId")
    void updateRoomStateByRoomId(@Param("roomId") Long roomId);

    Boolean existsRoomByIdAndOwnerReadyAndUserReady(Long id, Boolean ownerReady, Boolean userReady);

    Room findRoomByOwnerIdOrUserId(Long ownerId, Long userId);

    List<Room> findAllByRoomState(RoomState roomState);
}
