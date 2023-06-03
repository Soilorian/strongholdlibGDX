package org.example.control.enums;

public enum ProfileMenuMessages {
    //change Username
    EMPTY_USERNAME("Username's field is empty"),
    USERNAME_INVALID("Invalid username format"),
    USERNAME_ALREADY_EXISTS("Username already exists"),
    //change Nickname
    EMPTY_NICKNAME("Nickname's field is empty"),
    NICKNAME_EXISTS("you already have this nickname bro"),
    //change Email
    EMPTY_EMAIL("email's field is empty"),
    INVALID_EMAIL("invalid email format"),
    EMAIL_ALREADY_EXISTS("email already exists try another one"),
    //change Password
    EMPTY_OLD_PASSWORD("old password's field is empty"),
    EMPTY_NEW_PASSWORD("new password's field is empty"),
    INCORRECT_PASSWORD("incorrect password"),
    PASSWORD_ALREADY_EXISTS("you can't set your old password as new password!"),
    //change Slogan
    EMPTY_SLOGAN("slogan's field is empty"),
    SLOGAN_EXISTS("you already have this slogan bro"),
    SUCCEED("operation succeed"),
    WRONG_FIELD("there is no such field to show");

    private final String message;

    ProfileMenuMessages(String messages) {
        message = messages;
    }

    @Override
    public String toString() {
        return message;
    }
}
