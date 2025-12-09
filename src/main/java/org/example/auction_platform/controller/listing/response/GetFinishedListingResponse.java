package org.example.auction_platform.controller.listing.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class GetFinishedListingResponse extends GetListingResponse {
    private String winnerEmail;
}