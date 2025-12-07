package org.example.auction_platform.exception;

public class AccountExistsException extends RuntimeException {
    public AccountExistsException(String publicMessage) {
        super(publicMessage);
    }
}
