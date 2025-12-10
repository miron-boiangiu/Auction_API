package org.example.auction_platform.exception;

public class EntryNotFoundException extends RuntimeException {
    public EntryNotFoundException(String publicMessage) {
        super(publicMessage);
    }
}
