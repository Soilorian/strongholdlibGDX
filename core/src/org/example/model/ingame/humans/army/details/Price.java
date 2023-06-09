package org.example.model.ingame.humans.army.details;

import java.io.Serializable;

public enum Price implements Serializable {

    FREE(0),
    VERY_CHEAP(80),
    CHEAP(150),
    MEDIUM(200),
    EXPENSIVE(400),
    VERY_EXPENSIVE(800);


    private final int price;


    Price(int price) {
        this.price = price;
    }

    public int getGold() {
        return price;
    }
}
