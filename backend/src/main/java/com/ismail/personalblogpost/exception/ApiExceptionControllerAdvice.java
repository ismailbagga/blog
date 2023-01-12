package com.ismail.personalblogpost.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionControllerAdvice {

    public record  ExceptionMessage(String message , Throwable error , HttpStatus status , ZonedDateTime timestamp) {}
    @ExceptionHandler(value = APIException.class)
    public ResponseEntity<ExceptionMessage>  apiExceptionHandler(APIException exception) {
        var exceptionMessage = new ExceptionMessage(
                exception.getMessage() ,
                exception.getCause() ,
                exception.getStatus(),
                ZonedDateTime.now()

        ) ;
        return ResponseEntity.status(exceptionMessage.status())
                .body(exceptionMessage) ;

    }
}
