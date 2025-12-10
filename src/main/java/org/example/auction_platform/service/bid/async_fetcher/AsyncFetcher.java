package org.example.auction_platform.service.bid.async_fetcher;

import org.example.auction_platform.repository.account.AccountRepository;
import org.example.auction_platform.repository.account.entity.Account;
import org.example.auction_platform.repository.listing.OngoingListingRepository;
import org.example.auction_platform.repository.listing.entity.OngoingListing;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class AsyncFetcher {

    @Async
    public CompletableFuture<Account> getAccountByEmail(String email, AccountRepository accountRepository){
        return CompletableFuture.completedFuture(accountRepository.findByEmail(email));
    }

    @Async
    public CompletableFuture<OngoingListing> getOngoingListingById(
            long id,
            OngoingListingRepository ongoingListingRepository
    ) {
        return CompletableFuture.completedFuture(ongoingListingRepository.findById(id).orElse(null));
    }
}
