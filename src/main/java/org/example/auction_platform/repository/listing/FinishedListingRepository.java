package org.example.auction_platform.repository.listing;

import org.example.auction_platform.repository.listing.entity.FinishedListing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinishedListingRepository extends JpaRepository<FinishedListing,Long> {
}
