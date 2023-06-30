package org.example.model;


import com.badlogic.gdx.utils.Timer;
import org.example.control.Controller;
import org.example.model.ingame.castle.Empire;
import org.example.model.ingame.map.Map;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Game extends Thread {
    private final ArrayList<Empire> empires = new ArrayList<>();
    private final ArrayList<Empire> allEmpire = new ArrayList<>();
    private final HashMap<Player, Tunnel> players = new HashMap<>();
    private Player owner;
    private int gameSize = 0;
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

    public HashMap<Player, Tunnel> getPlayers() {
        return players;
    }

    public void addPlayer(Player player, Socket socket) {
        try {
            players.put(player, new Tunnel(socket));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    @Override
    public void run() {
        handleGame();
    }

    private void handleGame() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                try {
                    getInfo();
                } catch (IOException e) {
                    disconnect();
                }
                checkWin();
            }
        }, 5, 5);
    }

    private void checkWin() {
        int alive = 0;
        for (Empire empire : empires) if (!empire.isLordDead()) alive++;

    }

    private void disconnect() {
        Timer.instance().clear();
        Controller.log.fine("player disconnected");
        pause();
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                checkConnection();
            }
        }, 1, 1, 120);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                removeDisconnected();
            }
        }, 120);
    }

    private void removeDisconnected() {
        Iterator<Tunnel> iterator = players.values().iterator();
        while (iterator.hasNext()) {
            DataOutputStream out = iterator.next().out;
            try {
                out.writeUTF("are you alive?");
            } catch (IOException e) {
                iterator.remove();
            }
        }
    }

    private void pause() {
        for (Tunnel tun : players.values()) {
            DataOutputStream out = tun.out;
            try {
                out.writeUTF("pause");
            } catch (IOException ignored) {
            }
        }
    }

    private void checkConnection() {
        for (Player player : players.keySet()) {
            if (!Controller.players.contains(player)) {
                return;
            }
        }
        Timer.instance().clear();
        try {
            updateInfo(Controller.gson.toJson(currentMap));
        } catch (IOException e) {
            disconnect();
        }
        handleGame();
    }

    private void getInfo() throws IOException {
        for (Tunnel tun : players.values()) {
            DataInputStream in = tun.in;
            // TODO: 6/29/2023 apply changes from Update received
            String json = in.readUTF();
            updateInfo(json);
        }
    }

    private void updateInfo(String json) throws IOException {
        for (Tunnel tun : players.values()) {
            DataOutputStream out = tun.out;
            out.writeUTF(json);
        }
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

    public String getGameId() {
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