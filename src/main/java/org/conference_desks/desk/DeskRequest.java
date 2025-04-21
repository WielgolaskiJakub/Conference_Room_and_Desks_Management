package org.conference_desks.desk;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeskRequest {

    Long id;

    @NotBlank(message = "deskCode field cant be empty")
    String deskCode;

    @NotNull(message = "Active field cant be empty")
    boolean active;

    @NotNull(message = "Room ID cant be null")
    Long roomId;

}
