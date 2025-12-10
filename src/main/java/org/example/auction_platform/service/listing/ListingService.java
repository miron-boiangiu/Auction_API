package org.example.auction_platform.service.listing;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.example.auction_platform.exception.EntryNotFoundException;
import org.example.auction_platform.exception.UserInvalidInputException;
import org.example.auction_platform.repository.account.AccountRepository;
import org.example.auction_platform.repository.account.entity.Account;
import org.example.auction_platform.repository.listing.FinishedListingRepository;
import org.example.auction_platform.repository.listing.OngoingListingRepository;
import org.example.auction_platform.repository.listing.entity.FinishedListing;
import org.example.auction_platform.repository.listing.entity.Listing;
import org.example.auction_platform.repository.listing.entity.OngoingListing;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ListingService {

    private AccountRepository accountRepository;
    private FinishedListingRepository finishedListingRepository;
    private OngoingListingRepository ongoingListingRepository;

    public Listing createNewListing(@NonNull String creatorEmail,
                                 long startingPrice,
                                 String itemName) {

        Account listingCreator = accountRepository.findByEmail(creatorEmail);

        if (listingCreator == null) {
            throw new UserInvalidInputException(String.format("Account with email <%s> doesn't exist.", creatorEmail));
        }

        OngoingListing newListing = OngoingListing.builder()
                .startingPrice(startingPrice)
                .itemName(itemName)
                .listingCreator(listingCreator)
                .build();

        return ongoingListingRepository.save(newListing);
    }

    public Optional<Listing> getListing(long listingId) {
        return ongoingListingRepository.findById(listingId)
                .map(Listing.class::cast)
                .or(() -> finishedListingRepository.findById(listingId).map(Listing.class::cast));
    }

    public List<OngoingListing> getOngoingListings() {
        return ongoingListingRepository.findAll();
    }

    public List<FinishedListing> getFinishedListings() {
        return finishedListingRepository.findAll();
    }

    @Transactional
    public void endListing(long listingId) {

        OngoingListing listing = ongoingListingRepository.findById(listingId).orElse(null);

        if (listing == null) {
            throw new EntryNotFoundException("Listing not found.");
        }

        FinishedListing.FinishedListingBuilder<?, ?> newListing = FinishedListing.builder()
                        .listingCreator(listing.getListingCreator())
                        .itemName(listing.getItemName());

        if (!listing.getBids().isEmpty()) {
            newListing = newListing.biddingWinner(listing.getBids().get(0).getBidder());
        }

        ongoingListingRepository.deleteById(listingId);
        finishedListingRepository.save(newListing.build());
    }
}
