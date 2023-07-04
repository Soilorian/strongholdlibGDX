package org.example.model.ingame.humans.army;


import com.badlogic.gdx.graphics.Texture;
import org.example.control.Server;
//import org.example.control.menucontrollers.GameMenuController;
import org.example.model.ingame.castle.Building;
import org.example.model.ingame.castle.Empire;
import org.example.model.ingame.humans.army.details.Status;
import org.example.model.ingame.map.Map;
import org.example.model.ingame.map.Tile;
import org.example.model.ingame.map.enums.TileTypes;

import java.util.*;


public class Troop {
    private final int damage;
    private final Empire King;
    private final Troops troop;
    private final String type;
    private final int range;
    private int speed;
    private Status status = Status.NATURAL;
    private int hitPoint;
    private Tile currentTile;
    private Tile destination;
    private boolean isPatrol = false;

    public Troop(Troops troop, Empire empire, Tile tile) {
        this.type = troop.getName();
        this.hitPoint = troop.getHp();
        this.speed = troop.getSpeed();
        this.range = troop.getRange();
        this.damage = troop.getDamage();
        King = empire;
        this.troop = troop;
        currentTile = tile;
        empire.addTroop(this);
    }

    public Empire getKing() {
        return King;
    }

    public void searchForEnemyWithHammingDistance(int a) {

    }

    public void attack() {

    }

    private int calculateHeight() {
        Building building = currentTile.getBuilding();
        if (building == null) {
            return 1;
        }
        switch (building.getBuilding()) {
            case KEEP:
                return 3;
            case BIG_STONE_GATEHOUSE:
            case SQUARE_TOWER:
            case CIRCLE_TOWER:
            case TURRET:
            case PERIMETER_TOWER:
                return 2;
            case LOOKOUT_TOWER:
                return 4;
            default:
                return 1;
        }
    }

    public void giveDamage(Troop enemy) {
        enemy.takeDamage(damage);
    }


    public int convertToMapId(int x, int y) {
        return Server.getCurrentMap().getGroundWidth() * y + x;
    }


    public void setMovement(Tile tile, boolean isPatrol) {
        destination = tile;
        this.isPatrol = isPatrol;
    }

    public void moveAndPatrol() {
        int src = convertToMapId(currentTile.getX(), currentTile.getY());
        int des;
        if (destination == null)
            return;
        des = convertToMapId(destination.getX(), destination.getY());
        Map currentMap = Server.getCurrentMap();
        int count = currentMap.getGroundHeight() * currentMap.getGroundWidth();
        printShortestDistance(currentMap.getGraph(), src, des, count, isPatrol);
    }


    private void printShortestDistance(ArrayList<HashSet<Integer>> adj, int s, int dest, int v, boolean isPatrol) {

    }

    private boolean moveThroughThePath(LinkedList<Integer> path, int i) {
        return false;
    }

    private boolean BFS(ArrayList<HashSet<Integer>> adj, int src, int dest, int v, int[] pred, int[] dist) {
        LinkedList<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[v];
        Arrays.fill(visited, false);
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(pred, -1);
        visited[src] = true;
        dist[src] = 0;
        queue.add(src);
        while (!queue.isEmpty()) {
            int u = queue.remove();
            for (Integer integer : adj.get(u))
                if (!visited[integer]) {
                    visited[integer] = true;
                    dist[integer] = dist[u] + 1;
                    pred[integer] = u;
                    queue.add(integer);
                    if (integer == dest)
                        return true;
                }
        }
        return false;
    }

    public void takeDamage(int damage) {
        hitPoint -= damage;
    }

    public void changeHp(int change) {
        hitPoint += change;
    }

    public boolean isDead() {
        return hitPoint <= 0;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public void setHitPoint(int value) {
        this.hitPoint = value;
    }

    public Tile getTileDestination() {
        return destination;
    }

    public Troops getTroop() {
        return troop;
    }

    public Tile getCurrentTile() {
        return currentTile;
    }

    public int getDamage() {
        return damage;
    }

    public Status getHumanStatus() {
        return status;
    }

    public void setHumanStatus(Status humanStatus) {
        this.status = humanStatus;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Troop)) return false;
        return troop == ((Troop) o).troop;
    }

    @Override
    public int hashCode() {
        return Objects.hash(troop);
    }

    public void kill() {
        hitPoint = 0;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getRange() {
        return range;
    }

    public Texture getTexture() {
        return null;
    }
}
