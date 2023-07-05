package ru.kata.spring.boot_security.demo.exception_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserGlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<IncorrectUserData> handleException(UserNotFoundException exception) {
        IncorrectUserData data = new IncorrectUserData();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.I_AM_A_TEAPOT);
    }
}
