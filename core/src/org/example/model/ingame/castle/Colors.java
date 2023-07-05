package org.example.model.ingame.castle;

import com.badlogic.gdx.graphics.Color;

import java.io.Serializable;

public enum Colors  implements Serializable {
    YELLOW("yellow", Color.YELLOW),
    RED("red", Color.RED),
    BLACK("black", Color.BLACK),
    BLUE("blue", Color.BLUE),
    WHITE("white", Color.WHITE),
    GREEN("green", Color.GREEN),
    PURPLE("purple", Color.PURPLE),
    ORANGE("orange", Color.ORANGE),
    GRAY("gray", Color.GRAY),
    BROWN("brown", Color.BROWN);

    final String color;
    final Color comColor;

    Colors(String color, Color comColor) {
        this.color = color;
        this.comColor = comColor;
    }

    public static Colors getColor(String color) {
        color = color.toLowerCase();
         switch (color) {
             case "yellow" :return YELLOW;
            case "red" :return RED;
            case "black" :return BLACK;
            case "blue" :return BLUE;
            case "white" :return WHITE;
            case "green" :return GREEN;
            case "purple" :return PURPLE;
            case "orange" :return ORANGE;
            case "gray" :return GRAY;
            case "brown" :return BROWN;
            default :return null;
        }
    }

    public static String list() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Colors value : Colors.values()) {
            stringBuilder.append(value).append("\n");
        }
        return stringBuilder.toString();
    }

    public static Colors turnToColors(Color color) {
        for (Colors value : values()) {
            if (value.toColor().equals(color))
                return value;
        }
        return null;
    }

    @Override
    public String toString() {
        return color;
    }

    public Color toColor() {
        return comColor;
    }
}
