package org.example;

import org.example.control.Controller;

import java.net.Socket;

public class Main {
    static Controller controller;

    public Main(Socket socket) {
        Controller.setSocket(socket);
    }

    public void create() {
        controller = new Controller();
        controller.handleServer();
    }
    public static Controller getController() {
        return controller;
    }
}