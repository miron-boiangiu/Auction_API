package org.example.auction_platform.exception;

public class UserInvalidInputException extends RuntimeException {
    public UserInvalidInputException(String publicMessage) {
        super(publicMessage);
    }
}
