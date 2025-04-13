package org.conference_desks.globalExceptionHandler;

public class ApiException extends RuntimeException {
  public ApiException(String message) {
    super(message);
  }
}
