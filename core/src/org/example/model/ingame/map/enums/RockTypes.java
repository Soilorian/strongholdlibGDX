package org.example.model.ingame.map.enums;

import com.badlogic.gdx.graphics.Texture;
import org.example.view.enums.ConsoleColors;

import java.util.ArrayList;
import java.util.List;

public enum RockTypes {
    BIG_ROCK("big rock", ConsoleColors.BLACK_BACKGROUND_BRIGHT, new String[]{"][", "⎶⎶"}),
    ROUND_STONE("round stone", ConsoleColors.BLACK_BACKGROUND_BRIGHT, new String[]{"○"}),
    TRIANGULAR("triangular", ConsoleColors.BLACK_BACKGROUND_BRIGHT, new String[]{"◁◁", "△△", "▽▽", "▷▷"}),
    ARTHUR_SWORD("arthur sword", ConsoleColors.CYAN_BRIGHT, new String[]{"⚔⚔"});

    private final String type;
    private final ConsoleColors colors;
    private final ArrayList<String> symbols = new ArrayList<>();
    private Direction direction;

    RockTypes(String type, ConsoleColors colors, String[] symbol) {
        this.type = type;
        this.colors = colors;
        symbols.addAll(List.of(symbol));
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return ConsoleColors.turnToColoredString(symbols.get(direction.getDedicatedNumber() % symbols.size()), colors);
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Texture getTexture() {
        return null;
    }
}
