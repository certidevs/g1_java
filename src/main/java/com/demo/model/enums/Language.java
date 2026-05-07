package com.demo.model.enums;

public enum Language {
    VO("Version original (sin sibtitulo)"),
    VOS("Version original subtitulada"),
    VOSE("Version original subtitulada en español"),
    VOSI("Version original subtitulada en ingles"),
    DOB("Doblada"),
    SUB("Subtitulada"),
    CAST("Castellano (Español de España)"),
    LAT("Latino"),
    ESP("Español (Generico)");

    private final String label;

    Language(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
