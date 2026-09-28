package com.enigmacamp.challlcloanmanagement.entity.enums;

public enum EmploymentStatus {
    EMPLOYED,
    SELF_EMPLOYED,
    CONTRACT,
    UNEMPLOYED;

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
