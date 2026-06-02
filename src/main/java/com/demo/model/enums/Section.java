package com.demo.model.enums;

public enum Section {
    BILLBOARD("Cartelera"),
    FLUX("Emisiones Flux");

    private final String label;

    Section(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}