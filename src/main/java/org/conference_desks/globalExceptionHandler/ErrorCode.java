package org.conference_desks.globalExceptionHandler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    DESK_NOT_FOUND(HttpStatus.NOT_FOUND, "error.desk.not.found"),
    INVALID_ARGUMENT(HttpStatus.BAD_REQUEST, "error.invalid.argument"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "error.internal"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "error.user.not.found"),
    ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "error.desk.not.found"),
    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "error.reservation.not.found"),
    CALENDAR_EVENT_NOT_FOUND(HttpStatus.NOT_FOUND, "error.calendar.event.not.found"),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "error.access.denied");
    private final HttpStatus status;
    private final String messageKey;

    ErrorCode(HttpStatus status, String messageKey) {
        this.status = status;
        this.messageKey = messageKey;
    }
    }