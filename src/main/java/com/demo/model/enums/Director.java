package com.demo.model.enums;

public enum Director {
    FRANKEL("Frankel"),
    HORVATH("Horvath"),
    FUQUA("Fuqua"),
    MAZON("Mazón"),
    UNSPECIFIED("Unspecified");

    private final String label;

    Director(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
