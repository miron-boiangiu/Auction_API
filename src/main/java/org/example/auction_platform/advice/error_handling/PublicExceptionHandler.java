package org.example.auction_platform.advice.error_handling;

import org.example.auction_platform.advice.error_handling.response.PublicErrorResponse;
import org.example.auction_platform.exception.AccountExistsException;
import org.example.auction_platform.exception.UserInvalidInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Handles translating exceptions to responses.
 */
@ControllerAdvice
public class PublicExceptionHandler {

    @ExceptionHandler(UserInvalidInputException.class)
    public ResponseEntity<PublicErrorResponse> handleInvalidInput(UserInvalidInputException exception) {
        return new ResponseEntity<>(
                PublicErrorResponse.builder()
                        .error(exception.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountExistsException.class)
    public ResponseEntity<PublicErrorResponse> handleAccountExists(AccountExistsException exception) {
        return new ResponseEntity<>(
                PublicErrorResponse.builder()
                        .error(exception.getMessage())
                        .build(),
                HttpStatus.CONFLICT);
    }
}
