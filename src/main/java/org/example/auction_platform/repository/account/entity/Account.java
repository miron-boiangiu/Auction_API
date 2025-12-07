package org.example.auction_platform.repository.account.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

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
}
