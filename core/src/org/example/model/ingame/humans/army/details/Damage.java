package org.example.model.ingame.humans.army.details;

public enum Damage {
    VERY_LOW(840),
    LOW(1200),
    MEDIUM(1500),
    HIGH(2700),
    VERY_HIGH(4200);


    private final int damage;

    Damage(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }
}
