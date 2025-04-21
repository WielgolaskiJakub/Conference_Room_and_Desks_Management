package org.conference_desks.reservation;

import lombok.Getter;
import lombok.Setter;
import org.conference_desks.common.ReservationType;


import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationResponse {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String title;
    private ReservationType reservationType;
    private Long roomId;
    private Long deskId;



    public ReservationResponse(Long id, LocalDateTime startTime, LocalDateTime endTime,String title, ReservationType reservationType, Long roomId, Long deskId) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
        this.reservationType = reservationType;
        this.roomId = roomId;
        this.deskId = deskId;
    }
}
