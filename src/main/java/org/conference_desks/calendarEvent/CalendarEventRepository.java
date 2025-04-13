package org.conference_desks.calendarEvent;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarEventInterface extends JpaRepository<CalendarEvent, Integer> {
}
