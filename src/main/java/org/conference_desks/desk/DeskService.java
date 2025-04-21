package org.conference_desks.desk;

import org.conference_desks.globalExceptionHandler.ApiException;
import org.conference_desks.globalExceptionHandler.ErrorCode;
import org.conference_desks.room.Room;
import org.conference_desks.room.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeskService {

    private final DeskRepository deskRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public DeskService(DeskRepository deskRepository, RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
        this.deskRepository = deskRepository;
    }

    public List<DeskResponse> getAllDesks(){
        return deskRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    public DeskResponse getDeskById(Long id){
        Desk desk = deskRepository.findById(id).orElseThrow(()->new ApiException(ErrorCode.DESK_NOT_FOUND,id));
        return mapToResponse(desk);
    }

    public DeskResponse createDesk(DeskRequest deskRequest){
        Desk desk = new Desk();
        Desk savedDesk = updateFromRequest(desk, deskRequest);
        return mapToResponse(savedDesk);
    }

    //admin only
    public DeskResponse updateDeskFully(DeskRequest deskRequest, Long id){
        Desk desk = deskRepository.findById(id).orElseThrow(() -> new ApiException(ErrorCode.DESK_NOT_FOUND, id));
        Desk savedDesk = updateFromRequest(desk, deskRequest);
        return mapToResponse(savedDesk);
    }

    public void deleteDeskById(Long id){
        if(!deskRepository.existsById(id)){
            throw new ApiException(ErrorCode.DESK_NOT_FOUND,id);
        }
        deskRepository.deleteById(id);
    }

    private Desk updateFromRequest(Desk desk, DeskRequest deskRequest) {
        desk.setDeskCode(deskRequest.getDeskCode());
        desk.setActive(deskRequest.isActive());

        Room room = roomRepository.findById(deskRequest.getRoomId())
                .orElseThrow(() -> new ApiException(ErrorCode.ROOM_NOT_FOUND, deskRequest.getRoomId()));

        desk.setRoom(room);
        return deskRepository.save(desk);
    }

    public DeskResponse mapToResponse(Desk desk) {

        return new DeskResponse(
                desk.getId(),
                desk.getDeskCode(),
                desk.isActive(),
                desk.getRoom().getId(),
                desk.getRoom().getName()
        );
    }

}
