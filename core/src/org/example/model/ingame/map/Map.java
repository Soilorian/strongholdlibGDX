package org.example.model.ingame.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import org.example.control.Controller;
import org.example.model.ingame.castle.Building;
import org.example.model.ingame.castle.Castle;
import org.example.model.ingame.castle.Empire;
import org.example.model.ingame.map.enums.Direction;
import org.example.model.ingame.map.enums.TileTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;


public class Map {
    public static int lastNumUsed = 1;
    private static Integer lastInteger = 1;
    private final HashMap<Integer, Castle> castles = new HashMap<>();
    private final int groundWidth;
    private final int groundHeight;
    private final Tile[][] tiles;
    ArrayList<HashSet<Integer>> graph;
    private String id;

    public Map(int groundWidth, int groundHeight, String id) {
        this.groundWidth = groundWidth;
        this.groundHeight = groundHeight;
        graph = new ArrayList<>(groundHeight * groundWidth);
        tiles = new Tile[groundHeight][groundWidth];
        for (int i = 0; i < groundHeight; i++)
            for (int j = 0; j < groundWidth; j++) tiles[i][j] = new Tile(i, j, TileTypes.GRASS);
        createNodes();
        convertMapToGraph();
        this.id = id;
    }

    public static String getLastNumUsed() {
        String str = String.valueOf(lastNumUsed);
        lastNumUsed++;
        return str;
    }

    public ArrayList<HashSet<Integer>> getGraph() {
        return graph;
    }

    public void createNodes() {
        for (int i = 0; i < groundWidth * groundHeight; i++) {
            graph.add(new HashSet<>());
            tiles[i / groundWidth][i % groundWidth].setId(i);
        }
    }

    public void convertMapToGraph() {
        for (int i = 0; i < groundHeight; i++)
            for (int j = 0; j < groundWidth; j++) {
                tiles[i][j].setId(i * groundWidth + j);
                if (!tiles[i][j].isPassable())
                    continue;
                checkSides(i, j);
            }
    }

    public void checkSides(int i, int j) {
        int mul = i * groundWidth + j;
        if (i - 1 >= 0 && tiles[i - 1][j].isPassable())
            graph.get(mul).add((i - 1) * groundWidth + j);
        if (i + 1 < groundHeight && tiles[i + 1][j].isPassable())
            graph.get(mul).add((i + 1) * groundWidth + j);
        if (j - 1 >= 0 && tiles[i][j - 1].isPassable())
            graph.get(mul).add(i * groundWidth + j - 1);
        if (j + 1 < groundWidth && tiles[i][j + 1].isPassable())
            graph.get(mul).add(i * groundWidth + j + 1);
    }

    public int convertToTileId(int x, int y) {
        return x * groundWidth + y;
    }

    public void addEdge(int x, int y, int x2, int y2) {
        int id1 = convertToTileId(x, y);
        int id2 = convertToTileId(x2, y2);
        graph.get(id1).add(id2);
        graph.get(id2).add(id1);
    }

    public void removeEdge(int x, int y, int x2, int y2) {
        int id1 = convertToTileId(x, y);
        int id2 = convertToTileId(x2, y2);
        graph.get(id1).remove(id2);
        graph.get(id2).remove(id1);
    }

    public void dropWall(int x, int y, Building building) {
        tiles[x][y].setPassable(false);
        tiles[x][y].setWall(true);
        tiles[x][y].setBuilding(building);
        removeEdges(x, y);
        checkWall(x, y);
    }

    private void checkWall(int x, int y) {
        if (x + 1 < groundHeight && tiles[x + 1][y].isWall())
            addEdge(x, y, x + 1, y);
        if (x - 1 >= 0 && tiles[x - 1][y].isWall())
            addEdge(x, y, x - 1, y);
        if (y - 1 >= 0 && tiles[x][y - 1].isWall())
            addEdge(x, y, x, y - 1);
        if (y + 1 < groundWidth && tiles[x][y + 1].isWall())
            addEdge(x, y, x, y + 1);
    }

    public void addGate(int x, int y, Direction direction) {
        openGate(x, y, direction);
        checkWall(x, y);
    }

    private void removeEdges(int x, int y) {
        if (x + 1 < groundHeight)
            removeEdge(x, y, x + 1, y);
        if (x - 1 >= 0)
            removeEdge(x, y, x - 1, y);
        if (y + 1 < groundWidth)
            removeEdge(x, y, x, y + 1);
        if (y - 1 >= 0)
            removeEdge(x, y, x, y - 1);
    }

    public void dropStairOrSiegeTower(int x, int y, Building building) {
        if (building != null)
            tiles[x][y].setBuilding(building);
        checkSidesForStair(x, y);
    }

    public void checkSidesForStair(int x, int y) {
        if (x + 1 < groundHeight && (tiles[x + 1][y].isWall() || tiles[x + 1][y].isPassable()))
            addEdge(x, y, x + 1, y);
        if (x - 1 >= 0 && (tiles[x - 1][y].isWall() || tiles[x - 1][y].isPassable()))
            addEdge(x, y, x - 1, y);
        if (y - 1 >= 0 && (tiles[x][y - 1].isWall() || tiles[x][y - 1].isPassable()))
            addEdge(x, y, x, y - 1);
        if (y + 1 < groundWidth && (tiles[x][y + 1].isWall() || tiles[x][y + 1].isPassable()))
            addEdge(x, y, x, y + 1);
    }

