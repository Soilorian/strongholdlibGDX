package org.example.model.ingame.map.enums;

import org.example.view.enums.ConsoleColors;

public enum TreeTypes {
    DESERT_SHRUB("desert shrub", ConsoleColors.GREEN, "\uD83C\uDF40"),
    CHERRY_PALM("cherry palm", ConsoleColors.GREEN, "\uD83C\uDF38"),
    OLIVE_TREE("olive tree", ConsoleColors.GREEN, "\uD83C\uDF43"),
    COCONUT_PALM("coconut palm", ConsoleColors.GREEN, "\uD83C\uDF34"),
    DATES("dates", ConsoleColors.GREEN, "൹");

    private final String type;
    private final ConsoleColors colors;
    private final String symbol;

    TreeTypes(String type, ConsoleColors color, String symbol) {
        this.symbol = symbol;
        this.colors = color;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return ConsoleColors.turnToColoredString(symbol, colors);
    }
}
