package org.example.control.enums;

public enum EntranceMenuMessages {
    EMAIL_ALREADY_EXISTS("email already exists try another one"),
    EMPTY_FIELD("you have an empty field"),
    EMPTY_USERNAME("username's field is empty"),
    EMPTY_NICKNAME("nickname's field is empty"),
    EMPTY_EMAIL("email's field is empty"),
    EMPTY_SLOGAN("slogan's field is empty"),
    EMPTY_PASSWORD("password's field is empty"),
    EMPTY_PASSWORD_CONFIRMATION("password confirmation's field is empty"),
    EMPTY_QUESTION_NUM("question number's field is empty"),
    EMPTY_ANSWER("answer's field is empty"),
    EMPTY_ANSWER_CONFIRMATION("answer confirmation's field is empty"),
    ENTER_CAPTCHA("enter captcha"),
    INCORRECT_PASSWORD("incorrect password"),
    INVALID_CONFIRMATION("invalid confirmation"),
    INVALID_EMAIL("invalid email format"),
    INVALID_QUESTION_NUMBER("invalid question number\nchoose a number between 1 and 3"),
    IS_DIGIT("question num is not digit"),
    LENGTH_PASSWORD("Password must contain at least 6 char"),
    LOWERCASE_PASSWORD("No lowercase letter in your password"),
    DIGIT_PASSWORD("No digit letter in your password"),
    SUCCEED("operation succeed"),
    SYMBOl_PASSWORD("No symbol in your password"),
    UPPERCASE_PASSWORD("No uppercase letter in your password"),
    USERNAME_ALREADY_EXISTS("username already exists try another one"),
    USERNAME_INVALID("invalid username format"),
    USERNAME_NOT_EXIST("there's no Player with this username"),
    WEAK_PASSWORD("Password is weak");


    private final String message;

    EntranceMenuMessages(String messages) {
        message = messages;
    }

    @Override
    public String toString() {
        return message;
    }
}
