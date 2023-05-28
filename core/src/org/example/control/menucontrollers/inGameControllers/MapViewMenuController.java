package org.example.control.menucontrollers.inGameControllers;

import org.example.control.Controller;
import org.example.control.enums.GameMenuMessages;
import org.example.control.menucontrollers.GameMenuController;
import org.example.model.ingame.castle.Castle;
import org.example.model.ingame.humans.army.Troop;
import org.example.model.ingame.map.Map;
import org.example.model.ingame.map.Tile;

public class MapViewMenuController {
    private static int viewingX;
    private static int viewingY;

    public static String printMap() {
        int x = viewingX;
        int y = viewingY;
        StringBuilder map = new StringBuilder();
        Map currentMap = Controller.getCurrentMap();
        map.append(printHalfRow(currentMap.getTile(y - 3, x - 5))).append("|");
        for (int i = 0; i < 9; i++) map.append(printRow(currentMap.getTile(y - 3, x - 4 + i), 3)).append("|");
        map.append(printHalfRow(currentMap.getTile(y - 3, x + 5))).append("\n");
        map.append(printBorders());
        for (int i = 0; i < 5; i++) {
            for (int j = 1; j <= 3; j++) {
                map.append(printHalfRow(currentMap.getTile(y - 2 + i, x - 5))).append("|");
                for (int k = 0; k < 9; k++)
                    map.append(printRow(currentMap.getTile(y - 2 + i, x - 4 + k), j)).append("|");
                map.append(printHalfRow(currentMap.getTile(y - 2 + i, x + 5))).append("\n");
            }
            map.append(printBorders());
        }
        map.append(printHalfRow(currentMap.getTile(y + 3, x - 5))).append("|");
        for (int i = 0; i < 9; i++) map.append(printRow(currentMap.getTile(y + 3, x - 4 + i), 1)).append("|");
        map.append(printHalfRow(currentMap.getTile(y + 3, x + 5)));
        return map.toString();
    }

    private static String printBorders() {
        StringBuilder map = new StringBuilder();
        map.append("-".repeat(4)).append("|");
        for (int i = 0; i < 9; i++) map.append("-".repeat(10)).append("|");
        map.append("-".repeat(4)).append("\n");
        return map.toString();
    }

    private static String printHalfRow(Tile tile) {
        if (tile == null) return "⌧".repeat(2);
        return tile.toString().repeat(2);
    }

    public static String printRow(Tile tile, int rowNum) {
        if (tile == null) return "⌧⌧⌧⌧⌧⌧";
        StringBuilder row = new StringBuilder();
        String pattern = tile.getTile().toString();
        switch (rowNum) {
            case 1 -> {
                if (tile.getRock() != null) {
                    row.append(pattern);
                    pattern = tile.getRock().toString();
                    row.append(pattern.repeat(3));
                    pattern = tile.getTile().toString();
                    row.append(pattern);
                    return row.toString();
                }
                if (tile.isWall()) {
                    row.append("⟔ ");
                } else row.append(tile);

                if (!tile.getTroops().isEmpty()) {
                    for (Troop troop : tile.getTroops())
                        if (GameMenuController.isSiegeUnit(troop)) {
                            pattern = "\uD800\uDDDB\uD800";
                            break;
                        }
                    row.append(pattern);
                    pattern = tile.toString();
                    for (Troop troop : tile.getTroops())
                        if (troop.getDestination() != null) {
                            pattern = "\uD83C\uDFC3";
                            break;
                        }
                    row.append(pattern);
                    pattern = tile.toString();
                    for (Troop troop : tile.getTroops())
                        if (GameMenuController.isSiegeUnit(troop)) {
                            pattern = "\uD800\uDDDB\uD800\uDDDB";
                            break;
                        }
                    row.append(pattern);
                } else row.append(tile.toString().repeat(3));
                if (tile.isWall()) {
                    row.append(" ⌝");
                } else row.append(tile);
            }
            case 2 -> {
                if (tile.getRock() != null) {
                    pattern = tile.getRock().toString();
                    row.append(pattern.repeat(5));
                } else {
                    row.append(pattern.repeat(2));
                    if (tile.getBuilding() != null) if (tile.getBuilding() instanceof Castle) pattern = "\uD83C\uDFF0";
                    else pattern = "⌂⌂";
                    else if (tile.getTree() != null) pattern = tile.getTree().toString();
                    row.append(pattern);
                    pattern = tile.getTile().toString();
                    row.append(pattern.repeat(2));
                }
            }
            case 3 -> {
                if (tile.getRock() != null) {
                    row.append(pattern);
                    pattern = tile.getRock().toString();
                    row.append(pattern.repeat(3));
                    pattern = tile.getTile().toString();
                    row.append(pattern);
                    return row.toString();
                }
                if (tile.isWall()) {
                    row.append("⌞ ");
                } else row.append(tile);
                if (!tile.getTroops().isEmpty()) {
                    pattern = "\uD83E\uDDCD\uD83E\uDDCD\uD83E\uDDCD\uD83E\uDDCD";
                    row.append(pattern);
                } else row.append(tile.toString().repeat(3));
                if (tile.isWall()) {
                    row.append(" ⟓");
                } else row.append(tile);
            }
            default -> {
                return null;
            }
        }
        return row.toString();
    }

