package org.example.auction_platform.repository.bid;

import org.example.auction_platform.repository.bid.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<Bid,Long> {
}
