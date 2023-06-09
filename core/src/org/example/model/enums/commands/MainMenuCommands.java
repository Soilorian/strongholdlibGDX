package org.example.model.enums.commands;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MainMenuCommands implements Serializable {
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
