package org.example;

import com.badlogic.gdx.ApplicationAdapter;
import org.example.control.Controller;
import org.example.model.exceptions.CoordinatesOutOfMap;
import org.example.model.exceptions.NotInStoragesException;
import org.example.view.menus.RandomMapMenu;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Main extends ApplicationAdapter {
    static Controller controller;
    @Override
    public void create() {
        controller = new Controller();
        controller.create();
    }

    @Override
    public void render() {
        controller.render();
    }

    @Override
    public void dispose() {
        controller.dispose();
    }

    public static Controller getController() {
        return controller;
    }
}