package org.example.model.ingame.humans.army.details;

import java.io.Serializable;

public enum Status implements Serializable {
    AT_WORK("at work"),
    ON_THE_WAY("on the way"),
    DELIVERING("delivering"),
    RECEIVING("receiving"),
    NOTHINGNESS("life feels so empty"),
    STANDING("standing"),
    NATURAL("natural"),
    DEFENSIVE("defensive"),
    OFFENSIVE("offensive"),
    DIGGING("digging"),
    SICK("sick"),
    ON_THE_WAY_BACK("going back to work");
    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
