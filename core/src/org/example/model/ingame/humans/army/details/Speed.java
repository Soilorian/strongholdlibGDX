package org.example.model.ingame.humans.army.details;

import java.io.Serializable;

public enum Speed  implements Serializable {
    VERY_SLOW(1),
    SLOW(2),
    MEDIUM(3),
    FAST(4),
    VERY_FAST(5),
    ;
    final int speed;

    Speed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }
}
