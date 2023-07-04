package org.example.control.enums;

public enum SettingMenuMessages {
    ;

    private final String message;


    SettingMenuMessages(String messages) {
        message = messages;
    }

    @Override
    public String toString() {
        return message;
    }
}
