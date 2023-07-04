package org.example.control.menucontrollers.inGameControllers;

import org.example.control.enums.GameMenuMessages;
import org.example.control.menucontrollers.GameMenuController;
import org.example.model.ingame.castle.Castle;
import org.example.model.ingame.humans.army.Troop;
import org.example.model.ingame.map.Map;
import org.example.model.ingame.map.Tile;

public class MapViewMenuController {
    private static final int MAX_ZOOM = 10;
    private static int viewingX;
    private static int viewingY;
    public static int zoom = 7;

    public static String printMap() {
        int x = viewingX;
        int y = viewingY;
        StringBuilder map = new StringBuilder();
        Map currentMap = GameMenuController.getCurrentGame().getCurrentMap();
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
        map.append("----").append("|");
        for (int i = 0; i < 9; i++) map.append("----------").append("|");
        map.append("----").append("\n");
        return map.toString();
    }

    private static String printHalfRow(Tile tile) {
        if (tile == null) return "⌧⌧";
        return tile.toString() + tile;
    }

    public static String printRow(Tile tile, int rowNum) {
        if (tile == null) return "⌧⌧⌧⌧⌧⌧";
        StringBuilder row = new StringBuilder();
        String pattern = tile.getTile().toString();
        switch (rowNum) {
            case 1 : {
                if (tile.getRock() != null) {
                    row.append(pattern);
                    pattern = tile.getRock().toString();
                    row.append(pattern).append(pattern).append(pattern);
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
                        if (troop.getTileDestination() != null) {
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
                } else row.append(tile.toString()).append(tile).append(tile);
                if (tile.isWall()) {
                    row.append(" ⌝");
                } else row.append(tile);
                break;
            }
            case 2 : {
                if (tile.getRock() != null) {
                    pattern = tile.getRock().toString();
                    row.append(pattern).append(pattern).append(pattern).append(pattern).append(pattern);
                } else {
                    row.append(pattern).append(pattern);
                    if (tile.getBuilding() != null) if (tile.getBuilding() instanceof Castle) pattern = "\uD83C\uDFF0";
                    else pattern = "⌂⌂";
                    else if (tile.getTree() != null) pattern = tile.getTree().toString();
                    row.append(pattern);
                    pattern = tile.getTile().toString();
                    row.append(pattern).append(pattern);
                }
                break;
            }
            case 3 : {
                if (tile.getRock() != null) {
                    row.append(pattern);
                    pattern = tile.getRock().toString();
                    row.append(pattern).append(pattern).append(pattern);
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
                } else row.append(tile).append(tile).append(tile);
                if (tile.isWall()) {
                    row.append(" ⟓");
                } else row.append(tile);
                break;
            }
            default : {
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

    public static String showDetails(Tile tile) {
        return showDetails(tile.getX(), tile.getY());
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

    public static int getZoom() {
        return zoom;
    }

    public static void changeViewingY(int i) {
        viewingY += i;
    }

    public static void changeViewingX(int i) {
        viewingX += i;
    }

    public static int getZoomPrime() {
        return MAX_ZOOM - zoom;
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
