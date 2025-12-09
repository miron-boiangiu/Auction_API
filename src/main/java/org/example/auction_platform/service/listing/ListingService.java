package org.example.auction_platform.service.listing;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.example.auction_platform.exception.AccountExistsException;
import org.example.auction_platform.exception.UserInvalidInputException;
import org.example.auction_platform.repository.account.AccountRepository;
import org.example.auction_platform.repository.account.entity.Account;
import org.example.auction_platform.repository.listing.FinishedListingRepository;
import org.example.auction_platform.repository.listing.OngoingListingRepository;
import org.example.auction_platform.repository.listing.entity.Listing;
import org.example.auction_platform.repository.listing.entity.OngoingListing;
import org.springframework.stereotype.Service;

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
}
