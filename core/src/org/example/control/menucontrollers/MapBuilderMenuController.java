package org.example.control.menucontrollers;

import org.example.control.Controller;
import org.example.model.ingame.castle.Castle;
import org.example.model.ingame.map.Map;
import org.example.model.ingame.map.Tile;
import org.example.model.ingame.map.enums.Direction;
import org.example.model.ingame.map.enums.RockTypes;
import org.example.model.ingame.map.enums.TileTypes;
import org.example.model.ingame.map.enums.TreeTypes;

import java.util.Random;

import static org.example.control.menucontrollers.GameMenuController.isThereBuilding;
import static org.example.control.menucontrollers.MapEditMenuController.checkPass;
import static org.example.control.menucontrollers.MapEditMenuController.isPlaceable;
import static org.example.model.ingame.castle.Buildings.KEEP;


public class MapBuilderMenuController {
    private static Map map = Controller.getCurrentMap();
    private static int width = map.getGroundWidth();
    private static int height = map.getGroundHeight();

    public void update(){
        map = Controller.getCurrentMap();
        width = map.getGroundWidth();
        height = map.getGroundHeight();
    }

    private static int dropTreeAndRock() {
        int amount, dif;
        if (width >= 200 && width < 300) {
            dif = 30;
            if (height >= 200 && height < 300) {
                amount = 10;
            } else {
                amount = 15;
            }
        } else if (width >= 300 && width < 400) {
            dif = 50;
            if (height >= 300 && height < 400) {
                amount = 20;
            } else {
                amount = 25;
            }
        } else {
            amount = 30;
            dif = 70;
        }
        for (int i = 1; i <= amount; i++) {
            int x = (int) Math.floor(Math.random() * (width));
            int y = (int) Math.floor(Math.random() * (height));
            int pick = new Random().nextInt(TreeTypes.values().length);
            TreeTypes type = TreeTypes.values()[pick];
            Tile tree = map.getTile(validateHeight(y), validateWidth(x));
            tree.setTree(type);
        }
        for (int i = 1; i <= amount; i++) {
            int x = (int) Math.floor(Math.random() * (width));
            int y = (int) Math.floor(Math.random() * (height));
            int pick = new Random().nextInt(RockTypes.values().length);
            RockTypes type = RockTypes.values()[pick];
            pick = new Random().nextInt(Direction.values().length);
            Direction dir = Direction.values()[pick];
            type.setDirection(dir);
            Tile rock = map.getTile(validateHeight(y), validateWidth(x));
            rock.setRock(type);

        }
        return dif;
    }

    private static void changeGround(int x1, int y1, int x2, int y2, TileTypes type) {
        for (int i = x1; i < x2; i++) {
            for (int j = y1; j < y2; j++) {
                Tile tile = map.getTile(validateHeight(j), validateWidth(i));
                tile.setTile(type);
                tile.setPassable(checkPass(type));
            }
        }
    }

    private static void createType(int dif, int max, int min, boolean flag) {
        int d;
        if (flag) {
            d = dif / 2;
        } else {
            d = (dif - 10) / 4;
        }
        int x = (int) Math.floor(Math.random() * (width));
        int y = (int) Math.floor(Math.random() * (height));
        if (x + d >= width) {
            x = x - d;
        }
        if (y + d >= height) {
            y = y - d;
        }
        int pick = new Random().nextInt(max - min + 1) + min;
        TileTypes type1 = TileTypes.values()[pick];
        changeGround(validateWidth(x), validateHeight(y), validateWidth(x + d), validateHeight(x + d), type1);
        int newX, newY;
        do {
            do {
                newX = (int) Math.floor(Math.random() * (width));
                newY = (int) Math.floor(Math.random() * (height));
            } while ((newX + d >= width) && (newY + d >= height));
        } while ((newX == x && newY == y));
        pick = new Random().nextInt(max - min + 1) + min;
        TileTypes type2 = TileTypes.values()[pick];
        changeGround(validateWidth(newX), validateHeight(newY), validateWidth(newX + d), validateHeight(newY + d), type2);
    }

    private static void buildCastle() {
        for (int i = 1; i <= 8; i++) {
            boolean flag = false;
            int x = (int) Math.floor(Math.random() * (width));
            int y = (int) Math.floor(Math.random() * (height));
            Tile tile = map.getTile(validateHeight(y), validateWidth(x));
            if (isPlaceable(KEEP, tile)) {
                if (!isThereBuilding(x, y)) {
                    Castle castle = new Castle(x, y);
                    tile.setBuilding(castle);
                    map.getCastles().put(i, castle);
                    flag = true;
                }
            }
            if (!flag) {
                i--;
            }
        }
    }

    public static void createRockLand() {
        int dif = dropTreeAndRock();
        createType(dif, 4, 2, true);
        createType(dif, 7, 5, false);
        createType(dif, 15, 8, false);
        buildCastle();
    }

    public static void createIsland() {
        int dif = dropTreeAndRock();
        createType(dif, 4, 2, false);
        createType(dif, 7, 5, false);
        createType(dif, 15, 8, true);
        buildCastle();
    }

    public static void createBaseLand() {
        int dif = dropTreeAndRock();
        createType(dif, 4, 2, false);
        createType(dif, 7, 5, true);
        createType(dif, 15, 8, false);
        buildCastle();
    }

    public static int validateHeight(int a) {
        if (a < 0) return 0;
        else return Math.min(a, map.getGroundHeight() - 1);
    }

    public static int validateWidth(int a) {
        if (a < 0) return 0;
        else return Math.min(a, map.getGroundWidth() - 1);
    }
}
