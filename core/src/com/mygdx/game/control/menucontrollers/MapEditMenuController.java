package org.example.control.menucontrollers;

import org.example.control.Controller;
import org.example.control.enums.MapEditorMenuMessages;
import org.example.model.DataBase;
import org.example.model.ingame.castle.Building;
import org.example.model.ingame.castle.Buildings;
import org.example.model.ingame.castle.Castle;
import org.example.model.ingame.humans.army.Troop;
import org.example.model.ingame.humans.army.Troops;
import org.example.model.ingame.map.Map;
import org.example.model.ingame.map.Tile;
import org.example.model.ingame.map.enums.Direction;
import org.example.model.ingame.map.enums.RockTypes;
import org.example.model.ingame.map.enums.TileTypes;
import org.example.model.ingame.map.enums.TreeTypes;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

import static org.example.control.menucontrollers.GameMenuController.*;
import static org.example.model.ingame.castle.Buildings.*;
import static org.example.model.ingame.humans.army.Troops.getTroopByName;
import static org.example.model.ingame.map.enums.TileTypes.*;

public class MapEditMenuController {
    private static TileTypes isTileTypeValid(String type) {
        TileTypes[] types = TileTypes.class.getEnumConstants();
        for (TileTypes tile : types) {
            if (tile.getType().equalsIgnoreCase(type)) {
                return tile;
            }
        }
        return null;
    }

    private static RockTypes isRockTypeValid(String type) {
        for (RockTypes rock : RockTypes.values()) if (rock.getType().equalsIgnoreCase(type)) return rock;
        return null;
    }

    private static TreeTypes isTreeTypeValid(String type) {
        TreeTypes[] types = TreeTypes.class.getEnumConstants();
        for (TreeTypes tree : types) {
            if (tree.getType().equalsIgnoreCase(type)) {
                return tree;
            }
        }
        return null;
    }

    static Direction isDirectionValid(String direction) {
        Direction[] types = Direction.class.getEnumConstants();
        for (Direction dir : types) {
            if (dir.getDirection().equalsIgnoreCase(direction)) {
                return dir;
            }
        }
        return null;
    }

    private static boolean isSea(TileTypes tile) {
        return switch (tile) {
            case SWAMP, PLAIN, SHALLOW_WATER, RIVER, SMALL_POND, BIG_POND, BEACH, SEA -> true;
            default -> false;
        };
    }

    private static boolean isRock(TileTypes tile) {
        return switch (tile) {
            case ROCK, STONE, IRON_GROUND -> true;
            default -> false;
        };
    }

    public static boolean isPlaceable(Buildings type, Tile build) {
        if (isSea(build.getTile()) || build.getTile().equals(ROCK)) {
            return false;
        }
        if ((build.getTile()).equals(STONE)) {
            return type.equals(QUARRY);
        } else if ((build.getTile()).equals(IRON_GROUND)) {
            return type.equals(IRON_MINE);
        } else if (type.equals(GRAIN_FARM) || type.equals(WHEAT_FARM)) {
            return (build.getTile()).equals(GRASS) || (build.getTile()).equals(DENSE_GRASS_LAND);
        }
        return true;
    }

    public static boolean checkPass(TileTypes tile) {
        if (isSea(tile) && !tile.equals(SHALLOW_WATER) && !tile.equals(SWAMP)) {
            return false;
        } else return !tile.equals(ROCK);
    }

    private static String checkEmpty(String width, String height, String type) {
        if (Controller.isFieldEmpty(width)) {
            return MapEditorMenuMessages.EMPTY_WIDTH.toString();
        } else if (Controller.isFieldEmpty(height)) {
            return MapEditorMenuMessages.EMPTY_HEIGHT.toString();
        } else if (Controller.isFieldEmpty(type)) {
            return MapEditorMenuMessages.EMPTY_TYPE.toString();
        }
        return null;
    }

    public static String setTexture(String width, String height, String type) {
        String result;
        if ((result = checkEmpty(width, height, type)) != null) {
            return result;
        }
        if (isCoordinatesNotDigit(width, height)) {
            return MapEditorMenuMessages.DIGIT_COORDINATES.toString();
        }
        int x = Integer.parseInt(width);
        int y = Integer.parseInt(height);
        if (isNotCoordinatesValid(x, y)) {
            return MapEditorMenuMessages.INVALID_COORDINATES.toString();
        }
        if (isThereBuilding(x, y)) {
            return MapEditorMenuMessages.ALREADY_BUILDING.toString();
        }
        TileTypes tileType;
        if ((tileType = isTileTypeValid(type)) == null) {
            return MapEditorMenuMessages.INVALID_TYPE.toString();
        }
        Map map = Controller.getCurrentMap();
        Tile tile = map.getTile(y, x);
        tile.setTile(tileType);
        tile.setPassable(checkPass(tileType));
        return MapEditorMenuMessages.SUCCEED.toString();
    }

