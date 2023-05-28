package org.example.view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MapEditorMenuCommands {
    SET_TEXTURE("^set texture(?: (-x) (?<X>(\\S*)|(\"[^\"]*\"))()"
            + "| (-y) (?<Y>(\\S*)|(\"[^\"]*\"))()| (-t) (?<Type>(\\S*)|(\"[^\"]*\"))()){3}\\5\\10\\15$"),
    SET_TEXTURE_RECTANGLE("^set texture(?: (-x1) (?<FromX>(\\S*)|(\"[^\"]*\"))()| (-y1) (?<FromY>(\\S*)|(\"[^\"]*\"))()"
            + "| (-x2) (?<ToX>(\\S*)|(\"[^\"]*\"))()| (-y2) (?<ToY>(\\S*)|(\"[^\"]*\"))()"
            + "| (-t) (?<Type>(\\S*)|(\"[^\"]*\"))()){5}\\5\\10\\15\\20\\25$"),
    CLEAR("^clear(?: (-x) (?<X>(\\S*)|(\"[^\"]*\"))()| (-y) (?<Y>(\\S*)|(\"[^\"]*\"))()){2}\\5\\10$"),
    DROP_ROCK("^drop\\s+rock(?: (-x) (?<X>(\\S*)|(\"[^\"]*\"))()| (-y) (?<Y>(\\S*)|(\"[^\"]*\"))()|" +
            " (-t) (?<Type>(\\S*)|(\"[^\"]*\"))()| (-d) (?<Direction>(\\S*)|(\"[^\"]*\"))()){4}\\5\\10\\15\\20$"),
    DROP_TREE("^drop tree(?: (-x) (?<X>(\\S*)|(\"[^\"]*\"))()| (-y) (?<Y>(\\S*)|(\"[^\"]*\"))()"
            + "| (-t) (?<Type>(\\S*)|(\"[^\"]*\"))()){3}\\5\\10\\15$"),
    DROP_BUILDING("^drop building(?: (-x) (?<X>(\\S*)|(\"[^\"]*\"))()"
            + "| (-y) (?<Y>(\\S*)|(\"[^\"]*\"))()| (-t) (?<Type>(\\S*)|(\"[^\"]*\"))()){3}\\5\\10\\15$"),
    DROP_UNIT("^drop unit(?: (-x) (?<X>(\\S*)|(\"[^\"]*\"))()| (-y) (?<Y>(\\S*)|(\"[^\"]*\"))()"
            + "| (-t) (?<Type>(\\S*)|(\"[^\"]*\"))()| (-c) (?<Count>(\\S*)|(\"[^\"]*\"))()){4}\\5\\10\\15\\20$"),
    SAVE("^save (-i) (?<Id>(\\S*)|(\"[^\"]*\"))$"),
    NO("^no$"),
    BACK("^back$");

    final Pattern pattern;

    MapEditorMenuCommands(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    static public Matcher getMatcher(String input, MapEditorMenuCommands command) {
        Matcher matcher = command.pattern.matcher(input);
        return matcher.matches() ? matcher : null;
    }
}
