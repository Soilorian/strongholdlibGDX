package org.example.view.menus;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.ScreenUtils;
import org.example.Main;
import org.example.control.Controller;
import org.example.model.exceptions.CoordinatesOutOfMap;
import org.example.model.exceptions.NotInStoragesException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Scanner;

public abstract class Menu implements Screen {
    protected Controller controller = Main.getController();
    protected Stage stage = new Stage();
    protected ModelBatch batch = new ModelBatch();
    protected Camera camera = new PerspectiveCamera();
    protected CameraInputController cameraInput = new CameraInputController(camera);
    protected final TextButton okButton = new TextButton("ok", controller.getSkin()), cancelButton = new TextButton(

        "cancel", controller.getSkin());
    protected final Window errorWindow = new Window("error", controller.getSkin());

    protected abstract void run(String input) throws IOException, UnsupportedAudioFileException, LineUnavailableException,
            CoordinatesOutOfMap, NotInStoragesException;

    @Override
    public void render(float delta){
        ScreenUtils.clear(Color.BLACK);
        stage.draw();
        stage.act();
        batch.begin(camera);
        controller.getScreen().render(delta);
        batch.end();
    }

    @Override
    public void dispose(){
        stage.dispose();
        batch.dispose();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void show(){

    }
}
