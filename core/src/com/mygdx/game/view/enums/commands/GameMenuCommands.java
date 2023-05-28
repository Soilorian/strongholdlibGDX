package org.example.view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommands {
    SHOW_MAP("^show map$"),
    SHOW_POPULARITY_FACTORS("^show popularity factors$"),
    SHOW_POPULARITY("^show popularity$"),
    CHANGE_FEAR_RATE("^fear rate (-r) (?<Rate>(\\S*)|(\"[^\"]*\"))$"),
    SHOW_FEAR_RATE("^show\\s+fear\\s+rate$"),
    DROP_BUILDING("^drop\\s+building(?: (-x) (?<X>(\\S*)|(\"[^\"]*\"))()| (-y) (?<Y>(\\S*)|(\"[^\"]*\"))()|" +
            " (-t) (?<Type>(\\S*)|(\"[^\"]*\"))()| (-d) (?<Direction>(\\S*)|(\"[^\"]*\"))()){4}\\5\\10\\15\\20$"),
    SELECT_BUILDING("^select building(?: (-x) (?<X>(\\S*)|(\"[^\"]*\"))()| (-y) (?<Y>(\\S*)|(\"[^\"]*\"))()){2}\\5\\10$"),
    SELECT_UNIT("^select unit(?: (-x) (?<X>(\\S*)|(\"[^\"]*\"))()| (-y) (?<Y>(\\S*)|(\"[^\"]*\"))()){2}\\5\\10$"),
    MOVE_UNIT("^move unit to(?: (-x) (?<X>(\\S*)|(\"[^\"]*\"))()| (-y) (?<Y>(\\S*)|(\"[^\"]*\"))()){2}\\5\\10$"),
    PATROL_UNIT("^patrol\\s+unit(?: (-x) (?<X>(\\S*)|(\"[^\"]*\"))()| (-y) (?<Y>(\\S*)|(\"[^\"]*\"))()){2}\\5\\10$"),
    SET_STRATEGY("^set strategy (-s) (?<Strategy>(\\S*)|(\"[^\"]*\"))$"),
    ATTACK("^attack(?: (-x) (?<X>(\\S*)|(\"[^\"]*\"))()| (-y) (?<Y>(\\S*)|(\"[^\"]*\"))()| (?<option>-b)?()){2,3}\\5\\10$"),
    POUR_OIL("^pour oil (-d) (?<Direction>(\\S*)|(\"[^\"]*\"))$"),
    DIG_TUNNEL("^dig tunnel(?: (-x) (?<X>(\\S*)|(\"[^\"]*\"))()| (-y) (?<Y>(\\S*)|(\"[^\"]*\"))()){2}\\5\\10$"),
    BUILD_EQUIPMENT("^build (-q) (?<Type>(\\S*)|(\"[^\"]*\"))$"),
    DISBAND("^disband$"),
    REPAIR("^repair building$"),
    TRADE("^open trade menu$"),
    NEXT_TURN("^next turn$"),
    SHOW_PLAYER("^show player$"),
    OPEN_TRADE_MENU("^open trade menu$"),

    //CHEAT CODES qwer,
    GET_GOLD("^qwer (-g) (?<Amount>(\\S*)|(\"[^\"]*\"))$"),
    GET_ITEM("^qwer(?: (-i) (?<Item>(\\S*)|(\"[^\"]*\"))()| (-a) (?<Amount>(\\S*)|(\"[^\"]*\"))()){2}\\5\\10$"),
    SPAWN_TROOP("^qwer(?: (-t) (?<Troop>(\\S*)|(\"[^\"]*\"))()| (-a) (?<Amount>(\\S*)|(\"[^\"]*\"))()"
            + "| (-x) (?<X>(\\S*)|(\"[^\"]*\"))()| (-y) (?<Y>(\\S*)|(\"[^\"]*\"))()){4}\\5\\10\\15\\20$"),
    INFINITE_HEALTH("^qwer infinite health$"),
    CHOOSE_PLAYER("^qwer\\s+choose (-p) (?<Player>(\\S*)|(\"[^\"]*\"))$"),
    CHANGE_POPULARITY("^qwer (-p) (?<Popularity>(\\S*)|(\"[^\"]*\"))$");


    final Pattern pattern;

    GameMenuCommands(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    static public Matcher getMatcher(String input, GameMenuCommands command) {
        Matcher matcher = command.pattern.matcher(input);
        return matcher.matches() ? matcher : null;
    }
}
