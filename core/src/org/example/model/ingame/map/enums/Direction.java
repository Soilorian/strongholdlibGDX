package org.example.model.ingame.map.enums;

import com.badlogic.gdx.graphics.Pixmap;

public enum Direction {
    NORTH("north", 0),
    EAST("east", 1),
    SOUTH("south", 2),
    WEST("west", 3);
    private final String direction;
    private final int dedicatedNumber;

    Direction(String direction, int dedicatedNumber) {
        this.direction = direction;
        this.dedicatedNumber = dedicatedNumber;
    }

    public String getDirection() {
        return direction;
    }

    public int getDedicatedNumber() {
        return dedicatedNumber;
    }
}
