package org.example.model;

import com.google.gson.Gson;
import org.example.Main;
import org.example.control.Controller;
import org.example.control.menucontrollers.ProfileMenuController;
import org.example.model.chat.Chat;
import org.example.model.ingame.map.Map;
import org.example.model.ingame.map.Tile;
import org.example.model.utils.FriendShipRequest;
import org.example.model.utils.PlayerToken;
import org.example.model.utils.Request;
import org.example.model.utils.StreamSetup;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Client extends Thread implements Serializable {
    private final Gson gson = new Gson();
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private DataInputStream in;
    private DataOutputStream out;
    private Socket socket;
    private int port;
    private String host;

    public Client(String host, int port) {
        this.port = port;
        this.host = host;
    }

    @Override
    public void run() {
    }


    private void handleReceived() throws IOException, ClassNotFoundException {

    }

    public void setupConnection() {

    }

    public DataInputStream getIn() {
        return in;
    }

    public void setIn(DataInputStream in) {
        this.in = in;
    }

    public DataOutputStream getOut() {
        return out;
    }

    public void setOut(DataOutputStream out) {
        this.out = out;
    }

    public void sendPlayer(Player player) throws IOException {
        oos.writeObject(player);
    }

    public void sendPacket(Game game) throws IOException {
        oos.writeObject(game);
    }

    public void sendPacket(PlayerToken playerToken) throws IOException {
        oos.writeObject(playerToken);
    }

    public void sendPacket(Tile tile) throws IOException {
        oos.writeObject(tile);
    }

    public void sendPacket(FriendShipRequest friendShipRequest) throws IOException {
        oos.writeObject(friendShipRequest);
    }

    public void getGames(Game[] gamesForLobby) {

    }

    public void sendMap(Map asdf) throws IOException {
        oos.writeObject(asdf);
    }

    public void sendRequest(Request request) throws IOException {
        oos.writeObject(request);
        Thread.yield();
    }

    public void shutdown() {
        try {
            socket.shutdownInput();
            socket.shutdownOutput();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendPacket(StreamSetup streamSetup) throws IOException {
        oos.writeObject(streamSetup);
    }

    public void sendRequest(String username) throws IOException {
        oos.writeObject(username);
    }
}
