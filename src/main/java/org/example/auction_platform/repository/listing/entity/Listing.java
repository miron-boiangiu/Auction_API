package org.example.auction_platform.repository.listing.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.auction_platform.repository.account.entity.Account;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public abstract class Listing {
    @Id
    @GeneratedValue
    protected long id;
    protected String itemName;
    @ManyToOne
    protected Account listingCreator;
}
