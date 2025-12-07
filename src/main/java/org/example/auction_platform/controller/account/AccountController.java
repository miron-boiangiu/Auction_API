package org.example.auction_platform.controller.account;

import lombok.AllArgsConstructor;
import org.example.auction_platform.controller.account.request.AddAccountRequest;
import org.example.auction_platform.controller.account.request.GetAccountRequest;
import org.example.auction_platform.controller.account.response.GetAccountResponse;
import org.example.auction_platform.exception.UserInvalidInputException;
import org.example.auction_platform.repository.account.entity.Account;
import org.example.auction_platform.service.account.AccountService;
import org.example.auction_platform.validator.UserInputValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/account")
@AllArgsConstructor
public class AccountController {

    private AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addAccount(@RequestBody AddAccountRequest request) {

        if(!UserInputValidator.isValidEmail(request.getEmail())) {
            throw new UserInvalidInputException("Invalid email.");
        }

        if(!UserInputValidator.isValidName(request.getName())) {
            throw new UserInvalidInputException("Invalid name.");
        }

        accountService.addAccount(request.getName(), request.getEmail());
    }

    @GetMapping
    public ResponseEntity<GetAccountResponse> getAccount(@RequestBody GetAccountRequest request) {

        if(!UserInputValidator.isValidEmail(request.getEmail())) {
            throw new UserInvalidInputException("Invalid email.");
        }

        Optional<Account> account = accountService.getAccount(request.getEmail());

        if (account.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        GetAccountResponse response = GetAccountResponse.builder()
                .name(account.get().getName())
                .email(account.get().getEmail())
                .build();

        return ResponseEntity.ok(response);
    }
}
