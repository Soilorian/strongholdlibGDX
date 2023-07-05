package org.example.control;


import com.badlogic.gdx.utils.Timer;
import com.google.gson.Gson;
import org.example.model.DataBase;
import org.example.model.Game;
import org.example.model.Player;
import org.example.model.Tunnel;
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
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Server {
    public final static LinkedBlockingQueue<Tunnel> tunnels = new LinkedBlockingQueue<>();
    public final static LinkedBlockingQueue<Game> games = new LinkedBlockingQueue<>();
    public static final Gson gson = new Gson();
    public final Logger log = Logger.getLogger(Thread.currentThread().getName() + ".logger");
    private Map currentMap;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Player player;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public Server() {
        log.setLevel(Level.ALL);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.FINER);
        handler.setFormatter(new SimpleFormatter());
        log.addHandler(handler);
    }

    public static Tunnel getTunnelByItsPlayer(Player player) {
        for (Tunnel tunnel : tunnels)
            if (tunnel.getPlayer().getUsername().equals(player.getUsername()))
                return tunnel;
        return null;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
        setupConnection();
    }

    private void updatePlayers(Player player) {
        Player player1 = DataBase.getPlayerByEmail(player.getEmail());
        if (player1 != null) {
            if (!Objects.equals(player1, player)) {
                log.fine("player info synced");
                Objects.requireNonNull(player1).update(player);
                sendToAll(player);
            } else {
                log.fine("players updated");
                if (tunnels.remove(getTunnelByItsPlayer(player))) {
                    player.setLastVisit(new SimpleDateFormat("HH/mm").format(Calendar.getInstance().getTime()));
                    sendToAll(player);
                } else {
                    tunnels.add(new Tunnel(socket,out, in, oos, ois, player));

                }
                try {
                    DataBase.addPlayersToExcel();
                } catch (IOException ignored) {
                }
            }
        } else {
            log.fine("player registered");
            DataBase.addPlayer(player);
            sendToAll(player);
            try {
                DataBase.updatePlayersXS();
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ignored) {
            }
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

    public <T> void sendToAll(T t){
        sendToSome(t, tunnels);
    }

    public <T> void sendToSome(T t, Collection<Tunnel> tunnels){
        Iterator<Tunnel> iterator = tunnels.iterator();
        while (iterator.hasNext()) {
            Tunnel tunnel = iterator.next();
            try {
                tunnel.oos.writeObject(t);
            } catch (IOException e) {
                iterator.remove();
            }
        }
    }

    private boolean sendData() {
        try {
            ois = new ObjectInputStream(in);
            oos = new ObjectOutputStream(out);
            System.out.println(DataBase.getPlayers());
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
            if (games.isEmpty())
                oos.writeObject(null);
            for (Game game : games) {
                oos.writeObject(game);
            }
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
        if (json == null) {
            System.out.println("null json");
            return;
        }
        if (json.getClass().equals(Player.class))
            updatePlayers(((Player) json));
        else if (json.getClass().equals(Request.class)) {
            Request request = ((Request) json);
            if (request.getString().equalsIgnoreCase("chats")) {
                sendChats();
                log.fine("request handled");
            } else if (request.getString().contains("join room")){
                joinGame(request);
                log.fine("joining game");
            } else if (request.getString().equals("games")){
                sendGames();
                log.fine("games sent");
            } else {
                sendOnlineState(request);
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

    private void sendOnlineState(Request request) {
        try {
            oos.writeObject(getTunnelByItsPlayer(DataBase.getPlayerByUsername(request.getString())) != null);
        } catch (IOException e) {
            safeDisconnect();
        }
    }

    private void joinGame(Request request) {
        String gameId = extractGameId(request.getString());
        Game gameById = getGameById(gameId);
        gameById.addPlayer(player, socket, out, in ,oos ,ois);
        try {
            oos.writeObject(gameById);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String extractGameId(String string) {
        Pattern pattern = Pattern.compile("join room \\[(?<ID>.*)]");
        Matcher matcher = pattern.matcher(string);
        if (matcher.find()) {
            return matcher.group("ID");
        }
        return null;
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
            if (searchedGame.equals(game))
                games.remove(searchedGame);
            else if (game.getPlayersLength() != 0)
                games.remove(searchedGame);
            else
                searchedGame.update(game);
        }
    }

    private void safeDisconnect() {
//        if (players.remove(player))
        if (tunnels.remove(getTunnelByItsPlayer(player))) {
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

    public Socket getSocket() {
        return socket;
    }

    public boolean sendReflection() {
        return false;
    }
}
