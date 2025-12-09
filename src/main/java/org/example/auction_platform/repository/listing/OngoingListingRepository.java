package org.example.auction_platform.repository.listing;

import org.example.auction_platform.repository.listing.entity.OngoingListing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OngoingListingRepository extends JpaRepository<OngoingListing,Long> {
}
