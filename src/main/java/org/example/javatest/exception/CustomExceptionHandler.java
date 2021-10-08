package org.example.javatest.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class CustomExceptionHandler<E extends RuntimeException> {
    public ErrorResponse handleException(E exception) {
        log.error(exception.getMessage(), exception);
        return new ErrorResponse(exception.getMessage());
    }
}
