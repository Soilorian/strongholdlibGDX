package org.example.model.exceptions;

import java.io.Serializable;

public class CoordinatesOutOfMap extends Exception  implements Serializable {
    public CoordinatesOutOfMap() {
        super("the coordinates are out of map");
    }
}
