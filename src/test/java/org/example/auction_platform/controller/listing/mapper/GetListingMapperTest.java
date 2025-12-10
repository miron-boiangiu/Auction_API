package org.example.auction_platform.controller.listing.mapper;

import org.example.auction_platform.controller.listing.response.GetFinishedListingResponse;
import org.example.auction_platform.controller.listing.response.GetListingResponse;
import org.example.auction_platform.controller.listing.response.GetOngoingListingResponse;
import org.example.auction_platform.repository.account.entity.Account;
import org.example.auction_platform.repository.bid.entity.Bid;
import org.example.auction_platform.repository.listing.entity.FinishedListing;
import org.example.auction_platform.repository.listing.entity.OngoingListing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.example.auction_platform.constants.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class GetListingMapperTest {

    private final GetListingMapper mapper = new GetListingMapper();

    @Test
    void givenOngoingListing_whenVisit_thenMapToOngoingResponse_NoBids() {

        Account creator = Account.builder().email(EMAIL).build();

        OngoingListing listing = OngoingListing.builder()
                .id(LISTING_ID)
                .itemName(ITEM_NAME)
                .listingCreator(creator)
                .startingPrice(STARTING_PRICE)
                .bids(Collections.emptyList())
                .build();

        GetListingResponse result = listing.accept(mapper);

        assertInstanceOf(GetOngoingListingResponse.class, result);
        GetOngoingListingResponse response = (GetOngoingListingResponse) result;

        assertEquals(LISTING_ID, response.getId());
        assertEquals(ITEM_NAME, response.getItemName());
        assertEquals(EMAIL, response.getCreatorEmail());
        assertEquals(STARTING_PRICE, response.getStartingPrice());
        assertEquals(STARTING_PRICE, response.getCurrentPrice());
        assertEquals(0, response.getNumberOfBids());
    }

    @Test
    void givenOngoingListing_whenVisit_thenMapToOngoingResponse_WithBids() {

        Account creator = Account.builder().email(EMAIL).build();

        Bid highBid = Bid.builder().value(HIGH_BID_VALUE).build();
        Bid lowBid = Bid.builder().value(LOW_BID_VALUE).build();

        OngoingListing listing = OngoingListing.builder()
                .id(LISTING_ID)
                .itemName(ITEM_NAME)
                .listingCreator(creator)
                .startingPrice(LOW_BID_VALUE)
                .bids(List.of(highBid, lowBid))
                .build();

        GetListingResponse result = listing.accept(mapper);

        assertInstanceOf(GetOngoingListingResponse.class, result);
        GetOngoingListingResponse response = (GetOngoingListingResponse) result;

        assertEquals(LISTING_ID, response.getId());
        assertEquals(HIGH_BID_VALUE, response.getCurrentPrice());
        assertEquals(2, response.getNumberOfBids());
    }

    @Test
    void givenFinishedListing_whenVisit_thenMapToFinishedResponse_WithWinner() {

        Account creator = Account.builder().email(EMAIL).build();
        Account winner = Account.builder().email(WINNER_EMAIL).build();

        FinishedListing listing = FinishedListing.builder()
                .id(LISTING_ID)
                .itemName(ITEM_NAME)
                .listingCreator(creator)
                .biddingWinner(winner)
                .build();

        GetListingResponse result = listing.accept(mapper);

        assertInstanceOf(GetFinishedListingResponse.class, result);
        GetFinishedListingResponse response = (GetFinishedListingResponse) result;

        assertEquals(LISTING_ID, response.getId());
        assertEquals(ITEM_NAME, response.getItemName());
        assertEquals(EMAIL, response.getCreatorEmail());
        assertEquals(WINNER_EMAIL, response.getWinnerEmail());
    }

    @Test
    void givenFinishedListing_whenVisit_thenMapToFinishedResponse_NoWinner() {

        Account creator = Account.builder().email(EMAIL).build();

        FinishedListing listing = FinishedListing.builder()
                .id(LISTING_ID)
                .itemName(ITEM_NAME)
                .listingCreator(creator)
                .biddingWinner(null)
                .build();

        GetListingResponse result = listing.accept(mapper);

        assertInstanceOf(GetFinishedListingResponse.class, result);
        GetFinishedListingResponse response = (GetFinishedListingResponse) result;

        assertEquals(LISTING_ID, response.getId());
        assertEquals(EMAIL, response.getCreatorEmail());
        assertNull(response.getWinnerEmail());
    }
}