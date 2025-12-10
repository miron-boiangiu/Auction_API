package org.example.auction_platform.repository.listing.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.auction_platform.repository.account.entity.Account;
import org.example.auction_platform.repository.listing.entity.visitor.ListingVisitor;

@Entity
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FinishedListing extends Listing {
    @ManyToOne
    private Account biddingWinner;

    @Override
    public <R> R accept(ListingVisitor<R> visitor) {

        return visitor.visit(this);
    }
}
