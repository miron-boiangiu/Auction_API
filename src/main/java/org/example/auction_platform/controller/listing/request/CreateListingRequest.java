package org.example.auction_platform.controller.listing.request;

import lombok.Data;

@Data
public class CreateListingRequest {
    String listingName;
    long startingValue;
    String creatorEmail;  // TODO: depending on the app, this should probably come from a session
}
