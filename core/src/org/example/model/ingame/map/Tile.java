package org.example.model.ingame.map;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import org.example.control.Server;
import org.example.model.DataBase;
import org.example.model.ingame.castle.Building;
import org.example.model.ingame.castle.Colors;
import org.example.model.ingame.castle.Empire;
import org.example.model.ingame.castle.traps.Trap;
import org.example.model.ingame.humans.Peasant;
import org.example.model.ingame.humans.army.Troop;
import org.example.model.ingame.humans.army.Troops;
import org.example.model.ingame.humans.specialworkers.Tunneler;
import org.example.model.ingame.map.enums.RockTypes;
import org.example.model.ingame.map.enums.TileTypes;
import org.example.model.ingame.map.enums.TreeTypes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Tile implements Serializable {
    private final int x;
    private final int y;
    private final ArrayList<Peasant> peasants = new ArrayList<>();
    private final ArrayList<Troop> troops = new ArrayList<>();
    private final ArrayList<Tunneler> tunnelers = new ArrayList<>();
    private TileTypes tile;
    private int id;
    private Building building = null;
    private RockTypes rock = null;
    private TreeTypes tree = null;
    private boolean isPassable = true;
    private boolean isWall = false;
    private Trap trap = null;
    private boolean isTunnel = false;
    private String GameId;


    public void setGameId(String gameId) {
        GameId = gameId;
    }

    public String getGameId() {
        return GameId;
    }

    public Tile(int y, int x, TileTypes tileTypes) {
        this.tile = tileTypes;
        this.x = x;
        this.y = y;
    }

    public boolean isPassable() {
        return isPassable;
    }

    public void setPassable(boolean passable) {
            isPassable = passable;
            sendTile();
    }

    public void sendTile() {
//        try {
//            Server.getClient().sendPacket(this);
//        }
//        catch (Exception e) {
//            Main.getController().changeMenu(Menus.RECONNECTING_MENU.getMenu(), (Menu) Main.getController().getScreen());
//        }
    }



    public TileTypes getTile() {
        return tile;
    }

    public void setTile(TileTypes tile) {
        this.tile = tile;

    }

    public ArrayList<Troop> getTroops() {
        return troops;
    }

    public RockTypes getRock() {
        return rock;
    }

    public void setRock(RockTypes rock) {
        this.rock = rock;

    }

    public TreeTypes getTree() {
        return tree;
    }

    public void setTree(TreeTypes tree) {
        this.tree = tree;

    }


    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
        sendTile();
    }

    public boolean isWall() {
        return isWall;
    }

    public void setWall(boolean wall) {
        isWall = wall;
        sendTile();
    }

    public ArrayList<Peasant> getPeasants() {
        return peasants;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;

    }

    public void addPeasant(Peasant peasant) {
        peasants.add(peasant);
        sendTile();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String details() {
        StringBuilder stringBuilder = new StringBuilder(tile.getType());
        if (building != null) stringBuilder.append('\n').append(building.getBuildingName()).append(" , owner: ")
                .append(building.getOwner() == null ? building.getOwner().getColor() : "no one");
        HashMap<Colors, ArrayList<Troop>> troopsByEmpire;
        if ((troopsByEmpire = getPeasantsByEmpires()) != null) for (Colors colors : troopsByEmpire.keySet())
            stringBuilder.append("\n").append(colors).append(": ").append(troopsByEmpire.get(colors).size()).append(" peasants");
        if ((troopsByEmpire = getTroopsByEmpire()) != null) for (Colors colors : troopsByEmpire.keySet()) {
            stringBuilder.append("\n").append(colors).append(": ").append(troopsToList(troopsByEmpire.get(colors)));
        }
        if (trap != null && trap.getEmpire().equals(DataBase.getCurrentEmpire())) {
            stringBuilder.append("There is a ").append(trap.getName()).append(" here");
        }
        return stringBuilder.toString();
    }

    public HashMap<Colors, ArrayList<Troop>> getTroopsByEmpire() {
        HashMap<Colors, ArrayList<Troop>> result = new HashMap<>();
        for (Troop troop : troops) {
            if (result.containsKey(troop.getKing().getColor())) result.get(troop.getKing().getColor()).add(troop);
            else {
                ArrayList<Troop> value = new ArrayList<>();
                value.add(troop);
                result.put(troop.getKing().getColor(), value);
            }
        }
        if (result.isEmpty()) return null;
        return result;
    }

    private HashMap<Colors, ArrayList<Troop>> getPeasantsByEmpires() {
        HashMap<Colors, ArrayList<Troop>> result = new HashMap<>();
        for (Peasant peasant : peasants) {
            if (result.containsKey(peasant.getKing().getColor())) result.get(peasant.getKing().getColor()).add(peasant);
            else {
                ArrayList<Troop> value = new ArrayList<>();
                value.add(peasant);
                result.put(peasant.getKing().getColor(), value);
            }
        }
        if (result.isEmpty()) return null;
        return result;
    }

    private String troopsToList(ArrayList<Troop> troops) {
        HashMap<String, Integer> list = new HashMap<>();
        for (Troop troop : troops)
            if (list.containsKey(troop.getTroop().getName()))
                list.put(troop.getTroop().getName(), list.get(troop.getTroop().getName()) + 1);
            else list.put(troop.getTroop().getName(), 1);
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : list.keySet()) stringBuilder.append(s).append(": ").append(list.get(s));
        return stringBuilder.toString();
    }

    public void addTroop(Troop troop1) {
        troops.add(troop1);
        sendTile();
    }

    @Override
    public String toString() {
        return tile.toString();
    }

    public Trap getTrap() {
        return trap;
    }

    public void setTrap(Trap trap) {
        this.trap = trap;
        sendTile();
    }

    public boolean hasEnemies(Empire currentEmpire) {
        for (Troop troop : troops) {
            if (!troop.getKing().equals(currentEmpire))
                return true;
        }
        for (Peasant peasant : peasants) {
            if (!peasant.getKing().equals(currentEmpire))
                return true;
        }
        return false;
    }

    public ArrayList<Troop> getEnemies(Empire currentEmpire) {
        ArrayList<Troop> enemies = new ArrayList<>();
        for (Troop troop : troops) {
            if (!troop.getKing().equals(currentEmpire))
                enemies.add(troop);
        }
        for (Peasant peasant : peasants) {
            if (!peasant.getKing().equals(currentEmpire))
                enemies.add(peasant);
        }
        Troop king = null;
        for (Troop enemy : enemies) {
            if (enemy.getTroop().equals(Troops.LORD)) {
                king = enemy;
                break;
            }
        }
        if (king != null) {
            enemies.remove(king);
            enemies.add(king);
        }
        return enemies;
    }

    public boolean moveTunnel(Tile destination) {
        return false;
    }

    private void addTunnelers(ArrayList<Tunneler> tunnelers) {
        this.tunnelers.addAll(tunnelers);
        isTunnel = true;
        sendTile();
    }

    public Texture getTexture(int z) {
        return null;
    }

    public void addTunneler(Tunneler tunneler) {
        tunnelers.add(tunneler);
        sendTile();
    }

    public boolean isTunnel() {
        return isTunnel;
    }

    public void setTunnel(boolean tunnel) {
        isTunnel = tunnel;
        sendTile();
    }


}
