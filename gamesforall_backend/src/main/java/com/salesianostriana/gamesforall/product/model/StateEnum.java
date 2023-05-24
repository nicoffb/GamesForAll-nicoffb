package com.salesianostriana.gamesforall.product.model;

public enum StateEnum {
    SinAbrir("Sin Abrir"),
    ComoNuevo("Como Nuevo"),
    Usado("Usado");


    private final String displayName;
    private final String value;

    StateEnum(String value) {
        this.displayName = this.name();
        this.value = value;
    }

    public static StateEnum fromString(String value) {
        for (StateEnum stateEnum : StateEnum.values()) {
            if (stateEnum.value.equalsIgnoreCase(value)) {
                return stateEnum;
            }
        }
        throw new IllegalArgumentException("Unknown state value: " + value);
    }

    public String getValue() {
        return value;
    }

}
