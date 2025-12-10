package org.example.auction_platform.controller.bid.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BidResponse {

    private long value;
    private String bidderEmail;
}
