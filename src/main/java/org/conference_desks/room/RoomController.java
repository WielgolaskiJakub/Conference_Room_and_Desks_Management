package org.conference_desks.room;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/room")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public List<RoomResponse> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> getRoomById(@PathVariable long id) {
        RoomResponse room = roomService.getRoomById(id);
        return ResponseEntity.ok(room);
    }

    @PostMapping
    public ResponseEntity<RoomResponse> createRoom(@Valid @RequestBody RoomRequest roomRequest) {
        RoomResponse createdRoom = roomService.createRoom(roomRequest);
        return ResponseEntity
                .created(URI.create("/api/v1/room/" + createdRoom.getId()))
                .body(createdRoom);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomResponse> updateRoomFully(@PathVariable long id,
                                                        @Valid @RequestBody RoomRequest roomRequest) {
        RoomResponse updatedRoomFully = roomService.updateRoomFully(roomRequest, id);
        return ResponseEntity.ok(updatedRoomFully);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable long id) {
        roomService.deleteRoomById(id);
        return ResponseEntity.noContent().build();
    }
}
