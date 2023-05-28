package org.example.model;


import org.example.model.ingame.castle.Empire;
import org.example.model.ingame.map.Map;

import java.util.ArrayList;

public class Game {
    private final ArrayList<Empire> empires = new ArrayList<>();
    private final ArrayList<Player> players = new ArrayList<>();
    private final ArrayList<Empire> allEmpire = new ArrayList<>();
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
}