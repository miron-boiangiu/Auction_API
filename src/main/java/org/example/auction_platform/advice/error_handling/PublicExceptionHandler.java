package org.example.auction_platform.advice.error_handling;

import org.example.auction_platform.advice.error_handling.response.PublicErrorResponse;
import org.example.auction_platform.exception.EntryAlreadyExistsException;
import org.example.auction_platform.exception.BidTooSmallException;
import org.example.auction_platform.exception.EntryNotFoundException;
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

    @ExceptionHandler(EntryAlreadyExistsException.class)
    public ResponseEntity<PublicErrorResponse> handleEntryAlreadyExists(EntryAlreadyExistsException exception) {
        return new ResponseEntity<>(
                PublicErrorResponse.builder()
                        .error(exception.getMessage())
                        .build(),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntryNotFoundException.class)
    public ResponseEntity<PublicErrorResponse> handleEntryDoesNotExist(EntryNotFoundException exception) {
        return new ResponseEntity<>(
                PublicErrorResponse.builder()
                        .error(exception.getMessage())
                        .build(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BidTooSmallException.class)
    public ResponseEntity<PublicErrorResponse> handleBidTooSmall(BidTooSmallException exception) {
        return new ResponseEntity<>(
                PublicErrorResponse.builder()
                        .error(exception.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }
}
