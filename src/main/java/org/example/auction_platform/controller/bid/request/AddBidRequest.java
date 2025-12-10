package org.example.auction_platform.controller.bid.request;

import lombok.Data;

@Data
public class AddBidRequest {
    String bidderEmail;
    long value;
}
