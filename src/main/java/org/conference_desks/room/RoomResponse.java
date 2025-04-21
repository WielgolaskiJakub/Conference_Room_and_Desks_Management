package org.conference_desks.room;


import lombok.Getter;
import lombok.Setter;
import org.conference_desks.desk.DeskResponse;

import java.util.List;

@Getter
@Setter
public class RoomResponse {

    private Long id;
    private String name;
    private int capacity;
    private String location;
    private List<DeskResponse> desks;

    public RoomResponse(Long id, String name, int capacity, String location, List<DeskResponse> desks) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.location = location;
        this.desks = desks;
    }
}
