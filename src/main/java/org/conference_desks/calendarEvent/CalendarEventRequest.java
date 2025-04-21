package org.conference_desks.calendarEvent;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.conference_desks.common.validation.ValidReservationPeriod;

import java.time.LocalDateTime;
@ValidReservationPeriod
@Getter
@Setter
public class CalendarEventRequest {

    long id;

    @Size(min = 3, max = 100)
    @NotBlank(message = "Title cant be empty")
    String title;

    @NotNull(message = "Start Time is required")
    @FutureOrPresent(message = "Start time must be now or later")
    LocalDateTime start;

    @NotNull(message = "End time is required")
    @Future(message = "End time must be in the future")
    LocalDateTime end;

    @NotNull(message = "RoomID cant be empty")
    Long roomId;

    @NotNull(message = "ReservationID cant be empty")
    Long reservationId;
}
