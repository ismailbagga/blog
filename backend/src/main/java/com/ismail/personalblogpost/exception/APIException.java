package com.ismail.personalblogpost.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
@Getter
@Builder
public class APIException extends  RuntimeException{
    private HttpStatus status  =HttpStatus.BAD_REQUEST;


    public APIException(HttpStatus status) {
        this.status = status;
    }

    public APIException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public APIException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }

    public APIException(Throwable cause, HttpStatus status) {
        super(cause);
        this.status = status;
    }

    public APIException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, HttpStatus status) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.status = status;
    }
}
