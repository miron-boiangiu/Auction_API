package org.example.auction_platform.service.bid;

import org.example.auction_platform.exception.BidTooSmallException;
import org.example.auction_platform.exception.EntryNotFoundException;
import org.example.auction_platform.repository.account.AccountRepository;
import org.example.auction_platform.repository.account.entity.Account;
import org.example.auction_platform.repository.bid.BidRepository;
import org.example.auction_platform.repository.bid.entity.Bid;
import org.example.auction_platform.repository.listing.OngoingListingRepository;
import org.example.auction_platform.repository.listing.entity.OngoingListing;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.example.auction_platform.constants.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BidServiceTest {

    @Mock
    private BidRepository bidRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private OngoingListingRepository ongoingListingRepository;

    @InjectMocks
    private BidService bidService;

    @Test
    void givenValidData_whenAddBid_thenSuccess() {
        when(accountRepository.findByEmail(EMAIL)).thenReturn(ACCOUNT);

        OngoingListing listing = mock(OngoingListing.class);
        when(listing.getBids()).thenReturn(Collections.emptyList());
        when(listing.getStartingPrice()).thenReturn(STARTING_PRICE);

        when(ongoingListingRepository.findById(LISTING_ID)).thenReturn(Optional.of(listing));

        bidService.addBid(EMAIL, HIGH_BID_VALUE, LISTING_ID);

        verify(bidRepository).save(any(Bid.class));
    }

    @Test
    void givenUnknownAccount_whenAddBid_thenThrows() {
        when(accountRepository.findByEmail(EMAIL)).thenReturn(null);

        assertThrows(EntryNotFoundException.class, () -> bidService.addBid(EMAIL, HIGH_BID_VALUE, LISTING_ID));

        verify(bidRepository, times(0)).save(any(Bid.class));
    }

    @Test
    void givenUnknownListing_whenAddBid_thenThrows() {
        when(accountRepository.findByEmail(EMAIL)).thenReturn(ACCOUNT);
        when(ongoingListingRepository.findById(LISTING_ID)).thenReturn(Optional.empty());

        assertThrows(EntryNotFoundException.class, () -> bidService.addBid(EMAIL, HIGH_BID_VALUE, LISTING_ID));

        verify(bidRepository, times(0)).save(any(Bid.class));
    }

    @Test
    void givenBidLowerThanCurrentMax_whenAddBid_thenThrows() {
        when(accountRepository.findByEmail(EMAIL)).thenReturn(ACCOUNT);

        OngoingListing listing = mock(OngoingListing.class);
        Bid existingHighBid = Bid.builder().value(HIGH_BID_VALUE).build();

        when(listing.getBids()).thenReturn(List.of(existingHighBid));
        when(ongoingListingRepository.findById(LISTING_ID)).thenReturn(Optional.of(listing));

        assertThrows(BidTooSmallException.class, () -> bidService.addBid(EMAIL, LOW_BID_VALUE, LISTING_ID));

        verify(bidRepository, times(0)).save(any(Bid.class));
    }

    @Test
    void givenBidLowerThanStartingPrice_whenAddBid_thenThrows() {
        when(accountRepository.findByEmail(EMAIL)).thenReturn(ACCOUNT);

        OngoingListing listing = mock(OngoingListing.class);
        when(listing.getBids()).thenReturn(Collections.emptyList());
        when(listing.getStartingPrice()).thenReturn(STARTING_PRICE);

        when(ongoingListingRepository.findById(LISTING_ID)).thenReturn(Optional.of(listing));

        assertThrows(BidTooSmallException.class, () -> bidService.addBid(EMAIL, LOW_BID_VALUE, LISTING_ID));

        verify(bidRepository, times(0)).save(any(Bid.class));
    }

    @Test
    void givenExistingListing_whenGetBids_thenReturnBids() {
        OngoingListing listing = mock(OngoingListing.class);
        List<Bid> bids = List.of(new Bid());

        when(ongoingListingRepository.findById(LISTING_ID)).thenReturn(Optional.of(listing));
        when(listing.getBids()).thenReturn(bids);

        List<Bid> result = bidService.getBids(LISTING_ID);

        assertEquals(bids, result);
    }

    @Test
    void givenNonExistingListing_whenGetBids_thenThrows() {
        when(ongoingListingRepository.findById(LISTING_ID)).thenReturn(Optional.empty());

        assertThrows(EntryNotFoundException.class, () -> bidService.getBids(LISTING_ID));
    }
}