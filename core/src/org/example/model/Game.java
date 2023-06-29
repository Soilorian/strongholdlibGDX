package org.example.model;


import org.example.model.ingame.castle.Empire;
import org.example.model.ingame.map.Map;

import java.util.ArrayList;

public class Game {
    private final ArrayList<Empire> empires = new ArrayList<>();
    private final ArrayList<Player> players = new ArrayList<>();
    private final ArrayList<Empire> allEmpire = new ArrayList<>();
    private Player owner;
    private int gameSize = 0 ;
    private String id;
    private boolean isPrivate = false;
    int playerTurn = 0;
    private Map currentMap;

    public ArrayList<Empire> getAllEmpires() {
        return allEmpire;
    }

    public ArrayList<Empire> getEmpires() {
        return empires;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        players.add(player);
        Empire empire = new Empire(player);
        empires.add(empire);
        allEmpire.add(empire);
    }

    public void removeAClientFromPlayersAndEmpires(Player player) {
        allEmpire.remove(getEmpireByItsPlayer(player));
        players.remove(player);

    }

    public Empire getEmpireByItsPlayer(Player player) {
        for (Empire empire : allEmpire)
            if (empire.getOwner().equals(player))
                return empire;
        return null;

    }

    public int getPlayerTurn() {
        ++playerTurn;
        playerTurn %= players.size();
        return playerTurn;
    }

    public Map getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(Map currentMap) {
        this.currentMap = currentMap;
    }

    public int getGameSize() {
        return gameSize;
    }


    public void setGameSize(int gameSize) {
        this.gameSize = gameSize;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
    public int getPlayersLength() {
        return players.size();
    }
}