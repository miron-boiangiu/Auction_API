package org.example.auction_platform.repository.account;

import org.example.auction_platform.repository.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByEmail(String email);
}
