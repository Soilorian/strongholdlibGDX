package org.example.model.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MapBuilderMenuCommands {
    ROCK_LAND("rock land"),
    ISLAND("island"),
    BASE_LAND("base land");

    final Pattern pattern;

    MapBuilderMenuCommands(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    static public Matcher getMatcher(String input, MapBuilderMenuCommands command) {
        Matcher matcher = command.pattern.matcher(input);
        return matcher.matches() ? matcher : null;
    }
}
