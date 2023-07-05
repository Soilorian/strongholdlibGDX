package org.example.model.ingame.castle.details;

import java.io.Serializable;

public enum Durability  implements Serializable {

    VERY_LOW(5000),
    LOW(20000),
    MEDIUM(40000),
    HIGH(80000),
    VERY_HIGH(120000),
    INFINITY(Integer.MAX_VALUE);

    private final int hp;

    Durability(int hp) {
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }
}
