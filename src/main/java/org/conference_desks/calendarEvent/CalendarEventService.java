package org.conference_desks.calendarEvent;

import org.conference_desks.globalExceptionHandler.ApiException;
import org.conference_desks.globalExceptionHandler.ErrorCode;
import org.conference_desks.reservation.Reservation;
import org.conference_desks.reservation.ReservationRepository;
import org.conference_desks.room.Room;
import org.conference_desks.room.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CalendarEventService {

    private final CalendarEventRepository calendarEventRepository;
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    public CalendarEventService(CalendarEventRepository calendarEventRepository,
                                RoomRepository roomRepository,
                                ReservationRepository reservationRepository) {
        this.calendarEventRepository = calendarEventRepository;
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<CalendarEventResponse> getAllCalendarEvents() {
        return calendarEventRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    public CalendarEventResponse getCalendarEventById(Long id) {
        CalendarEvent calendarEvent = calendarEventRepository.findById(id).
                orElseThrow(() -> new ApiException(ErrorCode.CALENDAR_EVENT_NOT_FOUND, id));
        return mapToResponse(calendarEvent);
    }

    public CalendarEventResponse createCalendarEvent(CalendarEventRequest calendarEventRequest) {
        CalendarEvent calendarEvent = new CalendarEvent();
        CalendarEvent savedCalendarEvent = getCalendarEventFromRequest(calendarEvent, calendarEventRequest);
        return mapToResponse(savedCalendarEvent);
    }

    public CalendarEventResponse updateCalendarEventFully(CalendarEventRequest calendarEventRequest, long id) {
        CalendarEvent calendarEvent = calendarEventRepository.findById(id).
                orElseThrow(() -> new ApiException(ErrorCode.CALENDAR_EVENT_NOT_FOUND, id));
        CalendarEvent savedCalendarEvent = getCalendarEventFromRequest(calendarEvent, calendarEventRequest);
        return mapToResponse(savedCalendarEvent);
    }

    public void deleteCalendarEvent(long id) {
        if (!calendarEventRepository.existsById(id)) {
            throw new ApiException(ErrorCode.CALENDAR_EVENT_NOT_FOUND, id);
        }
        calendarEventRepository.deleteById(id);
    }

    private CalendarEvent getCalendarEventFromRequest(CalendarEvent calendarEvent, CalendarEventRequest calendarEventRequest) {
        calendarEvent.setId(calendarEventRequest.getId());
        calendarEvent.setTitle(calendarEventRequest.getTitle());
        calendarEvent.setStart(calendarEventRequest.getStart());
        calendarEvent.setEnd(calendarEventRequest.getEnd());

        Room room = roomRepository.findById(calendarEventRequest.getRoomId()).
                orElseThrow(() -> new ApiException(ErrorCode.ROOM_NOT_FOUND, calendarEventRequest.getRoomId()));
        calendarEvent.setRoom(room);

        Reservation reservation = reservationRepository.findById(calendarEventRequest.getReservationId()).
                orElseThrow(() -> new ApiException(ErrorCode.RESERVATION_NOT_FOUND, calendarEventRequest.getReservationId()));
        calendarEvent.setReservation(reservation);

        return calendarEventRepository.save(calendarEvent);
    }

    public CalendarEventResponse mapToResponse(CalendarEvent calendarEvent) {
        return new CalendarEventResponse(
                calendarEvent.getId(),
                calendarEvent.getTitle(),
                calendarEvent.getStart(),
                calendarEvent.getEnd(),
                calendarEvent.getRoom() != null ? calendarEvent.getRoom().getId() : null,
                calendarEvent.getReservation() != null ? calendarEvent.getReservation().getId() : null
        );
    }

    public CalendarEvent createFromReservation(Reservation reservation) {
        CalendarEvent event = new CalendarEvent();
        event.setTitle(reservation.getTitle());
        event.setStart(reservation.getStartTime());
        event.setEnd(reservation.getEndTime());
        event.setRoom(reservation.getRoom());
        event.setReservation(reservation);
        return calendarEventRepository.save(event);
    }

}