    public void removeAndReconnectToPassables(int x, int y) {
        removeEdges(x, y);
        checkSides(x, y);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Tile getTile(int height, int width) {
        if (height < 0 || width < 0 || height >= groundHeight || width >= groundWidth)
            return null;
        return tiles[height][width];
    }

    public HashMap<Integer, Castle> getCastles() {
        return castles;
    }

    @Override
    public String toString() {
        String[][] smaller = new String[groundHeight / 9][groundWidth / 9];
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < groundWidth; i += 9) stringBuilder.append("--");
        stringBuilder.replace(groundWidth / 9 - id.length() / 2, (int) (groundWidth / 9 + Math.ceil((double) id.length() / 2)), id);
        stringBuilder.append('\n');
        for (int i = 0; i < groundHeight / 9; i++)
            for (int j = 0; j < groundWidth / 9; j++) smaller[i][j] = tiles[i * 9][j * 9].toString();
        for (Integer integer : castles.keySet()) {
            Castle castle = castles.get(integer);
            smaller[Math.min((groundWidth / 9) - 1, castle.getY() / 9)][Math.min(castle.getX() / 9, (groundHeight / 9) - 1)] = integer + "\uD83C\uDFF0";
        }
        for (int i = 0; i < groundHeight / 9; i++) {
            for (int j = 0; j < groundWidth / 9; j++) stringBuilder.append(smaller[i][j]);
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }

    public void addCastle(Castle castle) {
        castles.put(lastInteger++, castle);
    }

    public int getGroundWidth() {
        return groundWidth;
    }

    public int getGroundHeight() {
        return groundHeight;
    }


    public boolean isInRange(int x, int y) {
        return x >= 0 && y >= 0 && x < groundWidth && y < groundHeight;
    }

    public void closeGate(int x, int y, Direction direction) {
        if (direction.getDedicatedNumber() % 2 == 0)
            removeEdge(x, y - 1, x, y + 1);
        else
            removeEdge(x - 1, y, x + 1, y);
    }

    public void openGate(int x, int y, Direction direction) {
        if (direction.getDedicatedNumber() % 2 == 0)
            addEdge(x, y - 1, x, y + 1);
        else
            addEdge(x - 1, y, x + 1, y);
    }

    public void destroyGate(int x, int y, Direction direction) {
        removeEdges(x, y);
        if (direction.getDedicatedNumber() % 2 == 0) {
            removeEdge(x, y - 1, x, y + 1);
            removeAndReconnectToPassables(x, y - 1);
            removeAndReconnectToPassables(x, y + 1);
        } else {
            removeEdge(x - 1, y, x + 1, y);
            removeAndReconnectToPassables(x - 1, y);
            removeAndReconnectToPassables(x + 1, y);
        }

    }

    public void destroyWall(int x, int y) {
        removeAndReconnectToPassables(x, y);
    }

    public void setUpPixmap(Pixmap pixmap, int i) {
        for (int j = 0; j < groundHeight; j++) {
            for (int k = 0; k < groundWidth; k++) {
                if (tiles[groundHeight - j - 1][k].getBuilding() != null) {
                    Empire owner = tiles[groundHeight - j - 1][k].getBuilding().getOwner();
                    if (owner == null || owner.getColor() == null)
                        pixmap.setColor(Color.DARK_GRAY);
                    else
                        pixmap.setColor(owner.getColor().toColor());
                } else if (!tiles[groundHeight - j - 1][k].getTroops().isEmpty()) {
                    pixmap.setColor(tiles[groundHeight - j - 1][k].getTroops().get(0).getKing().getColor().toColor());
                } else
                    pixmap.setColor(tiles[groundHeight - j - 1][k].getTile().getColor());
                pixmap.fillRectangle(k * i, (groundHeight - j - 1) * i, i, i);
            }
        }
        for (Integer integer : castles.keySet()) {
            Castle castle = castles.get(integer);
            Texture textureShield = Controller.getShield();
            if (!textureShield.getTextureData().isPrepared())
                textureShield.getTextureData().prepare();
            pixmap.drawPixmap(textureShield.getTextureData().consumePixmap(), i * castle.getX(), i * castle.getY());
            Texture textureNumber = Controller.getPictureOf(integer);
            if (!textureNumber.getTextureData().isPrepared())
                textureNumber.getTextureData().prepare();
            pixmap.drawPixmap(textureNumber.getTextureData().consumePixmap(), i * castle.getX(), i * castle.getY());
        }
    }

    public void clear() {
        for (int i = 0; i < groundHeight; i++)
            for (int j = 0; j < groundWidth; j++) tiles[i][j] = new Tile(i, j, TileTypes.GRASS);
        castles.clear();
    }

    public Integer getCastleNumber(Castle castle) {
        AtomicInteger index = new AtomicInteger(-1);
        castles.forEach( (x,y) -> {
            if (castle == y) {
                System.out.println("matched");
                index.set(x);
            }
        });
        return index.get();
    }
}
