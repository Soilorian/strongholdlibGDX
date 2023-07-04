package org.example.model.ingame.humans.specialworkers;

import org.example.model.ingame.castle.Empire;
import org.example.model.ingame.humans.Peasant;
import org.example.model.ingame.humans.army.details.Status;
import org.example.model.ingame.map.Tile;

public class Tunneler extends Peasant {
    private Tile tunnelStart;
    private Tile tileDestination;
    private int movesLeft = 5;

    public Tunneler(Empire empire, Tile tile) {
        super(empire, tile);
    }

    public static String getName() {
        return "tunneler";
    }

    public static int getPrice() {
        return 60;
    }

    @Override
    public void update() {

    }

    public Tile getTunnelStart() {
        return tunnelStart;
    }

    @Override
    public Tile getTileDestination() {
        return tileDestination;
    }

    public int getMovesLeft() {
        return movesLeft;
    }

    public void digTunnel(Tile tile) {
        tunnelStart = getCurrentTile();
        tunnelStart.setTunnel(true);
        tunnelStart.addTunneler(this);
        setHumanStatus(Status.DIGGING);
        tileDestination = tile;
    }
}
