package com.salesianostriana.gamesforall.product.model;

public enum PlatformEnum {
    PS5("Playstation 5"),
    PS4("Playstation 4"),
    XBOX("Xbox"),
    SWITCH("Nintendo Switch"),
    PC("PC");

    private final String displayName;
    private final String value;

    PlatformEnum(String value) {
        this.displayName = this.name();
        this.value = value;
    }

    public static PlatformEnum fromString(String value) {
        for (PlatformEnum platformEnum : PlatformEnum.values()) {
            if (platformEnum.value.equalsIgnoreCase(value)) {
                return platformEnum;
            }
        }
        throw new IllegalArgumentException("Unknown platform value: " + value);
    }

    public String getValue() {
        return value;
    }

    public String getDisplayName() {
        return displayName;
    }
}
