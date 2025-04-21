package org.conference_desks.User;

import lombok.Getter;
import lombok.Setter;
import org.conference_desks.common.Role;
import org.conference_desks.reservation.ReservationResponse;

import java.util.List;

@Getter
@Setter

public class UserResponse {

    private Long id;
    private String email;
    private Role role;
    private String department;
    private List<ReservationResponse> reservations;


    public UserResponse(Long id, String email, Role role, String department, List<ReservationResponse> reservations) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.department = department;
        this.reservations = reservations;
    }

}
