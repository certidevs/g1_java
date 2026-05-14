package com.demo.model.enums;

public enum Language {
    VO("VO - Version original (sin subtitulo)"),
    VOS("VOS - Version original subtitulada"),
    VOSE("VOSE - Version original subtitulada en español"),
    VOSI("VOSI - Version original subtitulada en ingles"),
    DOB("DOB - Doblada"),
    SUB("SUB - Subtitulada"),
    CAST("CAST - Castellano (Español de España)"),
    LAT("LAT - Latino"),
    ESP("ESP - Español (Generico)");

    private final String label;

    Language(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
