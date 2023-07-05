package com.mygdx.game;

import org.example.Main;

import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class Connection extends Thread {
    private static final LinkedBlockingQueue<Connection> connections = new LinkedBlockingQueue<>();
    private final Socket socket;

    public Connection(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("connection to " + socket.getInetAddress().toString() + " established!");
        if (newConnection(socket)) {
            System.out.println("new thread");
            connections.add(this);
            Main main = new Main(socket);
            main.create();
        }

    }

    private boolean newConnection(Socket socket) {
        for (Connection connection : connections) {
            if (connection.socket.getInetAddress().equals(socket.getInetAddress()) && connection.isAlive())
                return false;
        }
        return true;
    }
}
