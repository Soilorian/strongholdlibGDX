package org.example.model.ingame.humans.siege;

import org.example.control.Server;
import org.example.model.ingame.castle.Empire;
import org.example.model.ingame.humans.army.Troop;
import org.example.model.ingame.humans.army.Troops;
import org.example.model.ingame.map.Map;
import org.example.model.ingame.map.Tile;

public class CatapultWithWeight extends Troop implements SiegeWeapon {
    public CatapultWithWeight(Empire empire, Tile tile) {
        super(Troops.CATAPULT_WITH_WEIGHT, empire, tile);
    }

    public void attack() {
        setSpeed(0);
        int currentX = getCurrentTile().getX();
        int currentY = getCurrentTile().getY();
        Troop enemy;
        Map map = Server.getCurrentMap();
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
            return true;
        }
        return false;
    }
}
