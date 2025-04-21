package org.conference_desks.reservation;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Getter;
import lombok.Setter;
import org.conference_desks.common.validation.ValidReservationPeriod;

import java.time.LocalDateTime;

@Getter
@Setter
@ValidReservationPeriod
public class ReservationPatchRequest {

    @FutureOrPresent(message = "Start time must be now or later")
    LocalDateTime startTime;

    @Future(message = "End time must be in the future")
    LocalDateTime endTime;

    String title;

    Long deskId;
}
//TODO:
// dodać metodę existsByDeskAndTimeRange(desk, start, end) w ReservationRepository
// zabezpieczyć PATCH deskId – tylko jeśli desk jest wolny
// po wdrożeniu JWT usunąć userId z requestów
// dodać @PreAuthorize do PUT/PATCH w ReservationController
// opcjonalnie: integracja z Outlook Calendar (na później)