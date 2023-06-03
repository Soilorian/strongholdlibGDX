package org.example.model.ingame.map.enums;

import com.badlogic.gdx.graphics.Color;
import org.example.view.enums.ConsoleColors;

public enum TileTypes {
    GROUND("ground", ConsoleColors.BLACK, "OO", Color.GOLDENROD, "faze2-assets/"),
    GRAVEL_GROUND("gravel ground", ConsoleColors.BLACK_BACKGROUND_BRIGHT, "::", Color.GOLD, "faze2-assets/"),
    STONE("stone", ConsoleColors.BLACK_BACKGROUND, "##", Color.DARK_GRAY, "faze2-assets/"),
    ROCK("rock", ConsoleColors.BLACK, "&&", Color.SLATE, "faze2-assets/"),
    IRON_GROUND("iron ground", ConsoleColors.RED_BACKGROUND, "==", Color.RED, "faze2-assets/"),
    GRASS("grass", ConsoleColors.GREEN_BOLD, "-_", Color.CHARTREUSE, "faze2-assets/"),
    GRASS_LAND("grassland", ConsoleColors.GREEN_BACKGROUND, "$$", Color.LIME, "faze2-assets/"),
    DENSE_GRASS_LAND("dense grassland", ConsoleColors.GREEN_BACKGROUND_BRIGHT, "XX", Color.GREEN, "faze2-assets/"),
    SWAMP("swamp", ConsoleColors.PURPLE_BACKGROUND, "@@", Color.OLIVE, "faze2-assets/"),
    PLAIN("plain", ConsoleColors.GREEN_BOLD, "[]", Color.FOREST, "faze2-assets/"),
    SHALLOW_WATER("shallow water", ConsoleColors.CYAN, "%%", Color.SLATE, "faze2-assets/"),
    RIVER("river", ConsoleColors.CYAN_BRIGHT, "UU", Color.SKY, "faze2-assets/"),
    SMALL_POND("small pond", ConsoleColors.CYAN_BACKGROUND, "oo", Color.CYAN, "faze2-assets/"),
    BIG_POND("big pond", ConsoleColors.BLUE_BACKGROUND_BRIGHT, "/\\", Color.BLUE, "faze2-assets/"),
    BEACH("beach", ConsoleColors.YELLOW_BACKGROUND, ";\"", Color.TAN, "faze2-assets/"),
    SEA("sea", ConsoleColors.BLUE_BACKGROUND, "MW", Color.NAVY, "faze2-assets/");

    private final String type;
    private final ConsoleColors colors;
    private final String symbol;
    private final Color color;
    private final String textureAddress;

    TileTypes(String type, ConsoleColors color, String symbol, Color color1, String textureAddress) {
        this.symbol = symbol;
        this.colors = color;
        this.type = type;
        this.color = color1;
        this.textureAddress = textureAddress;
    }

    public String getType() {
        return type;
    }

    public String toString() {
        return ConsoleColors.turnToColoredString(symbol, colors);
    }

    public Color getColor() {
        return color;
    }
}
