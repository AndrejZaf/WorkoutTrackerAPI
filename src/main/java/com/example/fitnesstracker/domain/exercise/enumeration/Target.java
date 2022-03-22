package com.example.fitnesstracker.domain.exercise.enumeration;

public enum Target {
    GLUTES("Glutes"),
    ABDUCTORS("Abductors"),
    SERRATUS_ANTERIOR("Serratus Anterior"),
    LEVATOR_SCAPULAE("Levator Scapulae"),
    TRICEPS("Triceps"),
    LATS("Lats"),
    BICEPS("Biceps"),
    CARDIOVASCULAR_SYSTEM("Cardiovascular System"),
    FOREARMS("Forearms"),
    PECTORALS("Pectorals"),
    SPINE("Spine"),
    HAMSTRINGS("Hamstrings"),
    ADDUCTORS("Adductors"),
    DELTS("Delts"),
    QUADS("Quads"),
    CALVES("Calves"),
    ABS("Abs"),
    TRAPS("Traps"),
    UPPER_BACK("Upper Back");

    private final String target;

    Target(String target) {
        this.target = target;
    }
}
