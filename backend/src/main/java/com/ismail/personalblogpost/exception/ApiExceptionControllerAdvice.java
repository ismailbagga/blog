package com.ismail.personalblogpost.exception;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.key.ZonedDateTimeKeyDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ApiExceptionControllerAdvice {

    public record  ExceptionMessage(String message , Throwable error , HttpStatus status ,@JsonSerialize(using = ZonedDateTimeSerializer.class) ZonedDateTime timestamp) {}
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
