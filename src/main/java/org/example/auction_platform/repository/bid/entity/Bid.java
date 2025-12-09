package org.example.auction_platform.repository.bid.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.auction_platform.repository.account.entity.Account;
import org.example.auction_platform.repository.listing.entity.Listing;
import org.example.auction_platform.repository.listing.entity.OngoingListing;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bid {
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    private Account bidder;
    @ManyToOne
    private OngoingListing listing;
    private long value;
}
