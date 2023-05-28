package org.example.view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameStartUpMenuCommands {
    SELECT_SIZE("^select\\s+size(?: (-w) (?<Width>(\\S*)|(\"[^\"]*\"))()| (-h) (?<Height>(\\S*)|(\"[^\"]*\"))()){2}\\5\\10$"),
    ADD_PLAYER("^add player (-u) (?<Username>(\\S*)|(\"[^\"]*\"))$"),
    SELECT_COLOR("^select color (-c) (?<Color>(\\S*)|(\"[^\"]*\"))$"),
    SELECT_CASTLE("^select castle (-i) (?<Id>(\\S*)|(\"[^\"]*\"))$"),
    SELECT_MAP("^select map (-i) (?<Id>(\\S*)|(\"[^\"]*\"))$"),
    SHOW_MAPS("^show all maps$"),
    NEXT("^next$"),
    CANCEL("^cancel");
    private final Pattern pattern;

    GameStartUpMenuCommands(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    static public Matcher getMatcher(String input, GameStartUpMenuCommands command) {
        Matcher matcher = command.pattern.matcher(input);
        return matcher.matches() ? matcher : null;
    }
}
