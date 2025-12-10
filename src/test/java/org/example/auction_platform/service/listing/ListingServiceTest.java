package org.example.auction_platform.service.listing;

import org.example.auction_platform.exception.EntryNotFoundException;
import org.example.auction_platform.exception.UserInvalidInputException;
import org.example.auction_platform.repository.account.AccountRepository;
import org.example.auction_platform.repository.account.entity.Account;
import org.example.auction_platform.repository.bid.entity.Bid;
import org.example.auction_platform.repository.listing.FinishedListingRepository;
import org.example.auction_platform.repository.listing.OngoingListingRepository;
import org.example.auction_platform.repository.listing.entity.FinishedListing;
import org.example.auction_platform.repository.listing.entity.Listing;
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
class ListingServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private FinishedListingRepository finishedListingRepository;

    @Mock
    private OngoingListingRepository ongoingListingRepository;

    @InjectMocks
    private ListingService listingService;

    @Test
    void givenValidData_whenCreateNewListing_thenSuccess() {
        when(accountRepository.findByEmail(EMAIL)).thenReturn(ACCOUNT);

        when(ongoingListingRepository.save(any(OngoingListing.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        Listing result = listingService.createNewListing(EMAIL, STARTING_PRICE, ITEM_NAME);

        assertNotNull(result);
        verify(ongoingListingRepository).save(any(OngoingListing.class));
    }

    @Test
    void givenUnknownAccount_whenCreateNewListing_thenThrows() {
        when(accountRepository.findByEmail(EMAIL)).thenReturn(null);

        assertThrows(UserInvalidInputException.class,
                () -> listingService.createNewListing(EMAIL, STARTING_PRICE, ITEM_NAME));

        verify(ongoingListingRepository, times(0)).save(any(OngoingListing.class));
    }

    @Test
    void givenExistingOngoingListing_whenGetListing_thenReturnListing() {
        OngoingListing ongoingListing = new OngoingListing();

        lenient().when(finishedListingRepository.findById(LISTING_ID)).thenReturn(Optional.empty());
        lenient().when(ongoingListingRepository.findById(LISTING_ID)).thenReturn(Optional.of(ongoingListing));

        Optional<Listing> result = listingService.getListing(LISTING_ID);

        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof OngoingListing);
    }

    @Test
    void givenExistingFinishedListing_whenGetListing_thenReturnListing() {
        FinishedListing finishedListing = new FinishedListing();

        lenient().when(ongoingListingRepository.findById(LISTING_ID)).thenReturn(Optional.empty());
        lenient().when(finishedListingRepository.findById(LISTING_ID)).thenReturn(Optional.of(finishedListing));

        Optional<Listing> result = listingService.getListing(LISTING_ID);

        assertTrue(result.isPresent());
        assertTrue(result.get() instanceof FinishedListing);
    }

    @Test
    void givenNonExistingListing_whenGetListing_thenReturnEmpty() {
        when(ongoingListingRepository.findById(LISTING_ID)).thenReturn(Optional.empty());
        when(finishedListingRepository.findById(LISTING_ID)).thenReturn(Optional.empty());

        Optional<Listing> result = listingService.getListing(LISTING_ID);

        assertTrue(result.isEmpty());
    }

    @Test
    void whenGetOngoingListings_thenReturnList() {
        when(ongoingListingRepository.findAll()).thenReturn(List.of(new OngoingListing()));

        List<OngoingListing> result = listingService.getOngoingListings();

        assertFalse(result.isEmpty());
    }

    @Test
    void whenGetFinishedListings_thenReturnList() {
        when(finishedListingRepository.findAll()).thenReturn(List.of(new FinishedListing()));

        List<FinishedListing> result = listingService.getFinishedListings();

        assertFalse(result.isEmpty());
    }

    @Test
    void givenListingWithNoBids_whenEndListing_thenMoveToFinishedWithoutWinner() {
        OngoingListing listing = mock(OngoingListing.class);

        when(listing.getBids()).thenReturn(Collections.emptyList());
        when(listing.getListingCreator()).thenReturn(ACCOUNT);
        when(listing.getItemName()).thenReturn(ITEM_NAME);

        when(ongoingListingRepository.findById(LISTING_ID)).thenReturn(Optional.of(listing));

        listingService.endListing(LISTING_ID);

        verify(ongoingListingRepository).deleteById(LISTING_ID);
        verify(finishedListingRepository).save(any(FinishedListing.class));
    }

    @Test
    void givenListingWithBids_whenEndListing_thenMoveToFinishedWithWinner() {

        OngoingListing listing = mock(OngoingListing.class);

        Account winner = Account.builder().email(EMAIL).build();
        Bid winningBid = Bid.builder().bidder(winner).value(STARTING_PRICE).build();

        when(listing.getBids()).thenReturn(List.of(winningBid)); // Winning bid is index 0
        when(listing.getListingCreator()).thenReturn(ACCOUNT);
        when(listing.getItemName()).thenReturn(ITEM_NAME);

        when(ongoingListingRepository.findById(LISTING_ID)).thenReturn(Optional.of(listing));

        listingService.endListing(LISTING_ID);

        verify(ongoingListingRepository).deleteById(LISTING_ID);
        verify(finishedListingRepository).save(argThat(finished ->
                finished.getBiddingWinner() != null &&
                        finished.getBiddingWinner().equals(winner)
        ));
    }

    @Test
    void givenNonExistingListing_whenEndListing_thenThrows() {
        when(ongoingListingRepository.findById(LISTING_ID)).thenReturn(Optional.empty());

        assertThrows(EntryNotFoundException.class, () -> listingService.endListing(LISTING_ID));

        verify(ongoingListingRepository, times(0)).deleteById(anyLong());
        verify(finishedListingRepository, times(0)).save(any(FinishedListing.class));
    }
}