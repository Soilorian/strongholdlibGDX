package org.example.model.ingame.humans.army.details;

import java.io.Serializable;

public enum Range implements Serializable {
    VERY_LOW(0),
    LOW(5),
    MEDIUM(10),
    HIGH(20),
    VERY_HIGH(25),

    ;
    private final int range;

    Range(int range) {
        this.range = range;
    }

    public int getRange() {
        return range;
    }
}
