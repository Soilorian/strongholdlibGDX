package org.example.view.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import org.example.Main;
import org.example.control.Controller;
import org.example.model.exceptions.CoordinatesOutOfMap;
import org.example.model.exceptions.NotInStoragesException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public abstract class Menu implements Screen {
    protected Controller controller = Main.getController();
    protected Stage stage = new Stage();
    protected ModelBatch batch = new ModelBatch();
    protected Camera camera = new PerspectiveCamera();
    protected CameraInputController cameraInput = new CameraInputController(camera);
    protected final TextButton okButton = new TextButton("ok", controller.getSkin()), cancelButton = new TextButton(

        "cancel", controller.getSkin());
    private final Slider timerSlider = new Slider(0, 500, 1, false,controller.getSkin());
    protected final Window messageWindow = new Window("", controller.getSkin());
    protected final Label messageLabel = new Label("", controller.getSkin());

    protected abstract void run(String input) throws IOException, UnsupportedAudioFileException, LineUnavailableException,
            CoordinatesOutOfMap, NotInStoragesException;

    public Menu() {
        messageLabel.setColor(Color.BLACK);
        messageWindow.add(messageLabel);
        messageWindow.setX(Gdx.graphics.getHeight() / 4f);
        messageWindow.setY(Gdx.graphics.getWidth() / 4f);
        messageWindow.setWidth(Gdx.graphics.getWidth() / 2f);
        messageWindow.setHeight(Gdx.graphics.getHeight() / 2f);
        messageWindow.setVisible(false);
        okButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                messageWindow.setVisible(false);
            }
        });
        timerSlider.setDisabled(true);
        timerSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (((Slider) actor).isOver()){
                    messageWindow.setVisible(false);
                }
            }
        });
    }

    @Override
    public void render(float delta){
        ScreenUtils.clear(Color.BLACK);
        stage.draw();
        stage.act();
        batch.begin(camera);
        controller.getScreen().render(delta);
        batch.end();
        timerSlider.setValue(timerSlider.getValue()+1);
        timerSlider.updateVisualValue();
    }

    @Override
    public void dispose(){
        stage.dispose();
        batch.dispose();
    }

    protected void showSuccess(String message){
        timerSlider.setValue(0);
        messageLabel.setColor(new Color(0,0.3f,0,1));
        messageLabel.setText(message);
        messageWindow.setVisible(true);
        messageWindow.add(timerSlider).align(Align.top);
        messageWindow.add(okButton).align(Align.bottom).pad(20);
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
