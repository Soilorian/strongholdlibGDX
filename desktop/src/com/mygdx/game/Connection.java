package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.example.Main;
import org.example.model.Player;

import java.io.ObjectStreamException;
import java.net.Socket;

public class Connection extends Thread{

    private final Socket socket;

    public Connection(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("connection to " + socket.getInetAddress() +" established!");
        Main main = new Main(socket);
        main.create();
    }
}
