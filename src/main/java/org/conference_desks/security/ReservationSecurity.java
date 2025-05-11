package org.conference_desks.security;

import org.conference_desks.reservation.ReservationRepository;
import org.springframework.stereotype.Component;

@Component("reservationSecurity")
public class ReservationSecurity {

    private final ReservationRepository reservationRepository;

    public ReservationSecurity(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public boolean isOwner(Long reservationId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        return reservationRepository.findById(reservationId)
                .map(reservation -> reservation.getCreatedBy().getId().equals(currentUserId))
                .orElse(false);
    }
}
