package org.example.auction_platform.controller.listing.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder // Required for inheritance
@NoArgsConstructor
public class GetListingResponse {
    private String itemName;
    private String creatorEmail;
}
