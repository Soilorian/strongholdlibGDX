package com.mygdx.game;

import org.example.model.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class Broker {
    private static int port = 0;

    public Broker(int port) {
        Broker.port = port;
    }

    public void run() {
        if (port == 0) {
            System.out.println("no port selected");
            return;
        }
        System.out.println("Starting Broker service...");
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                Connection connection = new Connection(socket);
                connection.start();
            } catch (IOException ignored) { break; }
        }
    }

}
