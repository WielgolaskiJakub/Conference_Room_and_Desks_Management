package org.conference_desks.calendarEvent;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CalendarEventResponse {
    private Long id;
    private String title;
    private LocalDateTime start;
    private LocalDateTime end;
    private Long roomId;
    private Long reservationId;

    public CalendarEventResponse(Long id, String title, LocalDateTime start, LocalDateTime end, Long roomId, Long reservationId) {
        this.id = id;
        this.title = title;
        this.start = start;
        this.end = end;
        this.roomId = roomId;
        this.reservationId = reservationId;
    }
}
