package org.conference_desks.reservation;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.conference_desks.common.validation.ValidReservationPeriod;

import java.time.LocalDateTime;

@ValidReservationPeriod
@Getter
@Setter
public class ReservationRequest {

    Long id;

    @NotNull(message = "Start Time is required")
    @FutureOrPresent(message = "Start time must be now or later")
    LocalDateTime startTime;

    @NotNull(message = "End time is required")
    @Future(message = "End time must be in the future")
    LocalDateTime endTime;

    @Size(min = 3, max = 100)
    @NotBlank(message = "Title cant be empty")
    String title;

    //TODO USUNAC PO ZROBIENIU JWT TOKENA POWINNO WTEDY POBIERAC USERA Z TOKENA A NIE RECZNIE
    @NotNull(message = "roomId cant be empty")
    Long roomId;

    @NotNull(message = "userId cant be empty")
    Long userId;

    @NotNull(message = "deskId cant be empty")
    Long deskId;
}

