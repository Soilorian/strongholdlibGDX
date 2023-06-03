package org.example.model.ingame.map.enums;

import com.badlogic.gdx.graphics.Color;
import org.example.view.enums.ConsoleColors;

public enum TileTypes {
    GROUND("ground", ConsoleColors.BLACK, "OO", Color.GOLDENROD),
    GRAVEL_GROUND("gravel ground", ConsoleColors.BLACK_BACKGROUND_BRIGHT, "::", Color.GOLD),
    STONE("stone", ConsoleColors.BLACK_BACKGROUND, "##", Color.DARK_GRAY),
    ROCK("rock", ConsoleColors.BLACK, "&&", Color.GRAY),
    IRON_GROUND("iron ground", ConsoleColors.RED_BACKGROUND, "==", Color.LIGHT_GRAY),
    GRASS("grass", ConsoleColors.GREEN_BOLD, "-_", Color.CHARTREUSE),
    GRASS_LAND("grassland", ConsoleColors.GREEN_BACKGROUND, "$$", Color.LIME),
    DENSE_GRASS_LAND("dense grassland", ConsoleColors.GREEN_BACKGROUND_BRIGHT, "XX", Color.GREEN),
    SWAMP("swamp", ConsoleColors.PURPLE_BACKGROUND, "@@", Color.OLIVE),
    PLAIN("plain", ConsoleColors.GREEN_BOLD, "[]", Color.FOREST),
    SHALLOW_WATER("shallow water", ConsoleColors.CYAN, "%%", Color.SLATE),
    RIVER("river", ConsoleColors.CYAN_BRIGHT, "UU", Color.SKY),
    SMALL_POND("small pond", ConsoleColors.CYAN_BACKGROUND, "oo", Color.CYAN),
    BIG_POND("big pond", ConsoleColors.BLUE_BACKGROUND_BRIGHT, "/\\", Color.BLUE),
    BEACH("beach", ConsoleColors.YELLOW_BACKGROUND, ";\"", Color.TAN),
    SEA("sea", ConsoleColors.BLUE_BACKGROUND, "MW", Color.NAVY);

    private final String type;
    private final ConsoleColors colors;
    private final String symbol;
    private final Color color;

    TileTypes(String type, ConsoleColors color, String symbol, Color color1) {
        this.symbol = symbol;
        this.colors = color;
        this.type = type;
        this.color = color1;
    }

    public String getType() {
        return type;
    }

    public String toString() {
        return ConsoleColors.turnToColoredString(symbol, colors);
    }

}
