package org.example.model.ingame.map.enums;

import com.badlogic.gdx.graphics.Color;
import org.example.model.enums.ConsoleColors;

public enum TileTypes {
    GROUND("ground", ConsoleColors.BLACK, "OO", Color.GOLDENROD, "tiles/ground.jpg"),
    GRAVEL_GROUND("gravel ground", ConsoleColors.BLACK_BACKGROUND_BRIGHT, "::", Color.GOLD, "tiles/gravel-ground.png"),
    STONE("stone", ConsoleColors.BLACK_BACKGROUND, "##", Color.DARK_GRAY, "tiles/stone.png"),
    ROCK("rock", ConsoleColors.BLACK, "&&", Color.SLATE, "tiles/rock.png"),
    IRON_GROUND("iron ground", ConsoleColors.RED_BACKGROUND, "==", Color.RED, "tiles/iron-ground.png"),
    GRASS("grass", ConsoleColors.GREEN_BOLD, "-_", Color.CHARTREUSE, "tiles/grass-tile.jpg"),
    GRASS_LAND("grassland", ConsoleColors.GREEN_BACKGROUND, "$$", Color.LIME, "tiles/grass-land.png"),
    DENSE_GRASS_LAND("dense grassland", ConsoleColors.GREEN_BACKGROUND_BRIGHT, "XX", Color.GREEN, "tiles/dense-grass" +
            ".png"),
    SWAMP("swamp", ConsoleColors.PURPLE_BACKGROUND, "@@", Color.OLIVE, "tiles/swamp.png"),
    PLAIN("plain", ConsoleColors.GREEN_BOLD, "[]", Color.FOREST, "tiles/plain.png"),
    SHALLOW_WATER("shallow water", ConsoleColors.CYAN, "%%", Color.SLATE, "tiles/shallow-water.png"),
    RIVER("river", ConsoleColors.CYAN_BRIGHT, "UU", Color.SKY, "tiles/river.png"),
    SMALL_POND("small pond", ConsoleColors.CYAN_BACKGROUND, "oo", Color.CYAN, "tiles/gulf-tile.jpg"),
    BIG_POND("big pond", ConsoleColors.BLUE_BACKGROUND_BRIGHT, "/\\", Color.BLUE, "tiles/big-pound.jpg"),
    BEACH("beach", ConsoleColors.YELLOW_BACKGROUND, ";\"", Color.TAN, "tiles/beach.png"),
    SEA("sea", ConsoleColors.BLUE_BACKGROUND, "MW", Color.NAVY, "tiles/sea_tile.jpg");

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

    public String getTextureAddress() {
        return textureAddress;
    }
}
