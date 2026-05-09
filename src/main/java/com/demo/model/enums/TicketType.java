package com.demo.model.enums;

public enum TicketType {
    STANDARD ("Standard"),
    VIP("Vip"),
    PREMIUM ("Premium");

    private final String label;

    TicketType(String label){this.label = label;}

    public String getLabel(){ return label;}
}
