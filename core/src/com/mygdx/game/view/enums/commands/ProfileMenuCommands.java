package org.example.view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileMenuCommands {
    CHANGE_USERNAME("^change profile (-u) (?<Username>(\\S*)|(\"[^\"]*\"))$"),
    CHANGE_NICKNAME("^change profile (-n) (?<Nickname>(\\S*)|(\"[^\"]*\"))$"),
    CHANGE_PASSWORD("^change profile password(?: (-o) (?<OldPassword>(\\S*)|(\"[^\"]*\"))()| (-n) (?<NewPassword>(\\S*)|(\"[^\"]*\"))()){2}\\5\\10$"),
    CHANGE_EMAIL("^change profile (?<Email>(\\S*)|(\"[^\"]*\"))$"),
    CHANGE_SLOGAN("^change profile (-s) (?<Slogan>(\\S*)|(\"[^\"]*\"))$"),
    REMOVE_SLOGAN("^remove profile slogan$"),
    DISPLAY_PROFILE("^display profile(?<Option1> -o (?<Field>\\S*))?$");

    final Pattern pattern;

    ProfileMenuCommands(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    static public Matcher getMatcher(String input, ProfileMenuCommands command) {
        Matcher matcher = command.pattern.matcher(input);
        return matcher.matches() ? matcher : null;
    }
}
