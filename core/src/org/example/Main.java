package org.example;

import org.example.control.Server;

import java.net.Socket;

public class Main {
    Server server;

    public Main(Socket socket) {
        server = new Server();
        server.setSocket(socket);
    }

    public void create() {
        server.handleServer();
    }
    public Server getController() {
        return server;
    }


}