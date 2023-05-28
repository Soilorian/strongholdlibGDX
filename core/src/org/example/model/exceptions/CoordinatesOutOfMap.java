package org.example.model.exceptions;

public class CoordinatesOutOfMap extends Exception {
    public CoordinatesOutOfMap() {
        super("the coordinates are out of map");
    }
}
