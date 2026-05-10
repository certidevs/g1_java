package com.demo.model.enums;

public enum MinAge {
    ALL("All audiences"),
    AGE_7("+7"),
    AGE_12("+12"),
    AGE_16("+16"),
    AGE_18("+18");

    private final String label;

    MinAge(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}