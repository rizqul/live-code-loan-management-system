package com.enigmacamp.challlcloanmanagement.entity.enums;

public enum AiRecommendation {
    APPROVE,
    REVIEW,
    REJECT,
    UNAVAILABLE;

    public static boolean isValid(String value) {
        if (value == null) {
            return false;
        }

        for (AiRecommendation aiRecommendation : AiRecommendation.values()) {
            if (aiRecommendation.name().equals(value)) {
                return true;
            }
        }

        return false;
    }
}
