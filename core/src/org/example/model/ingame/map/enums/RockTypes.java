package org.example.model.ingame.map.enums;

import com.badlogic.gdx.graphics.Texture;
import org.example.control.Controller;
import org.example.model.enums.ConsoleColors;

import java.util.ArrayList;
import java.util.List;

public enum RockTypes {
    BIG_ROCK("big rock", ConsoleColors.BLACK_BACKGROUND_BRIGHT, new String[]{"][", "⎶⎶"}, "rocks/collection70.png"),
    ROUND_STONE("round stone", ConsoleColors.BLACK_BACKGROUND_BRIGHT, new String[]{"○"}, "rocks/collection77.png"),
    TRIANGULAR("triangular", ConsoleColors.BLACK_BACKGROUND_BRIGHT, new String[]{"◁◁", "△△", "▽▽", "▷▷"}, "rocks/collection72.png"),
    ARTHUR_SWORD("arthur sword", ConsoleColors.CYAN_BRIGHT, new String[]{"⚔⚔"}, "rocks/aurthorsSword.png");

    private final String type;
    private final ConsoleColors colors;
    private final ArrayList<String> symbols = new ArrayList<>();
    private Direction direction;
    private final String address;

    RockTypes(String type, ConsoleColors colors, String[] symbol, String address) {
        this.type = type;
        this.colors = colors;
        this.address = address;
        symbols.addAll(List.of(symbol));
    }

    public String getType() {
        return type;
    }

//    @Override
//    public String toString() {
//        return ConsoleColors.turnToColoredString(symbols.get(direction.getDedicatedNumber() % symbols.size()), colors);
//    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Texture getTexture() {
        return null;
    }

    public String getTextureAddress() {
        return address;
    }
}
