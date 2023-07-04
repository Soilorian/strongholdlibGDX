package org.example.model.ingame.humans.siege;

import org.example.control.Controller;
import org.example.control.menucontrollers.GameMenuController;
import org.example.model.ingame.castle.Empire;
import org.example.model.ingame.humans.army.Troop;
import org.example.model.ingame.humans.army.Troops;
import org.example.model.ingame.map.Map;
import org.example.model.ingame.map.Tile;

public class SiegeTower extends Troop implements SiegeWeapon {
    public SiegeTower(Empire empire, Tile tile) {
        super(Troops.SIEGE_TOWER, empire, tile);
    }

    public void attack() {
        setSpeed(0);
        int currentX = getCurrentTile().getX();
        int currentY = getCurrentTile().getY();
        Troop enemy;
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
        if (!tile.getBuilding().getOwner().equals(getKing()) && tile.isWall()) {
            GameMenuController.getCurrentGame().getCurrentMap().dropStairOrSiegeTower(getCurrentTile().getX(), getCurrentTile().getY(), null);
            return true;
        }
        return false;
    }

    @Override
    public void kill() {
        GameMenuController.getCurrentGame().getCurrentMap().removeAndReconnectToPassables(getCurrentTile().getX(), getCurrentTile().getY());
    }
}