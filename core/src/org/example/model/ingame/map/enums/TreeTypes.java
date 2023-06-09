package org.example.model.ingame.map.enums;

import com.badlogic.gdx.graphics.Texture;
import org.example.control.Controller;
import org.example.model.enums.ConsoleColors;

import java.io.Serializable;

public enum TreeTypes implements Serializable {
    CACTUS("cactus", ConsoleColors.GREEN, "\uD83C\uDF40", "trees/cactus1.png"),
    CHERRY_PALM("cherry palm", ConsoleColors.GREEN, "\uD83C\uDF38", "trees/cherry.png"),
    OLIVE_TREE("olive tree", ConsoleColors.GREEN, "\uD83C\uDF43", "trees/olive.png"),
    COCONUT_PALM("coconut palm", ConsoleColors.GREEN, "\uD83C\uDF34", "trees/coconut.png"),
    DATES("dates", ConsoleColors.GREEN, "൹", "trees/date.png"),
    DEAKHT("derakht", ConsoleColors.GREEN, "+", "trees/Derakht.png");

    private final String type;
    private final ConsoleColors colors;
    private final String symbol;
    private final String textureAddress;

    TreeTypes(String type, ConsoleColors color, String symbol, String textureAddress) {
        this.symbol = symbol;
        this.colors = color;
        this.type = type;
        this.textureAddress = textureAddress;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return ConsoleColors.turnToColoredString(symbol, colors);
    }

    public String getTextureAddress() {
        return textureAddress;
    }

    public Texture getTexture() {
        return null;
    }
}
