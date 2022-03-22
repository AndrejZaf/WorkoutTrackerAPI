package com.example.fitnesstracker.domain.exercise.enumeration;

public enum Equipment {
    DUMBBELL("Dumbbell"),
    SMITH_MACHINE("Smith Machine"),
    MEDICINE_BALL("Medicine Ball"),
    STEPMILL_MACHINE("Stepmill Machine"),
    SKIERG_MACHINE("Skierg Machine"),
    RESISTANCE_BAND("Resistance Band"),
    WEIGHTED("Weighted"),
    UPPER_BODY_ERGOMETER("Upper Body Ergometer"),
    TIRE("Tire"),
    STATIONARY_BIKE("Stationary Bike"),
    ROPE("Rope"),
    TRAP_BAR("Trap Bar"),
    ASSISTED("Assisted"),
    LEVERAGE_MACHINE("Leverage Machine"),
    OLYMPIC_BARBELL("Olympic Barbell"),
    HAMMER("Hammer"),
    WHEEL_ROLLER("Wheel Roller"),
    SLED_MACHINE("Sled Machine"),
    KETTLEBELL("Kettlebell"),
    BOSU_BALL("Bosu Ball"),
    ELLIPTICAL_MACHINE("Elliptical Machine"),
    ROLLER("Roller"),
    EZ_BARBELL("Ez Barbell"),
    BAND("Band"),
    STABILITY_BALL("Stability Ball"),
    BARBELL("Barbell"),
    CABLE("Cable"),
    BODY_WEIGHT("Body Weight");

    private final String equipment;

    Equipment(String equipment) {
        this.equipment = equipment;
    }

}