    public static String setTextureRectangle(String width1, String height1, String width2, String height2, String type) {
        if (Controller.isFieldEmpty(width1) || Controller.isFieldEmpty(width2)) {
            return MapEditorMenuMessages.EMPTY_WIDTH.toString();
        } else if (Controller.isFieldEmpty(height1) || Controller.isFieldEmpty(height2)) {
            return MapEditorMenuMessages.EMPTY_HEIGHT.toString();
        } else if (Controller.isFieldEmpty(type)) {
            return MapEditorMenuMessages.EMPTY_TYPE.toString();
        }
        if (isCoordinatesNotDigit(width1, height1) || isCoordinatesNotDigit(width2, height2)) {
            return MapEditorMenuMessages.DIGIT_COORDINATES.toString();
        }
        int x1 = Integer.parseInt(width1);
        int y1 = Integer.parseInt(height1);
        int x2 = Integer.parseInt(width2);
        int y2 = Integer.parseInt(height2);
        if (isNotCoordinatesValid(x1, y1) || isNotCoordinatesValid(x2, y2)) {
            return MapEditorMenuMessages.INVALID_COORDINATES.toString();
        }
        if (isThereBuilding(x1, y1) || isThereBuilding(x2, y2)) {
            return MapEditorMenuMessages.ALREADY_BUILDING.toString();
        }
        TileTypes tileType;
        if ((tileType = isTileTypeValid(type)) == null) {
            return MapEditorMenuMessages.INVALID_TYPE.toString();
        }
        Map map = Controller.getCurrentMap();
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                Tile tile = map.getTile(j, i);
                tile.setTile(tileType);
                tile.setPassable(checkPass(tileType));
            }
        }
        return MapEditorMenuMessages.SUCCEED.toString();
    }

    public static String clear(String width, String height) {
        if (Controller.isFieldEmpty(width)) {
            return MapEditorMenuMessages.EMPTY_WIDTH.toString();
        } else if (Controller.isFieldEmpty(height)) {
            return MapEditorMenuMessages.EMPTY_HEIGHT.toString();
        }
        if (isCoordinatesNotDigit(width, height)) {
            return MapEditorMenuMessages.DIGIT_COORDINATES.toString();
        }
        int x = Integer.parseInt(width);
        int y = Integer.parseInt(height);
        if (isNotCoordinatesValid(x, y)) {
            return MapEditorMenuMessages.INVALID_COORDINATES.toString();
        }
        Map map = Controller.getCurrentMap();
        Tile tile = map.getTile(y, x);
        tile.setTile(GROUND);
        return MapEditorMenuMessages.SUCCEED.toString();
    }

    public static String dropRock(String width, String height, String direction, String type) {
        String result;
        if ((result = checkEmpty(width, height, type)) != null) {
            return result;
        } else if (Controller.isFieldEmpty(direction)) {
            return MapEditorMenuMessages.EMPTY_DIRECTION.toString();
        }
        if (isCoordinatesNotDigit(width, height)) {
            return MapEditorMenuMessages.DIGIT_COORDINATES.toString();
        }
        int x = Integer.parseInt(width);
        int y = Integer.parseInt(height);
        if (isNotCoordinatesValid(x, y)) {
            return MapEditorMenuMessages.INVALID_COORDINATES.toString();
        }
        Direction dir;
        if ((dir = isDirectionValid(direction)) == null) {
            return MapEditorMenuMessages.INVALID_DIRECTION.toString();
        }
        RockTypes rockType;
        if ((rockType = isRockTypeValid(type)) == null) {
            return MapEditorMenuMessages.INVALID_TYPE.toString();
        }
        Map map = Controller.getCurrentMap();
        Tile rock = map.getTile(y, x);
        if (isSea(rock.getTile())) {
            return MapEditorMenuMessages.INVALID_TILE_TYPE.toString();
        }
        rockType.setDirection(dir);
        rock.setRock(rockType);
        return MapEditorMenuMessages.SUCCEED.toString();
    }

    public static String dropTree(String width, String height, String type) {
        String result;
        if ((result = checkEmpty(width, height, type)) != null) {
            return result;
        }
        if (isCoordinatesNotDigit(width, height)) {
            return MapEditorMenuMessages.DIGIT_COORDINATES.toString();
        }
        int x = Integer.parseInt(width);
        int y = Integer.parseInt(height);
        if (isNotCoordinatesValid(x, y)) {
            return MapEditorMenuMessages.INVALID_COORDINATES.toString();
        }
        TreeTypes treeType;
        if ((treeType = isTreeTypeValid(type)) == null) {
            return MapEditorMenuMessages.INVALID_TYPE.toString();
        }
        Map map = Controller.getCurrentMap();
        Tile tree = map.getTile(y, x);
        if (isSea(tree.getTile()) || isRock(tree.getTile())) {
            return MapEditorMenuMessages.INVALID_TILE_TYPE.toString();
        }
        if ((tree.getTree() == null) && (tree.getRock() == null) && (tree.getBuilding() == null)) {
            return MapEditorMenuMessages.BAD_COORDINATES.toString();
        }
        tree.setTree(treeType);
        return MapEditorMenuMessages.SUCCEED.toString();
    }

    public static String dropBuilding(String width, String height, String type, String menu) {
        String result;
        if ((result = checkEmpty(width, height, type)) != null) {
            return result;
        }
        if (isCoordinatesNotDigit(width, height)) {
            return MapEditorMenuMessages.DIGIT_COORDINATES.toString();
        }
        int x = Integer.parseInt(width);
        int y = Integer.parseInt(height);
        if (isNotCoordinatesValid(x, y)) {
            return MapEditorMenuMessages.INVALID_COORDINATES.toString();
        }
        if (isThereBuilding(x, y)) {
            return MapEditorMenuMessages.ALREADY_BUILDING.toString();
        }
        Buildings buildings;
        if ((buildings = Buildings.getBuildingsEnumByName(type)) == null) {
            return MapEditorMenuMessages.INVALID_BUILDING.toString();
        }
        Map map = Controller.getCurrentMap();
        Tile tile = map.getTile(y, x);
        if (!isPlaceable(buildings, tile)) {
            return MapEditorMenuMessages.INVALID_TILE_TYPE.toString();
        }
        if (menu.equals("GameMenu")) {
            return null;
        } else {
            if (buildings.equals(KEEP)) Controller.getCurrentMap().addCastle((Castle) tile.getBuilding());
            else {
                Building building = new Building(buildings);
                tile.setBuilding(building);
            }
            return MapEditorMenuMessages.SUCCEED.toString();
        }
    }

    public static String dropUnit(String width, String height, String type, String count) {
        String result;
        if ((result = checkEmpty(width, height, type)) != null) {
            return result;
        } else if (Controller.isFieldEmpty(count)) {
            return MapEditorMenuMessages.EMPTY_COUNT.toString();
        }
        if (isCoordinatesNotDigit(width, height)) {
            return MapEditorMenuMessages.DIGIT_COORDINATES.toString();
        }
        int x = Integer.parseInt(width);
        int y = Integer.parseInt(height);
        if (isNotCoordinatesValid(x, y)) {
            return MapEditorMenuMessages.INVALID_COORDINATES.toString();
        }
        if (!EntranceMenuController.isDigit(count)) {
            return MapEditorMenuMessages.DIGIT_COUNT.toString();
        }
        Map map = Controller.getCurrentMap();
        Tile unit = map.getTile(y, x);
        if (isSea(unit.getTile()) || isRock(unit.getTile())) {
            return MapEditorMenuMessages.INVALID_TILE_TYPE.toString();
        }
        Troops troops;
        if ((troops = getTroopByName(type)) == null) {
            return MapEditorMenuMessages.INVALID_TROOPS.toString();
        }
        int amount = Integer.parseInt(count);
        Troop troop = new Troop(troops, DataBase.getNatural(), unit);
        for (int i = 1; i <= amount; i++) {
            unit.getTroops().add(troop);
        }
        return MapEditorMenuMessages.SUCCEED.toString();
    }

    public static MapEditorMenuMessages save(String id) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        if (DataBase.getMapById(id) != null)
            return MapEditorMenuMessages.ALREADY_EXISTS;
        Map currentMap = Controller.getCurrentMap();
        currentMap.setId(id);
        DataBase.addMap(currentMap);
        DataBase.updateMaps();
        return MapEditorMenuMessages.SUCCEED;
    }

    public static MapEditorMenuMessages dropCastle(int x, int y) {
        Map map = Controller.getCurrentMap();
        Tile build = map.getTile(y, x);
        if (isPlaceable(KEEP, build)) {
            if (!isThereBuilding(x, y)) {
                Building building = new Castle(x, y);
                build.setBuilding(building);
                map.getCastles().put(Controller.getCurrentMap().getCastles().size(), new Castle(x, y));
                return MapEditorMenuMessages.SUCCEED;
            }
        }
        return MapEditorMenuMessages.FAILED;
    }
}
