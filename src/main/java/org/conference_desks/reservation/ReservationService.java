package org.conference_desks.reservation;

import org.conference_desks.User.User;
import org.conference_desks.User.UserRepository;
import org.conference_desks.calendarEvent.CalendarEventService;
import org.conference_desks.desk.Desk;
import org.conference_desks.desk.DeskRepository;
import org.conference_desks.globalExceptionHandler.ApiException;
import org.conference_desks.globalExceptionHandler.ErrorCode;
import org.conference_desks.room.Room;
import org.conference_desks.room.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final DeskRepository deskRepository;
    private final CalendarEventService calendarEventService;

    public ReservationService(ReservationRepository reservationRepository,
                              RoomRepository roomRepository,
                              UserRepository userRepository,
                              DeskRepository deskRepository,
                              CalendarEventService calendarEventService) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.deskRepository = deskRepository;
        this.calendarEventService = calendarEventService;
    }

    public List<ReservationResponse> getAllReservations() {
        return reservationRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    public ReservationResponse getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id).
                orElseThrow(() -> new ApiException(ErrorCode.RESERVATION_NOT_FOUND, id));
        return mapToResponse(reservation);
    }

    public ReservationResponse createReservation(ReservationRequest request) {
        Reservation reservation = new Reservation();
        Reservation savedReservation = updateReservationFromRequestFully(reservation, request);

        calendarEventService.createFromReservation(savedReservation);

        return mapToResponse(savedReservation);
    }

    public ReservationResponse updateReservationFully(ReservationRequest request, Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.RESERVATION_NOT_FOUND, id));
        Reservation savedReservation = updateReservationFromRequestFully(reservation, request);

        calendarEventService.createFromReservation(reservation);

        return mapToResponse(savedReservation);
    }

    public ReservationResponse updateReservationPartially(ReservationPatchRequest request, Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.RESERVATION_NOT_FOUND, id));
        Reservation updatedReservation = updateReservationFromRequestPartially(reservation, request);

        calendarEventService.createFromReservation(updatedReservation);

        return mapToResponse(updatedReservation);
    }

    public void deleteReservationById(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ApiException(ErrorCode.RESERVATION_NOT_FOUND, id);
        }
        reservationRepository.deleteById(id);
    }

    private Reservation updateReservationFromRequestFully(Reservation reservation, ReservationRequest reservationRequest) {
        reservation.setId(reservationRequest.getId());
        reservation.setStartTime(reservationRequest.getStartTime());
        reservation.setEndTime(reservationRequest.getEndTime());
        reservation.setTitle(reservationRequest.getTitle());

        Room room = roomRepository.findById(reservationRequest.getRoomId()).
                orElseThrow(() -> new ApiException(ErrorCode.ROOM_NOT_FOUND, reservationRequest.getRoomId()));
        reservation.setRoom(room);

        Desk desk = deskRepository.findById(reservationRequest.getDeskId()).
                orElseThrow(() -> new ApiException(ErrorCode.DESK_NOT_FOUND, reservationRequest.getDeskId()));
        reservation.setDesk(desk);

        User user = userRepository.findById(reservationRequest.getUserId()).
                orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND, reservationRequest.getUserId()));
        reservation.setCreatedBy(user);

        return reservationRepository.save(reservation);
    }

    private Reservation updateReservationFromRequestPartially(Reservation reservation,
                                                              ReservationPatchRequest reservationPatchRequest) {
        reservation.setStartTime(reservationPatchRequest.getStartTime());
        reservation.setEndTime(reservationPatchRequest.getEndTime());
        reservation.setTitle(reservationPatchRequest.getTitle());
        if (reservationPatchRequest.getDeskId() != null &&
                !Objects.equals(reservation.getDesk().getId(), reservationPatchRequest.getDeskId())) {

            Desk desk = deskRepository.findById(reservationPatchRequest.getDeskId())
                    .orElseThrow(() -> new ApiException(ErrorCode.DESK_NOT_FOUND, reservationPatchRequest.getDeskId()));
            reservation.setDesk(desk);
        }

        return reservationRepository.save(reservation);
    }

    public ReservationResponse mapToResponse(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getStartTime(),
                reservation.getEndTime(),
                reservation.getTitle(),
                reservation.getType(),
                reservation.getRoom() != null ? reservation.getRoom().getId() : null,
                reservation.getDesk() != null ? reservation.getDesk().getId() : null
        );
    }
}
