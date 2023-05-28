package org.example.view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MainMenuCommands {
    LOGOUT("^log out$"),
    START_GAME("^start new game$"),
    MAP_EDITOR("^map editor$"),
    SETTINGS("^settings$"),
    PROFILE("^profile$");

    final Pattern pattern;

    MainMenuCommands(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    static public Matcher getMatcher(String input, MainMenuCommands command) {
        Matcher matcher = command.pattern.matcher(input);
        return matcher.matches() ? matcher : null;
    }
}
