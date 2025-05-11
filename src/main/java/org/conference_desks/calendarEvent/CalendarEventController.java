package org.conference_desks.calendarEvent;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/event")
public class CalendarEventController {

    private final CalendarEventService calendarEventService;

    public CalendarEventController(CalendarEventService calendarEventService) {
        this.calendarEventService = calendarEventService;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<CalendarEventResponse> getAllCalendarEvents() {
        return calendarEventService.getAllCalendarEvents();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<CalendarEventResponse> getCalendarEventById(@PathVariable Long id) {
        CalendarEventResponse event = calendarEventService.getCalendarEventById(id);
        return ResponseEntity.ok(event);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CalendarEventResponse> createCalendarEvent(@Valid @RequestBody CalendarEventRequest request) {
        CalendarEventResponse event = calendarEventService.createCalendarEvent(request);
        return ResponseEntity.created(URI.create("/api/v1/event/" + event.getId())).body(event);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CalendarEventResponse> updateCalendarEvent(@PathVariable Long id,
                                                                     @Valid @RequestBody CalendarEventRequest request) {
        CalendarEventResponse event = calendarEventService.updateCalendarEventFully(request, id);
        return ResponseEntity.ok(event);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCalendarEvent(@PathVariable Long id) {
        calendarEventService.deleteCalendarEvent(id);
        return ResponseEntity.noContent().build();
    }
}

