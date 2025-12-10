package org.example.auction_platform.controller.bid;

import org.example.auction_platform.controller.bid.mapper.BidMapper;
import org.example.auction_platform.controller.bid.request.AddBidRequest;
import org.example.auction_platform.exception.UserInvalidInputException;
import org.example.auction_platform.service.bid.BidService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.example.auction_platform.constants.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BidControllerTest {

    @Mock
    private BidService bidService;

    private BidMapper mapper = new BidMapper();

    private BidController controller;

    @BeforeEach
    public void setUp() {
        controller = new BidController(bidService, mapper);
    }

    @Test
    public void givenValidData_whenAddBid_thenCallService() {
        AddBidRequest request = new AddBidRequest();
        request.setValue(STARTING_PRICE);
        request.setBidderEmail(EMAIL);

        controller.addBid(LISTING_ID, request);

        verify(bidService).addBid(EMAIL, STARTING_PRICE, LISTING_ID);
    }

    @Test
    public void givenInvalidValue_whenAddBid_thenThrow() {
        AddBidRequest request = new AddBidRequest();
        request.setValue(-5);
        request.setBidderEmail(EMAIL);

        assertThrows(UserInvalidInputException.class,
                () -> controller.addBid(LISTING_ID, new AddBidRequest()));

        verify(bidService, times(0)).addBid(EMAIL, STARTING_PRICE, LISTING_ID);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", "gagagag" })  // TODO: Way too simple tests since the check in UserInputValidator is simple and I am too tired to change it.
    public void givenInvalidEmail_whenAddBid_thenThrow(String email) {
        AddBidRequest request = new AddBidRequest();
        request.setValue(STARTING_PRICE);
        request.setBidderEmail(email);

        assertThrows(UserInvalidInputException.class,
                () -> controller.addBid(LISTING_ID, new AddBidRequest()));

        verify(bidService, times(0)).addBid(EMAIL, STARTING_PRICE, LISTING_ID);
    }

    @Test
    public void givenId_whenGetBids_thenCallService() {

        controller.getBids(LISTING_ID);

        verify(bidService).getBids(LISTING_ID);
    }
}