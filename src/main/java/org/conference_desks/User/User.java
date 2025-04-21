package org.conference_desks.User;

import jakarta.persistence.*;
import lombok.*;
import org.conference_desks.common.Role;
import org.conference_desks.reservation.Reservation;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String email;

    String password;

    @Enumerated(EnumType.STRING)
    Role role;

    String department;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Reservation> reservations = new ArrayList<>();
}
