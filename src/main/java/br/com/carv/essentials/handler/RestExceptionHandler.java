package br.com.carv.essentials.handler;

import br.com.carv.essentials.exception.ExceptionResponse;
import br.com.carv.essentials.exception.ResourceNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ControllerAdvice
@RestController
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<?> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage(), ex.getClass().getName(), LocalDateTime.now());
        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFound.class)
    public final ResponseEntity<?> handleNotFoundExceptions(ResourceNotFound ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse("Bad Request", HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(), ex.getClass().getName(), LocalDateTime.now());
        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
    }
}
