package org.example.auction_platform.service.bid;

import lombok.AllArgsConstructor;
import org.example.auction_platform.exception.BidTooSmallException;
import org.example.auction_platform.exception.EntryNotFoundException;
import org.example.auction_platform.repository.account.AccountRepository;
import org.example.auction_platform.repository.account.entity.Account;
import org.example.auction_platform.repository.bid.BidRepository;
import org.example.auction_platform.repository.bid.entity.Bid;
import org.example.auction_platform.repository.listing.OngoingListingRepository;
import org.example.auction_platform.repository.listing.entity.OngoingListing;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BidService {

    private BidRepository bidRepository;
    private AccountRepository accountRepository;
    private OngoingListingRepository ongoingListingRepository;

    public void addBid(String email, long value, long listingId) {

        Account account = accountRepository.findByEmail(email);

        if (account == null) {
            throw new EntryNotFoundException(String.format("Account with email <%s> not found", email));
        }

        OngoingListing listing = ongoingListingRepository.findById(listingId).orElse(null);

        if (listing == null) {
            throw new EntryNotFoundException("Listing not found.");
        }

        if (!listing.getBids().isEmpty() && listing.getBids().get(0).getValue() >= value) {  // TODO: who cares about race conditions am i right
            throw new BidTooSmallException(String.format("The bid needs to be larger than %d.", listing.getBids().get(0).getValue()));
        }

        if (value < listing.getStartingPrice()) {
            throw new BidTooSmallException(String.format("The bid needs to be larger than %d.", listing.getStartingPrice()));
        }

        Bid newBid = Bid.builder()
                .value(value)
                .bidder(account)
                .listing(listing)
                .build();

        bidRepository.save(newBid);
    }
}
