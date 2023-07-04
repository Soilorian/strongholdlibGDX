package org.example.control;


import com.badlogic.gdx.utils.Timer;
import com.google.gson.Gson;
import org.example.model.DataBase;
import org.example.model.Game;
import org.example.model.Player;
import org.example.model.ingame.map.Map;
import org.example.model.ingame.map.Tile;
import org.example.model.utils.FriendShipRequest;
import org.example.model.utils.PlayerToken;
import org.example.model.utils.Request;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Server {
    public final static LinkedBlockingQueue<Player> players = new LinkedBlockingQueue<>();
    public final static LinkedBlockingQueue<Game> games = new LinkedBlockingQueue<>();
    public static final Gson gson = new Gson();
    public static final Logger log = Logger.getLogger(Thread.currentThread().getName() + ".logger");
    private static Map currentMap;
    private static Socket socket;
    private static DataInputStream in;
    private static DataOutputStream out;
    private Player player;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public Server() {
        log.setLevel(Level.ALL);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.FINER);
        handler.setFormatter(new SimpleFormatter());
        log.addHandler(handler);
        setupConnection();
    }

    public static Map getCurrentMap() {
        return currentMap;
    }

    public static void setCurrentMap(Map currentMap) {
        Server.currentMap = currentMap;
    }

    public static void setSocket(Socket socket) {
        Server.socket = socket;
    }

    private void updatePlayers(Player player) {
        Player player1 = DataBase.getPlayerByEmail(player.getEmail());
        if (player1 != null) {
            if (!Objects.equals(player1, player)) {
                log.fine("player info synced");
                Objects.requireNonNull(player1).update(player);
            } else {
                log.fine("players updated");
                if (players.remove(player)) {
                    player.setLastVisit(new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()));
                    try {
                        DataBase.addPlayersToExcel();
                    } catch (IOException ignored) {
                    }
                } else {
                    players.add(player);
                }
            }
        } else {
            log.fine("player registered");
            DataBase.addPlayer(player);
            try {
                DataBase.updatePlayersXS();
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ignored) {}
        }
    }

    private void sendPlayer(Player player) {
        try {
            oos.writeObject(player);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleServer() {
        DataBase.generateInfoFromJson();
        log.fine("started handling");
        if (sendData()) {
            log.fine("connection lost during data sending!");
            return;
        }
        while (true) {
            try {
                handleIncomingJson();
            } catch (IOException e) {
                log.fine("connection lost during handling!");
                safeDisconnect();
                return;
            }
        }
    }

    private boolean sendData() {
        try {
            ois = new ObjectInputStream(in);
            oos = new ObjectOutputStream(out);
            for (Player player1 : DataBase.getPlayers()) oos.writeObject(player1);
            log.fine("data sent");
        } catch (IOException e) {
            safeDisconnect();
            return true;
        }
        return false;
    }

    private void sendGames() {
        try {
            Game[] toSendGame = (Game[]) games.toArray();
            oos.writeUTF(gson.toJson(toSendGame, Game[].class));
        } catch (Exception e) {
            safeDisconnect();
        }

    }

    private void handleIncomingJson() throws IOException {
        log.fine("at handling point");
        Object json;
        try {
            json = ois.readObject();
            log.fine("packet received!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (json == null){
            System.out.println("null json");
            return;
        }
        if (json.getClass().equals(Player.class))
            updatePlayers(((Player) json));
        else if (json.getClass().equals(Request.class)) {
            Request request = ((Request) json);
            if (request.getString().equalsIgnoreCase("chats")) {
                log.fine("request handled");
                sendChats();
            }
        } else if (json.getClass().equals(Map.class)) {
            log.fine("map received");
            Map map = ((Map) json);
        } else if (json.getClass().equals(Game.class)) {
            log.fine("game collected");
            handleGame(((Game) json));
        } else if (json.getClass().equals(Tile.class)) {
            log.fine("tile arrived");
            handleTile(((Tile) json));
        } else if (json.getClass().equals(FriendShipRequest.class)) {
            handleFriendShopRequest(((FriendShipRequest) json));
        } else if (json.getClass().equals(PlayerToken.class))
            handleToken(((PlayerToken) json));
    }

    private void handleToken(PlayerToken playerToken) {
        log.fine(playerToken.getHash());
    }

    private void handleFriendShopRequest(FriendShipRequest friendShipRequest) {
        Objects.requireNonNull(DataBase.getPlayerByUsername(friendShipRequest.getAcceptor().getUsername()))
                .addFriendShipRequest(friendShipRequest);
    }

    private void handleTile(Tile tile) {
        Map currentMap = getGameById(tile.getGameId()).getCurrentMap();
        currentMap.setTile(tile);
    }

    private void sendChats() throws IOException {
        oos.writeObject(player.getChats());
        System.out.println("hapoijf");
    }

    private void handleGame(Game game) {
        Game searchedGame;
        if ((searchedGame = getGameById(game.getGameId())) == null)
            games.add(game);
        else {

            games.remove(searchedGame);
            if (game.getPlayersLength() != 0)
                games.add(game);
        }
    }

    private void safeDisconnect() {
        if (players.remove(player)) {
            log.fine("player " + player.getUsername() + " disconnected");
        }
    }

    private void setupConnection() {
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ignored) {
            System.out.println("reconnecting in 5 seconds...");
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    setupConnection();
                }
            }, 5000);
        }
    }

    public Game getGameById(String id) {
        for (Game game : games)
            if (game.getGameId().equals(id))
                return game;
        return null;
    }
}
