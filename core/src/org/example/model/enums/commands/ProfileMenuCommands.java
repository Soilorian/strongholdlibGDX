package org.example.model.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileMenuCommands {
    CHANGE_USERNAME("Username"),
    CHANGE_NICKNAME("Nickname"),
    CHANGE_PASSWORD("Password"),
    CHANGE_EMAIL("Email"),
    CHANGE_SLOGAN("Slogan"),
    REMOVE_SLOGAN("Remove");

    final Pattern pattern;

    ProfileMenuCommands(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    static public Matcher getMatcher(String input, ProfileMenuCommands command) {
        Matcher matcher = command.pattern.matcher(input);
        return matcher.matches() ? matcher : null;
    }
}
