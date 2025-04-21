package org.conference_desks.room;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomRequest {

    Long id;

    @NotBlank(message = "Name cant be empty")
    String name;

    @Positive
    int capacity;

    @NotBlank(message = "Location cant be empty")
    String location;

}
