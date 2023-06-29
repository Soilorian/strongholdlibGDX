package org.example;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import org.example.control.Controller;

import java.io.IOException;
import java.net.Socket;

public class Main {
    Controller controller;

    public Main(Socket socket) {
        Controller.setSocket(socket);
    }

    public void create() {
        controller = new Controller();
        controller.handleServer();
    }
    public Controller getController() {
        return controller;
    }


}