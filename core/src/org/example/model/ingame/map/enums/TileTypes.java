package org.example.model.ingame.map.enums;

import com.badlogic.gdx.graphics.Color;
import org.example.view.enums.ConsoleColors;

public enum TileTypes {
    GROUND("ground", ConsoleColors.BLACK, "OO", Color.GREEN),
    GRAVEL_GROUND("gravel ground", ConsoleColors.BLACK_BACKGROUND_BRIGHT, "::", Color.GREEN),
    STONE("stone", ConsoleColors.BLACK_BACKGROUND, "##", Color.GREEN),
    ROCK("rock", ConsoleColors.BLACK, "&&", Color.GREEN),
    IRON_GROUND("iron ground", ConsoleColors.RED_BACKGROUND, "==", Color.GREEN),
    GRASS("grass", ConsoleColors.GREEN_BOLD, "-_", Color.GREEN),
    GRASS_LAND("grassland", ConsoleColors.GREEN_BACKGROUND, "$$", Color.GREEN),
    DENSE_GRASS_LAND("dense grassland", ConsoleColors.GREEN_BACKGROUND_BRIGHT, "XX", Color.GREEN),
    SWAMP("swamp", ConsoleColors.PURPLE_BACKGROUND, "@@", Color.GREEN),
    PLAIN("plain", ConsoleColors.GREEN_BOLD, "[]", Color.GREEN),
    SHALLOW_WATER("shallow water", ConsoleColors.CYAN, "%%", Color.GREEN),
    RIVER("river", ConsoleColors.CYAN_BRIGHT, "UU", Color.GREEN),
    SMALL_POND("small pond", ConsoleColors.CYAN_BACKGROUND, "oo", Color.GREEN),
    BIG_POND("big pond", ConsoleColors.BLUE_BACKGROUND_BRIGHT, "/\\", Color.GREEN),
    BEACH("beach", ConsoleColors.YELLOW_BACKGROUND, ";\"", Color.GREEN),
    SEA("sea", ConsoleColors.BLUE_BACKGROUND, "MW", Color.GREEN);

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
