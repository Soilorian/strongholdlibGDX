package org.example.model.ingame.map.enums;

import org.example.view.enums.ConsoleColors;

public enum TileTypes {
    GROUND("ground", ConsoleColors.BLACK, "OO"),
    GRAVEL_GROUND("gravel ground", ConsoleColors.BLACK_BACKGROUND_BRIGHT, "::"),
    STONE("stone", ConsoleColors.BLACK_BACKGROUND, "##"),
    ROCK("rock", ConsoleColors.BLACK, "&&"),
    IRON_GROUND("iron ground", ConsoleColors.RED_BACKGROUND, "=="),
    GRASS("grass", ConsoleColors.GREEN_BOLD, "-_"),
    GRASS_LAND("grassland", ConsoleColors.GREEN_BACKGROUND, "$$"),
    DENSE_GRASS_LAND("dense grassland", ConsoleColors.GREEN_BACKGROUND_BRIGHT, "XX"),
    SWAMP("swamp", ConsoleColors.PURPLE_BACKGROUND, "@@"),
    PLAIN("plain", ConsoleColors.GREEN_BOLD, "[]"),
    SHALLOW_WATER("shallow water", ConsoleColors.CYAN, "%%"),
    RIVER("river", ConsoleColors.CYAN_BRIGHT, "UU"),
    SMALL_POND("small pond", ConsoleColors.CYAN_BACKGROUND, "oo"),
    BIG_POND("big pond", ConsoleColors.BLUE_BACKGROUND_BRIGHT, "/\\"),
    BEACH("beach", ConsoleColors.YELLOW_BACKGROUND, ";\""),
    SEA("sea", ConsoleColors.BLUE_BACKGROUND, "MW");

    private final String type;
    private final ConsoleColors colors;
    private final String symbol;

    TileTypes(String type, ConsoleColors color, String symbol) {
        this.symbol = symbol;
        this.colors = color;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String toString() {
        return ConsoleColors.turnToColoredString(symbol, colors);
    }

}
