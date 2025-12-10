package org.example.auction_platform.validator;

public class UserInputValidator {
    private UserInputValidator() {}

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
        return true;  // Whatever
    }

    public static boolean isValidValue(long startingValue) {
        return startingValue >= 0;
    }
}
