package group1.itschool.onlinebookstore.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import group1.itschool.onlinebookstore.exceptions.book.BookNotFoundException;
import group1.itschool.onlinebookstore.exceptions.user.UserNotFoundException;
import group1.itschool.onlinebookstore.exceptions.user.UsernameAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    private final ObjectMapper objectMapper;

    @Autowired
    public ExceptionHandlerAdvice(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Object> bookNotFoundException(BookNotFoundException bookException) {
        return getExceptionResponse(bookException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<Object> usernameAlreadyExists(UsernameAlreadyExistsException usernameAlreadyExistsException) {
        return getExceptionResponse(usernameAlreadyExistsException, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> userNotFound(UserNotFoundException userNotFoundException) {
        return getExceptionResponse(userNotFoundException, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<Object> getExceptionResponse(RuntimeException runtimeException, HttpStatus httpStatus) {
        Map<String, Object> result = new HashMap<>();
        result.put("Message: ", runtimeException.getMessage());
        return new ResponseEntity<>(objectToString(result), httpStatus);
    }

    private String objectToString(Object response) {
        try {
            objectMapper.findAndRegisterModules();
            return objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            System.out.println("something went really bad here");
        }
        return null;
    }
}