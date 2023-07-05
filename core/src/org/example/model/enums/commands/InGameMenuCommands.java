package org.example.model.enums.commands;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum InGameMenuCommands implements Serializable {
    PAUSE("^pause$"),
    RESUME("^resume$"),
    RANDOM_SONG("^play random song$"),
    SHOW_SONGS("^show all songs$"),
    MOVE_MAP("^move map (-d) (?<Direction>(\\S*)|(\"[^\"]*\"))$"),
    SHOW_DETAILS("^show details(?: (-x) (?<X>(\\S*)|(\"[^\"]*\"))()| (-y) (?<Y>(\\S*)|(\"[^\"]*\"))()){2}\\5\\10$"),
    SHOW_FOODLIST("^show foodlist$"),
    CHANGE_FOOD_RATE("^food rate (-r) (?<Rate>(\\S*)|(\"[^\"]*\"))$"),
    SHOW_FOOD_RATE("^food rate show$"),
    CHANGE_TAX_RATE("^tax rate (-r) (?<Rate>(\\S*)|(\"[^\"]*\"))$"),
    SHOW_TAX_RATE("^tax rate show$"),
    CREATE_UNIT("^create unit(?: (-t) (?<Type>(\\S*)|(\"[^\"]*\"))()| (-c) (?<Count>(\\S*)|(\"[^\"]*\"))()){2}\\5\\10$"),
    REPAIR("^repair$"),
    SHOW_SHOP_PRICES("^show price list$"),
    SELL_ITEM("^sell(?: (-i) (?<Item>(\\S*)|(\"[^\"]*\"))()| (-a) (?<Amount>(\\S*)|(\"[^\"]*\"))()){2}\\5\\10$"),
    BUY_ITEM("^buy(?: (-i) (?<Item>(\\S*)|(\"[^\"]*\"))()| (-a) (?<Amount>(\\S*)|(\"[^\"]*\"))()){2}\\5\\10$"),
    SEND_NEW_TRADE("^trade(?: (-t) (?<Type>(\\S*)|(\"[^\"]*\"))()| (-a) (?<Amount>(\\S*)|(\"[^\"]*\"))()|" +
            " (-p) (?<Price>(\\S*)|(\"[^\"]*\"))()| (-m) (?<Message>(\\S*)|(\"[^\"]*\"))()){4}\\5\\10\\15\\20$"),
    TRADE_ACCEPT("^trade\\s+accept(?: (-i) (?<Id>(\\S*)|(\"[^\"]*\"))()| " +
            "(-m) (?<Message>(\\S*)|(\"[^\"]*\"))()){2}\\5\\10$"),
    SHOW_TRADE_LIST("^trade\\s+list$"),
    TRADE_HISTORY("^trade\\s+history$"),
    BACK("^back$"),
    showMeXY("^show\\s+map(?: (-x) (?<X>(\\S*)|(\"[^\"]*\"))()| (-y) (?<Y>(\\S*)|(\"[^\"]*\"))()){2}\\5\\10$");

    final Pattern pattern;

    InGameMenuCommands(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    static public Matcher getMatcher(String input, InGameMenuCommands command) {
        Matcher matcher = command.pattern.matcher(input);
        return matcher.matches() ? matcher : null;
    }
}
