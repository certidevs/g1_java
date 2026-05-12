package com.demo.model.enums;

public enum ScreenType {
    STANDARD("ESTÁNDAR"),
    IMAX("IMAX"),
    THREE_D("3D"),
    VIP("VIP");

    private final String label;

    ScreenType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
