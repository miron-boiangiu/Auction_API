package org.example.auction_platform.controller.listing.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class GetOngoingListingResponse extends GetListingResponse {
    private long currentPrice;
    private long startingPrice;
    private int numberOfBids;
}
