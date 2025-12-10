package org.example.auction_platform.validator;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserInputValidator {

    public static boolean isValidId(String id) {
        return id.matches("\\d+");
    }

    public static boolean isValidEmail(String email) {  // TODO: use regexes
        if (email == null) {
            return false;
        }

        if (email.isEmpty()) {
            return false;
        }

        if (!email.contains("@")) {
            return false;
        }

        return true;
    }

    public static boolean isValidName(String name) {
        if (name == null) {
            return false;
        }

        if (name.isEmpty()) {
            return false;
        }

        return true;
    }

    public static boolean isValidItemName(String itemName) {
        if (itemName == null) {
            return false;
        }

        if (itemName.isEmpty()) {
            return false;
        }

        return true;
    }

    public static boolean isValidValue(long startingValue) {
        return startingValue >= 0;
    }
}
