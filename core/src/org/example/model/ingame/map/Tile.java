package org.example.model.ingame.map;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import org.example.Main;
import org.example.control.Controller;
import org.example.control.menucontrollers.GameMenuController;
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
import org.example.view.enums.Menus;
import org.example.view.menus.Menu;

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
//            Controller.getClient().sendPacket(this);
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
        Tile tile;
        Map currentMap = GameMenuController.getCurrentGame().getCurrentMap();
        if (destination.getY() != y) {
            if (destination.getY() > y)
                tile = currentMap.getTile(y + 1, x);
            else tile = currentMap.getTile(y - 1, x);
        } else if (destination.getX() > x) tile = currentMap.getTile(y, x + 1);
        else tile = currentMap.getTile(y, x - 1);
        tile.addTunnelers(tunnelers);
        tunnelers.clear();
        sendTile();
        return !tile.isTunnel();
    }

    private void addTunnelers(ArrayList<Tunneler> tunnelers) {
        this.tunnelers.addAll(tunnelers);
        isTunnel = true;
        sendTile();
    }

    public Texture getTexture(int z) {
        int width = Gdx.graphics.getWidth() / z;
        int height = Gdx.graphics.getHeight() / z;
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        Texture texture = Controller.resizer((float) Gdx.graphics.getWidth() / z, (float) Gdx.graphics.getHeight() / z,
                Controller.getTexture(tile.getTextureAddress()));
        if (!texture.getTextureData().isPrepared())
            texture.getTextureData().prepare();
        pixmap.drawPixmap(texture.getTextureData().consumePixmap(), 0, 0);
//        if (tree != null) {
//            Texture texture1 = tree.getTexture();
//            if (!texture1.getTextureData().isPrepared())
//                texture1.getTextureData().prepare();
//            Pixmap pixmap1 = texture1.getTextureData().consumePixmap();
//            pixmap.drawPixmap(pixmap1, texture.getWidth() / 2 - pixmap1.getWidth() / 2,
//                    texture.getHeight() / 2 - pixmap1.getHeight() / 2);
////            pixmap1.dispose();
//        }
//        if (rock != null) {
//            Texture texture1 = rock.getTexture();
//            if (!texture1.getTextureData().isPrepared())
//                texture1.getTextureData().prepare();
//            Pixmap pixmap1 = texture1.getTextureData().consumePixmap();
//            pixmap.drawPixmap(pixmap1, texture.getWidth() / 2 - pixmap1.getWidth() / 2,
//                    texture.getHeight() / 2 - pixmap1.getHeight() / 2);
////            pixmap1.dispose();
//        }
//        if (trap != null && DataBase.getCurrentEmpire().equals(trap.getEmpire())){
//            Texture texture1 = trap.getTexture();
//            if (!texture1.getTextureData().isPrepared())
//                texture1.getTextureData().prepare();
//            Pixmap pixmap1 = texture1.getTextureData().consumePixmap();
//            pixmap.drawPixmap(pixmap1, texture.getWidth() / 2 - pixmap1.getWidth() / 2,
//                    texture.getHeight() / 2 - pixmap1.getHeight() / 2);
////            pixmap1.dispose();
////        }
        if (building != null) {
            Texture texture1 = Controller.resizer(width, height, building.getTexture());
            if (!texture1.getTextureData().isPrepared()) {
                texture1.getTextureData().prepare();
            }
            Pixmap pixmap1 = texture1.getTextureData().consumePixmap();
            pixmap.drawPixmap(pixmap1, texture.getWidth() / 2 - pixmap1.getWidth() / 2,
                    texture.getHeight() / 2 - pixmap1.getHeight() / 2);
//            pixmap1.dispose();
        }
//        if (!peasants.isEmpty()) {
//            Texture texture1 = Controller.getPeseantTexture();
//            if (!texture1.getTextureData().isPrepared()) {
//                texture1.getTextureData().prepare();
//            }
//            Pixmap pixmap1 = texture1.getTextureData().consumePixmap();
//            pixmap.drawPixmap(pixmap1, texture.getWidth() - pixmap1.getWidth(),
//                    texture.getHeight() - pixmap1.getHeight());
////            pixmap1.dispose();
//        }
//        if (!troops.isEmpty()) {
//            for (int i = 0; i < troops.size(); i++) {
//                Troop troop = troops.get(i);
//                Texture texture1 = troop.getTexture();
//                if (!texture1.getTextureData().isPrepared()) {
//                    texture1.getTextureData().prepare();
//                }
//                Pixmap pixmap1 = texture1.getTextureData().consumePixmap();
//                pixmap.drawPixmap(pixmap1, i + 10, pixmap.getHeight()/2 - pixmap1.getHeight());
////                pixmap1.dispose();
//            }
//        }

        texture = new Texture(pixmap);
//        pixmap.dispose();
        return texture;
    }

//    private Pixmap addQuality(Pixmap pixmap, int x, int y, int a){
//        for (int i = 0; i < a; i++) {
//            for (int j = 0; j < a; j++) {
//                pixmap.setColor(tile.getColor().add(new Color(random.nextFloat(-0.15f, 0.3f),random.nextFloat(-0.15f,
//                        0.3f),random.nextFloat(-0.15f, 0.3f),random.nextFloat(-0.15f, 0.3f))));
//                pixmap.fillRectangle(x, y, 4, 4);
//            }
//        }
//        return pixmap;
//    }

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
