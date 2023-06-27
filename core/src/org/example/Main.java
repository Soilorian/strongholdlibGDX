package org.example;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import org.example.control.Controller;
import org.example.model.ClipboardImage;
import org.example.model.exceptions.CoordinatesOutOfMap;
import org.example.model.exceptions.NotInStoragesException;
import org.example.view.menus.RandomMapMenu;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;
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