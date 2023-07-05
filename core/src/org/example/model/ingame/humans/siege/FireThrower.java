package org.example.model.ingame.humans.siege;

import org.example.control.Controller;
import org.example.model.ingame.castle.Empire;
import org.example.model.ingame.humans.army.Troop;
import org.example.model.ingame.humans.army.Troops;
import org.example.model.ingame.map.Map;
import org.example.model.ingame.map.Tile;

import java.io.Serializable;

public class FireThrower extends Troop implements SiegeWeapon, Serializable {
    public FireThrower(Empire empire, Tile tile) {
        super(Troops.FIRE_CATAPULT, empire, tile);
    }

    public void attack() {
        int currentX = getCurrentTile().getX();
        int currentY = getCurrentTile().getY();
        Map map = Controller.getCurrentMap();
        Tile tile;
        for (int i = 0; i <= getRange(); i++) {
            for (int j = 0; j <= i; j++) {
                if (map.isInRange(currentX + j, currentY + i - j)) {
                    tile = map.getTile(currentY + i - j, currentX + j);
                    if (checkBuilding(tile)) return;
                }
                if (map.isInRange(currentX + j, currentY - i + j)) {
                    tile = map.getTile(currentY - i + j, currentX + j);
                    if (checkBuilding(tile)) return;
                }
                if (map.isInRange(currentX - j, currentY + i - j)) {
                    tile = map.getTile(currentY + i - j, currentX + j);
                    if (checkBuilding(tile)) return;
                }
                if (map.isInRange(currentX - j, currentY - i + j)) {
                    tile = map.getTile(currentY - i + j, currentX - j);
                    if (checkBuilding(tile)) return;
                }
            }
        }
    }

    private boolean checkBuilding(Tile tile) {
        if (!tile.getBuilding().getOwner().equals(getKing())) {
            tile.getBuilding().takeDamage(getDamage());
            tile.getBuilding().setFire();
            return true;
        }
        return false;
    }
}