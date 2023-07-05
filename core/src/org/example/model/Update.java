package org.example.model;

import org.example.model.ingame.castle.Empire;
import org.example.model.ingame.map.Tile;

import java.io.Serializable;
import java.util.ArrayList;

public class Update implements Serializable {
    Empire empire;
    ArrayList<Tile> tiles = new ArrayList<>();
}
