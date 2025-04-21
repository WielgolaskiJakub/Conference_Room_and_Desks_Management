package org.conference_desks.globalExceptionHandler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {
    private final ErrorCode errorCode;
    private final Object[] args;

    public ApiException(ErrorCode errorCode, Object... args) {
        super(errorCode.getMessageKey());
        this.errorCode = errorCode;
        this.args = args;
    }

    public HttpStatus getStatus() {
        return errorCode.getStatus();
    }

    public String getMessageKey() {
        return errorCode.getMessageKey();
    }
}
