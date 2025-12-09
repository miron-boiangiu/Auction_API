package org.example.auction_platform.repository.listing.entity.visitor;

import org.example.auction_platform.repository.listing.entity.FinishedListing;
import org.example.auction_platform.repository.listing.entity.OngoingListing;

public interface ListingVisitor<R> {
    R visit(OngoingListing ongoingListing);
    R visit(FinishedListing finishedListing);
}