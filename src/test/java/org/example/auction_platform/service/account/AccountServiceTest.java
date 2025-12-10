package org.example.auction_platform.service.account;

import org.example.auction_platform.repository.account.AccountRepository;
import org.example.auction_platform.repository.account.entity.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.awaitility.Awaitility.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.example.auction_platform.constants.TestConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    void givenNewData_whenAddAccount_thenSuccess()
    {
        accountService.addAccount(NAME, EMAIL);

        verify(accountRepository).save(any(Account.class));
    }

    @Test
    void givenExistingAccount_whenAddAccount_thenThrows()
    {
        when(accountRepository.findByEmail(EMAIL)).thenReturn(new Account());

        assertThrows(RuntimeException.class, () -> accountService.addAccount(NAME, EMAIL));

        verify(accountRepository, times(0)).save(any(Account.class));
    }

    @Test
    void givenExistingAccount_whenGetAccount_thenReturnAccount()
    {
        when(accountRepository.findByEmail(EMAIL)).thenReturn(ACCOUNT);

        assertEquals(ACCOUNT, accountService.getAccount(EMAIL).orElse(null));
    }

    @Test
    void givenNonExistingAccount_whenGetAccount_thenReturnNull()
    {
        assertNull(accountService.getAccount(EMAIL).orElse(null));
    }
}