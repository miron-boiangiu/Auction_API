package org.example.auction_platform.repository.account.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.auction_platform.repository.bid.entity.Bid;
import org.example.auction_platform.repository.listing.entity.FinishedListing;
import org.example.auction_platform.repository.listing.entity.Listing;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue
    private long id;
    @Column(unique = true)
    private String email;
    private String name;
    @OneToMany(mappedBy = "listingCreator")
    private List<Listing> listings;
    @OneToMany(mappedBy = "biddingWinner")
    private List<FinishedListing> wonListings;
    @OneToMany(mappedBy = "bidder")
    private List<Bid> bids;
}
