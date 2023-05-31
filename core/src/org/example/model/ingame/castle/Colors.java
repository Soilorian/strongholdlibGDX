package org.example.model.ingame.castle;

public enum Colors {
    YELLOW("yellow"),
    RED("red"),
    BLACK("black"),
    BLUE("blue"),
    WHITE("white"),
    GREEN("green"),
    PURPLE("purple"),
    ORANGE("orange"),
    GRAY("gray"),
    BROWN("brown");

    final String color;

    Colors(String color) {
        this.color = color;
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

    @Override
    public String toString() {
        return color;
    }
}
