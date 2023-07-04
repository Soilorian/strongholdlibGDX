package org.example.control.enums;

public enum ProfileMenuMessages {
    //change Username
    EMPTY_USERNAME("Username's field is empty"),
    USERNAME_INVALID("Invalid username format"),
    USERNAME_ALREADY_EXISTS("Username already exists"),
    //change Nickname
    EMPTY_NICKNAME("Nickname's field is empty"),
    NICKNAME_EXISTS("Nickname already exists"),
    //change Email
    EMPTY_EMAIL("Email's field is empty"),
    INVALID_EMAIL("Invalid email format"),
    EMAIL_ALREADY_EXISTS("Email already exists"),
    //change Password
    EMPTY_OLD_PASSWORD("Old password's field is empty"),
    EMPTY_NEW_PASSWORD("New password's field is empty"),
    INCORRECT_PASSWORD("Incorrect password"),
    PASSWORD_ALREADY_EXISTS("Can't set old password as new password!"),
    //change Slogan
    EMPTY_SLOGAN("Slogan's field is empty"),
    SLOGAN_EXISTS("You already have this slogan bro"),
    SUCCEED("Succeed");

    private final String message;

    ProfileMenuMessages(String messages) {
        message = messages;
    }

    @Override
    public String toString() {
        return message;
    }
}
