package org.conference_desks.desk;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DeskResponse {

    private Long id;
    private String deskCode;
    private boolean active;
    private Long roomId;
    private String roomName;

    public DeskResponse(Long id, String deskCode, boolean active, Long roomId, String roomName) {
        this.id = id;
        this.deskCode = deskCode;
        this.active = active;
        this.roomId = roomId;
        this.roomName = roomName;

    }
}
