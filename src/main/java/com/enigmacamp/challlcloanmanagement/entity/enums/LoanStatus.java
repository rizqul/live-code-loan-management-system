package com.enigmacamp.challlcloanmanagement.entity.enums;

public enum LoanStatus {

    PENDING,
    APPROVED,
    REJECTED;

    public static boolean isValid(String value) {
        if (value == null) {
            return false;
        }

        for (UserRole userRole : UserRole.values()) {
            if (userRole.name().equals(value)) {
                return true;
            }
        }

        return false;
    }
}
