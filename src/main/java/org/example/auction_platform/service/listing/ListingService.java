package org.example.auction_platform.service.listing;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.example.auction_platform.exception.AccountExistsException;
import org.example.auction_platform.exception.UserInvalidInputException;
import org.example.auction_platform.repository.account.AccountRepository;
import org.example.auction_platform.repository.account.entity.Account;
import org.example.auction_platform.repository.listing.FinishedListingRepository;
import org.example.auction_platform.repository.listing.OngoingListingRepository;
import org.example.auction_platform.repository.listing.entity.OngoingListing;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ListingService {

    private AccountRepository accountRepository;
    private FinishedListingRepository finishedListingRepository;
    private OngoingListingRepository ongoingListingRepository;

    public void createNewListing(@NonNull String creatorEmail,
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

        ongoingListingRepository.save(newListing);
    }
}
