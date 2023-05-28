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
        return switch (color) {
            case "yellow" -> YELLOW;
            case "red" -> RED;
            case "black" -> BLACK;
            case "blue" -> BLUE;
            case "white" -> WHITE;
            case "green" -> GREEN;
            case "purple" -> PURPLE;
            case "orange" -> ORANGE;
            case "gray" -> GRAY;
            case "brown" -> BROWN;
            default -> null;
        };
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
