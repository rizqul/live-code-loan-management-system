package com.enigmacamp.challlcloanmanagement.entity.enums;

public enum AiStatus {

    PENDING,
    SUCCESS,
    FAILED;


    public static boolean isValid(String value) {
        if (value == null) {
            return false;
        }

        for (AiStatus aiStatus : AiStatus.values()) {
            if (aiStatus.name().equals(value)) {
                return true;
            }
        }

        return false;
    }
}
