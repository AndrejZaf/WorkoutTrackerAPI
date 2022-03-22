package com.example.fitnesstracker.domain.exercise.enumeration;

public enum BodyPart {
    UPPER_ARMS("Upper Arms"),
    UPPER_LEGS("Upper Legs"),
    WAIST("Waist"),
    NECK("Neck"),
    LOWER_ARMS("Lower Arms"),
    CHEST("Chest"),
    SHOULDERS("Shoulders"),
    CARDIO("Cardio"),
    LOWER_LEGS("Lower Legs"),
    BACK("Back");

    private final String bodyPart;

    BodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
    }
}
