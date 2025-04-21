package org.conference_desks.desk;

import jakarta.persistence.*;
import lombok.*;
import org.conference_desks.room.Room;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "desk")
public class Desk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String deskCode;

    boolean active;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
}
