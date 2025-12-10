package org.example.auction_platform.constants;

import lombok.experimental.UtilityClass;
import org.example.auction_platform.repository.account.entity.Account;

@UtilityClass
public class TestConstants {

    public static final String ITEM_NAME = "Vintage Lamp";
    public static final long LISTING_ID = 1L;
    public static final long HIGH_BID_VALUE = 200L;
    public static final long LOW_BID_VALUE = 50L;
    public static final long STARTING_PRICE = 100L;
    public static final String NAME = "NAME";
    public static final String EMAIL = "EMAIL@email.com";
    public static final String WINNER_EMAIL = "ANOTHER_EMAIL@email.com";
    public static final Account ACCOUNT = Account.builder()
            .name(NAME)
            .email(EMAIL)
            .build();
}
