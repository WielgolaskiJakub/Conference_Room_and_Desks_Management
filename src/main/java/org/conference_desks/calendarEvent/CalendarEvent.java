package org.conference_desks.calendarEvent;

import jakarta.persistence.*;
import lombok.*;
import org.conference_desks.reservation.Reservation;
import org.conference_desks.room.Room;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "calendar_event")
@Entity
public class CalendarEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String title;

    LocalDateTime start;

    LocalDateTime end;

    @ManyToOne
    @JoinColumn(name = "room_id")
    Room room;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    Reservation reservation;
}
