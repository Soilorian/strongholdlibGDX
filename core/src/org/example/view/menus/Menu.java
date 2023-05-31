package org.example.view.menus;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import org.example.Main;
import org.example.control.Controller;
import org.example.model.exceptions.CoordinatesOutOfMap;
import org.example.model.exceptions.NotInStoragesException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Scanner;

public interface Menu extends Screen {
    Controller controller = Main.getController();
    Stage stage = new Stage();
    ModelBatch batch = new ModelBatch();
    Camera camera = new PerspectiveCamera();
    CameraInputController cameraInput = new CameraInputController(camera);


    void run() throws IOException, UnsupportedAudioFileException, LineUnavailableException, CoordinatesOutOfMap, NotInStoragesException;

    @Override
    default void render(float delta){
        ScreenUtils.clear(Color.BLACK);
        stage.draw();
        stage.act();
        batch.begin(camera);
        controller.getScreen().render(delta);
        batch.end();
    }

    @Override
    default void dispose(){
        stage.dispose();
        batch.dispose();
    }
}
