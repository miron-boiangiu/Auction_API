package org.example.auction_platform.repository.listing.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.auction_platform.repository.bid.entity.Bid;
import org.example.auction_platform.repository.listing.entity.visitor.ListingVisitor;
import org.hibernate.annotations.SQLOrder;

import java.util.List;

@Entity
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OngoingListing extends Listing {
    @OneToMany(mappedBy = "listing", cascade = CascadeType.REMOVE)
    @SQLOrder("value desc")
    private List<Bid> bids;
    protected long startingPrice;

    @Override
    public <R> R accept(ListingVisitor<R> visitor) {
        // "I am an OngoingListing, so I call the ongoing method"
        return visitor.visit(this);
    }
}
