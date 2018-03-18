package hu.unideb.inf.szakdoga.repository;

import hu.unideb.inf.szakdoga.model.Room;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room, Long> {

    @Modifying
    @Query("UPDATE Room r SET r.userId = :userId WHERE r.id = :roomId")
    void updateUserIdByRoomId(Long roomId, Long userId);

    @Modifying
    @Query("UPDATE Room r SET r.ownerReady = :isReady WHERE r.id = :roomId")
    void updatOwnerReadyByRoomId(Long roomId, Boolean isReady);

    @Modifying
    @Query("UPDATE Room r SET r.userReady = :isReady WHERE r.id = :roomId")
    void updatUserReadyByRoomId(Long roomId, Boolean isReady);

    @Modifying
    @Query("UPDATE Room r SET r.roomState = 'ONGOING' where r.id = :roomId")
    void updateRoomStateByRoomId(Long roomId);

    Boolean existsRoomByIdAndOwnerReadyAndUserReady(Long id, Boolean ownerReady, Boolean userReady);
}
