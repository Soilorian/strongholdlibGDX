package org.example.model;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Tunnel implements Serializable{
    public Socket socket;
    public DataOutputStream out;
    public DataInputStream in;
    public ObjectOutputStream oos;
    public ObjectInputStream ois;
    public Player player;

    public Tunnel(Socket socket,Player player) throws IOException {
        this.socket = socket;
        this.out = new DataOutputStream(socket.getOutputStream());
        this.in = new DataInputStream(socket.getInputStream());
        oos = new ObjectOutputStream(out);
        ois = new ObjectInputStream(in);
        this.player = player;
    }

    public Tunnel(Socket socket, DataOutputStream out, DataInputStream in, ObjectOutputStream oos, ObjectInputStream ois, Player player) {
        this.socket = socket;
        this.out = out;
        this.in = in;
        this.oos = oos;
        this.ois = ois;
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
