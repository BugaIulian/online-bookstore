package com.example.onlinebookstore.exceptions;

import com.example.onlinebookstore.exceptions.user.UsersCredentialsExceptions;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.onlinebookstore.exceptions.book.BookNotFoundException;
import com.example.onlinebookstore.exceptions.user.UserNotFoundException;
import com.example.onlinebookstore.exceptions.user.UserCreationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
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

    @ExceptionHandler(UserCreationException.class)
    public ResponseEntity<Object> usernameAlreadyExists(UserCreationException userCreationException) {
        return getExceptionResponse(userCreationException, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> userNotFound(UserNotFoundException userNotFoundException) {
        return getExceptionResponse(userNotFoundException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsersCredentialsExceptions.class)
    public ResponseEntity<Object> incorrectPassword(UsersCredentialsExceptions usersCredentialsExceptions) {
        return getExceptionResponse(usersCredentialsExceptions, HttpStatus.UNAUTHORIZED);
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
            log.info("Json processing exception.");
        }
        return null;
    }
}