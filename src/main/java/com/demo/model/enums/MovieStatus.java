package com.demo.model.enums;

public enum MovieStatus {

    COMING_SOON("Próximamente"),
    PRE_SALES("Venta anticipada"),
    NEW_RELEASE("Estreno"),
    NOW_SHOWING("En cartelera"),
    LAST_DAYS("Últimos días"),
    FINISHED("Ya no disponible"),
    IN_VOTING("En votación"),
    VOTED("Votado");

    private final String label;

    MovieStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}