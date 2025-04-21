package org.conference_desks.room;

import jakarta.persistence.*;
import lombok.*;
import org.conference_desks.desk.Desk;
import org.conference_desks.reservation.Reservation;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "room")
@ToString
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String name;

    int capacity;

    String location;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL,orphanRemoval = true)
    List<Desk> desks = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "room_reservation",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id"))
    List<Reservation> reservations = new ArrayList<>();
}
