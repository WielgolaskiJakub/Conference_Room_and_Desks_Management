package org.conference_desks.room;


import org.conference_desks.desk.DeskResponse;
import org.conference_desks.globalExceptionHandler.ApiException;
import org.conference_desks.globalExceptionHandler.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;


    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    //user
    public List<RoomResponse> getAllRooms() {
        return roomRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    //user
    public RoomResponse getRoomById(Long id) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new ApiException(ErrorCode.ROOM_NOT_FOUND, id));
        return mapToResponse(room);
    }

    //admin
    public RoomResponse createRoom(RoomRequest roomRequest) {
        Room room = new Room();
        Room savedRoom = updateRoomFromRequest(room, roomRequest);
        return mapToResponse(savedRoom);
    }

    //admin
    public RoomResponse updateRoomFully(RoomRequest roomRequest, Long id) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new ApiException(ErrorCode.ROOM_NOT_FOUND, id));
        Room savedRoom = updateRoomFromRequest(room, roomRequest);
        return mapToResponse(savedRoom);
    }

    //admin
    public void deleteRoomById(Long id) {
        if (!roomRepository.existsById(id)) {
            throw new ApiException(ErrorCode.ROOM_NOT_FOUND, id);
        }
        roomRepository.deleteById(id);
    }

    private Room updateRoomFromRequest(Room room, RoomRequest roomRequest) {
        room.setName(roomRequest.getName());
        room.setLocation(roomRequest.getLocation());
        room.setCapacity(roomRequest.getCapacity());

        return roomRepository.save(room);
    }

    public RoomResponse mapToResponse(Room room) {
        List<DeskResponse> deskResponses = room.getDesks().stream().map(desk -> new DeskResponse(
                desk.getId(),
                desk.getDeskCode(),
                desk.isActive(),
                desk.getRoom().getId(),
                desk.getRoom().getName()
        )).toList();
        return new RoomResponse(
                room.getId(),
                room.getName(),
                room.getCapacity(),
                room.getLocation(),
                deskResponses
        );
    }

}
