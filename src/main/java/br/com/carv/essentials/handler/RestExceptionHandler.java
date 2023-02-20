package br.com.carv.essentials.handler;

import br.com.carv.essentials.exception.ExceptionMessageValidator;
import br.com.carv.essentials.exception.ExceptionResponse;
import br.com.carv.essentials.exception.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@RestController
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<?> handleAllExceptions(Exception ex) {
        ExceptionResponse response = new ExceptionResponse("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage(), ex.getClass().getName(), LocalDateTime.now());
        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<?> handleNotFoundExceptions(ResourceNotFoundException ex) {
        ExceptionResponse response = new ExceptionResponse("Bad Request", HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(), ex.getClass().getName(), LocalDateTime.now());
        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        String messages = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

        ExceptionMessageValidator exceptionMessageValidator = new ExceptionMessageValidator("Bad Request",
                status.value(), "Check the field(s) error(s)", ex.getClass().getSimpleName(), LocalDateTime.now(), fields, messages);

        return new ResponseEntity<>(exceptionMessageValidator, headers, status);

    }
}
