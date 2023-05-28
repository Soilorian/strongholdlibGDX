package org.example.model.ingame.humans.army.details;

public enum HP {
    VERY_LOW(500),
    LOW(1000),
    MEDIUM(5000),
    HIGH(10000),
    VERY_HIGH(50000);

    final int hp;

    HP(int hp) {
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }
}
