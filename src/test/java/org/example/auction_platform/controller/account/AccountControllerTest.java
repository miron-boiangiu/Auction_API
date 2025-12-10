package org.example.auction_platform.controller.account;

import org.example.auction_platform.controller.account.request.AddAccountRequest;
import org.example.auction_platform.controller.account.request.GetAccountRequest;
import org.example.auction_platform.controller.account.response.GetAccountResponse;
import org.example.auction_platform.exception.UserInvalidInputException;
import org.example.auction_platform.repository.account.entity.Account;
import org.example.auction_platform.service.account.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Incubating;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

// Assuming these are similar to your TestConstants, otherwise replace with literals
import static org.example.auction_platform.constants.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController controller;

    @Test
    public void givenValidData_whenAddAccount_thenCallService() {
        AddAccountRequest request = new AddAccountRequest();
        request.setName(NAME);
        request.setEmail(EMAIL);

        controller.addAccount(request);

        verify(accountService).addAccount(NAME, EMAIL);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", "invalid-email" })
    public void givenInvalidEmail_whenAddAccount_thenThrow(String email) {
        AddAccountRequest request = new AddAccountRequest();
        request.setName(NAME);
        request.setEmail(email);

        assertThrows(UserInvalidInputException.class,
                () -> controller.addAccount(request));

        verify(accountService, times(0)).addAccount(any(), any());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "" })
    public void givenInvalidName_whenAddAccount_thenThrow(String name) {
        AddAccountRequest request = new AddAccountRequest();
        request.setName(name);
        request.setEmail(EMAIL);

        assertThrows(UserInvalidInputException.class,
                () -> controller.addAccount(request));

        verify(accountService, times(0)).addAccount(any(), any());
    }

    @Test
    public void givenExistingAccount_whenGetAccount_thenReturnOk() {
        GetAccountRequest request = new GetAccountRequest();
        request.setEmail(EMAIL);

        Account mockAccount = mock(Account.class);
        when(mockAccount.getName()).thenReturn(NAME);
        when(mockAccount.getEmail()).thenReturn(EMAIL);

        when(accountService.getAccount(EMAIL)).thenReturn(Optional.of(mockAccount));

        ResponseEntity<GetAccountResponse> response = controller.getAccount(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(NAME, response.getBody().getName());
        assertEquals(EMAIL, response.getBody().getEmail());

        verify(accountService).getAccount(EMAIL);
    }

    @Test
    public void givenNonExistingAccount_whenGetAccount_thenReturnNotFound() {
        GetAccountRequest request = new GetAccountRequest();
        request.setEmail(EMAIL);

        when(accountService.getAccount(EMAIL)).thenReturn(Optional.empty());

        ResponseEntity<GetAccountResponse> response = controller.getAccount(request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(accountService).getAccount(EMAIL);
    }

    @Test
    public void givenInvalidEmail_whenGetAccount_thenThrow() {
        GetAccountRequest request = new GetAccountRequest();
        request.setEmail("invalid-email");

        assertThrows(UserInvalidInputException.class,
                () -> controller.getAccount(request));

        verify(accountService, times(0)).getAccount(any());
    }
}