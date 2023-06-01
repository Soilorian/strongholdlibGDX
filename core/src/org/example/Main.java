package org.example;

import com.badlogic.gdx.ApplicationAdapter;
import org.example.control.Controller;

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