package org.example.model.ingame.castle;

import org.example.control.Controller;
import org.example.control.enums.GameMenuMessages;
import org.example.control.menucontrollers.GameMenuController;
import org.example.model.ingame.humans.army.Troop;
import org.example.model.ingame.humans.army.Troops;
import org.example.model.ingame.map.Map;
import org.example.model.ingame.map.Tile;

import java.io.Serializable;

public class Castle extends Building  implements Serializable {
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
        super.setTileUnder(Controller.getCurrentMap().getTile(y, x));
    }

    public void setLord() {
        lord = new Troop(Troops.LORD, empire, GameMenuController.getCurrentGame().getCurrentMap().getTile(y, x));
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
        Tile tile = getTileUnder();
        int currentX = tile.getX();
        int currentY = tile.getY();
        Map map = GameMenuController.getCurrentGame().getCurrentMap();
        for (int i = 0; i <= 5; i++) {
            for (int j = 0; j <= i; j++) {
                if (map.isInRange(currentX + j, currentY + i - j)) {
                    tile = map.getTile(currentY + i - j, currentX + j);
                    if (tile.getBuilding() == null && build(stockpile, tile) == GameMenuMessages.SUCCESS)
                        return;
                }
                if (map.isInRange(currentX + j, currentY - i + j)) {
                    tile = map.getTile(currentY - i + j, currentX + j);
                    if (tile.getBuilding() == null && build(stockpile, tile) == GameMenuMessages.SUCCESS)
                        return;

                }
                if (map.isInRange(currentX - j, currentY + i - j)) {
                    tile = map.getTile(currentY + i - j, currentX - j);
                    if (tile.getBuilding() == null && build(stockpile, tile) == GameMenuMessages.SUCCESS)
                        return;
                }
                if (map.isInRange(currentX - j, currentY - i + j)) {
                    tile = map.getTile(currentY - i + j, currentX - j);
                    if (tile.getBuilding() == null && build(stockpile, tile) == GameMenuMessages.SUCCESS)
                        return;
                }
            }
        }

    }
}
