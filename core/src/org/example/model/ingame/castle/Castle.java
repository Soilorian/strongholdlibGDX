package org.example.model.ingame.castle;

import org.example.control.Server;
import org.example.model.ingame.humans.army.Troop;
import org.example.model.ingame.humans.army.Troops;
import org.example.model.ingame.map.Map;
import org.example.model.ingame.map.Tile;

public class Castle extends Building {
    private final int x;
    private final int y;
    private Empire empire;
    private Troop lord;

    public Castle(int x, int y) {
        super(Buildings.KEEP);
        this.x = x;
        this.y = y;
    }

    public void setTile() {
        super.setTileUnder(Server.getCurrentMap().getTile(y, x));
    }

    public void setLord() {
    }

    public Empire getEmpire() {
        return empire;
    }

    public void setEmpire(Empire empire) {
        this.empire = empire;
        super.setOwner(empire);
        empire.setCastle(this);
    }

    public void clearEmpire() {
        empire = null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Troop getLord() {
        return lord;
    }

    public void setStorages(Buildings stockpile) {

    }
}
