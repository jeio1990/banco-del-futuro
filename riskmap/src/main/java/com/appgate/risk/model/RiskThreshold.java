package com.appgate.risk.model;

public enum RiskThreshold {

    BAD(0, 2, "RED"),
    REGULAR(3, 5, "YELLOW"),
    GOOD(6, 8, "ORANGE"),
    GREAT(9, 10, "GREEN");

    private final int min;
    private final int max;
    private final String warm;

    RiskThreshold(int min, int max, String warm) {
        this.min = min;
        this.max = max;
        this.warm = warm;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public String getWarm() {
        return warm;
    }
}
