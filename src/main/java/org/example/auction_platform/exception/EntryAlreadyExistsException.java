package org.example.auction_platform.exception;

public class EntryAlreadyExistsException extends RuntimeException {
    public EntryAlreadyExistsException(String publicMessage) {
        super(publicMessage);
    }
}
