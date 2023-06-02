package org.example.view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum EntranceMenuCommands {
    CREATE_USER("^create user(?: (-u) (?<Username>(\\S*)|(\"[^\"]*\"))()| (-p) (?<Password>(\\S*)|(\"[^\"]*\"))()"
            + "| (-c) (?<PasswordConfirmation>(\\S*)|(\"[^\"]*\"))()| (-e) (?<Email>(\\S*)|(\"[^\"]*\"))()"
            + "| (-n) (?<Nickname>(\\S*)|(\"[^\"]*\"))()| (-s) (?<Slogan>(\\S*)|(\"[^\"]*\"))()){6}\\5\\10\\15\\20\\25\\30$"),
    CREATE_USER_WITHOUT_SLOGAN("^create user(?: (-u) (?<Username>(\\S*)|(\"[^\"]*\"))()"
            + "| (-p) (?<Password>(\\S*)|(\"[^\"]*\"))()| (-n) (?<Nickname>(\\S*)|(\"[^\"]*\"))()"
            + "| (-e) (?<Email>(\\S*)|(\"[^\"]*\"))()| (-c) (?<PasswordConfirmation>(\\S*)|(\"[^\"]*\"))()){5}\\5\\10\\15\\20\\25$"),
    CREATE_USER_WITH_RANDOM_PASSWORD("^create user(?: (-u) (?<Username>(\\S*)|(\"[^\"]*\"))()"
            + "| (-p) (?<Password>(\\S*)|(\"[^\"]*\"))()| (-n) (?<Nickname>(\\S*)|(\"[^\"]*\"))()"
            + "| (-e) (?<Email>(\\S*)|(\"[^\"]*\"))()| (-s) (?<Slogan>(\\S*)|(\"[^\"]*\"))()){5}\\5\\10\\15\\20\\25$"),
    FORGOT_PASSWORD("^forgot my password (-u) (?<Username>(\\S*)|(\"[^\"]*\"))$"),
    LOGIN("^login user(?: (-u) (?<Username>(\\S*)|(\"[^\"]*\"))()| (-p) (?<Password>(\\S*)|(\"[^\"]*\"))()){2}\\5\\100$"),
    LOGIN_STAY_IN("^login\\s+user(?: (-u) (?<Username>(\\S*)|(\"[^\"]*\"))()" +
            "| (-p) (?<Password>(\\S*)|(\"[^\"]*\"))()| (-L)(?<StayLoggedIn>(\\S*)|(\"[^\"]*\"))()){3}\\5\\10\\15$"),
    NEW_CAPTCHA("^new captcha$"),
    PICK_QUESTION("^pick question(?: (-q) (?<QuestionNumber>(\\S*)|(\"[^\"]*\"))()"
            + "| (-a) (?<Answer>(\\S*)|(\"[^\"]*\"))()| (-c) (?<AnswerConfirmation>(\\S*)|(\"[^\"]*\"))()){3}\\5\\10\\15$"),
    CHANGE_PASSWORD("^change\\s+password(?: (-p) (?<Password>(\\S*)|(\"[^\"]*\"))()| (-c) (?<Confirmation>(\\S*)|(\"[^\"]*\"))()){2}\\5\\10$"),
    EXIT("^exit$"),
    ;
    private final Pattern pattern;

    EntranceMenuCommands(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    static public Matcher getMatcher(String input, EntranceMenuCommands command) {
        Matcher matcher = command.pattern.matcher(input);
        return matcher.matches() ? matcher : null;
    }
}
