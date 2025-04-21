package org.conference_desks.reservation;

import jakarta.persistence.*;
import lombok.*;
import org.conference_desks.User.User;
import org.conference_desks.common.ReservationType;
import org.conference_desks.desk.Desk;
import org.conference_desks.room.Room;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    LocalDateTime startTime;

    LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    ReservationType type;

    String title;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    User createdBy;

    @ManyToOne
    @JoinColumn(name = "room_id")
    Room room;

    @ManyToOne
    @JoinColumn(name = "desk_id")
    Desk desk;

    @ManyToMany
    @JoinTable(
            name = "reservation_invited_users",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    List<User> invitedUsers = new ArrayList<>();
}
