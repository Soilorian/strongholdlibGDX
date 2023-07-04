package org.example.model.utils;


import java.io.Serializable;

public class PlayerToken implements Serializable {
    String hash;

    public PlayerToken(String hash) {
        this.hash = hash;
    }


    public String getHash() {
        return hash;
    }
}