    public static String showDetails(int x, int y) {
        Map currentMap = GameMenuController.getCurrentGame().getCurrentMap();
        if (currentMap.isInRange(x, y))
            return currentMap.getTile(y, x).details();
        return GameMenuMessages.NOT_IN_RANGE.toString();
    }

    public static int getViewingX() {
        return viewingX;
    }

    public static void setViewingX(int viewingX) {
        MapViewMenuController.viewingX = viewingX;
    }

    public static int getViewingY() {
        return viewingY;
    }

    public static void setViewingY(int viewingY) {
        MapViewMenuController.viewingY = viewingY;
    }
}


/*
####|##########|##########|##########|##########|##########|##########|##########|##########|##########|####
----|----------|----------|----------|----------|----------|----------|----------|----------|----------|----
####|##########|##########|##########|##########|##########|##########|##########|##########|##########|####
####|##########|##########|##########|##########|##########|##########|##########|##########|##########|####
####|##########|##########|##########|##########|##########|##########|##########|##########|##########|####
----|----------|----------|----------|----------|----------|----------|----------|----------|----------|----
####|##########|##########|##########|##########|##########|##########|##########|##########|##########|####
####|##########|##########|##########|##########|##########|##########|##########|##########|##########|####
####|##########|##########|##########|##########|##########|##########|##########|##########|##########|####
----|----------|----------|----------|----------|----------|----------|----------|----------|----------|----
####|##########|##########|##########|⟔########⌝|@@@@@@@@@@|##########|##########|##########|##########|####
####|##########|##########|##########|##########|@@@@@@@@@@|##########|##########|##########|##########|####
####|##########|##########|##########|⌞########⟓|@@@@@@@@@@|##########|##########|##########|##########|####
----|----------|----------|----------|----------|----------|----------|----------|----------|----------|----
####|##########|##########|##########|##########|##########|##########|##########|##########|##########|####
####|##########|##########|##########|##########|##########|##########|##########|##########|##########|####
####|##########|##########|##########|##########|##########|##########|##########|##########|##########|####
----|----------|----------|----------|----------|----------|----------|----------|----------|----------|----
####|##########|##########|##########|##########|##########|##########|##########|##########|##########|####
####|##########|##########|##########|##########|##########|##########|##########|##########|##########|####
####|##########|##########|##########|##########|##########|##########|##########|##########|##########|####
----|----------|----------|----------|----------|----------|----------|----------|----------|----------|----
####|##########|##########|##########|##########|##########|##########|##########|##########|##########|####
 */
