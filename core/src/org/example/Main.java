package org.example;

import org.example.control.Server;

import java.net.Socket;

public class Main {
    Server server;

    public Main(Socket socket) {
        Server.setSocket(socket);
    }

    public void create() {
        server = new Server();
        server.handleServer();
    }
    public Server getController() {
        return server;
    }


}