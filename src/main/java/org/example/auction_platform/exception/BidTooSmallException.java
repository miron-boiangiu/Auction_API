package org.example.auction_platform.exception;

public class BidTooSmallException extends RuntimeException {
    public BidTooSmallException(String publicMessage) {
        super(publicMessage);
    }
}
