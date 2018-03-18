package hu.unideb.inf.szakdoga.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Room implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long gameId;

    @NotNull
    private Long ownerId;


    private Long userId;

    @NotNull
    private Boolean ownerReady;

    @NotNull
    private Boolean userReady;

    @NotNull
    private RoomState roomState;
}
