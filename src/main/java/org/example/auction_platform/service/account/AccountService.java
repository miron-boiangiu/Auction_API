package org.example.auction_platform.service.account;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.example.auction_platform.exception.AccountExistsException;
import org.example.auction_platform.repository.account.AccountRepository;
import org.example.auction_platform.repository.account.entity.Account;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountService {

    private AccountRepository accountRepository;

    public void addAccount(@NonNull String name, @NonNull String email) {

        if (accountRepository.findByEmail(email) != null) {
            throw new AccountExistsException(String.format("Account with email <%s> already exists", email));
        }

        Account account = Account.builder()
                .name(name)
                .email(email)
                .build();

        accountRepository.save(account);
    }

    public Optional<Account> getAccount(@NonNull String email) {

        return Optional.ofNullable(accountRepository.findByEmail(email));
    }
}
