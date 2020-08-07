package com.nlmk.potapov.tm.enumerated;

public enum RoleType {

    USER("Пользователь"),
    ADMIN("Администратор");

    private final String displayName;

    RoleType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

}