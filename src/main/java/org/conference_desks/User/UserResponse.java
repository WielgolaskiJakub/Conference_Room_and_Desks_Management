package org.conference_desks.User;

import lombok.Getter;
import lombok.Setter;
import org.conference_desks.common.TypeOfReservation;
import org.conference_desks.reservation.ReservationResponse;

import java.util.List;

@Getter
@Setter

public class UserResponse {

    private Long id;
    private String email;
    private String department;
    private List<ReservationResponse> reservations;


    public UserResponse(Long id, String email, String department, List<ReservationResponse> reservations) {
        this.id = id;
        this.email = email;
        this.department = department;
        this.reservations = reservations;
    }

}
