package org.example.view.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
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
    protected Camera camera = new PerspectiveCamera(75, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    protected final TextButton okButton = new TextButton("ok", controller.getSkin()), cancelButton = new TextButton(

        "cancel", controller.getSkin());
    protected final Slider timerSlider = new Slider(0, 500, 1, false,controller.getSkin());
    protected final Dialog messageDialog = new Dialog("", controller.getSkin());
    protected final Label messageLabel = new Label("", controller.getSkin());

    protected abstract void run(String input) throws IOException, UnsupportedAudioFileException, LineUnavailableException,
            CoordinatesOutOfMap, NotInStoragesException;

    public abstract void create();
    public Menu() {
        messageLabel.setColor(Color.BLACK);
        messageDialog.add(messageLabel);
        messageDialog.setX(Gdx.graphics.getHeight() / 10f);
        messageDialog.setY(Gdx.graphics.getWidth() / 10f);
        messageDialog.setWidth(Gdx.graphics.getWidth() / 10f);
        messageDialog.setHeight(Gdx.graphics.getHeight() / 10f);
        messageDialog.setVisible(false);
        messageDialog.add(okButton).expand().bottom();
        messageDialog.row();
        messageDialog.add(timerSlider).expand().top();

        okButton.setHeight(80);
        okButton.setWidth(150);
        okButton.setX(Gdx.graphics.getWidth() / 20f * 19 - okButton.getWidth());
        okButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                messageDialog.setVisible(false);
            }
        });

        cancelButton.setHeight(80);
        cancelButton.setWidth(150);
        cancelButton.setX(Gdx.graphics.getWidth() / 20f);

        timerSlider.setDisabled(true);
        timerSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (((Slider) actor).isOver()){
                    messageDialog.setVisible(false);
                }
            }
        });

        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void render(float delta){
        ScreenUtils.clear(Color.BLACK);
        batch.begin(camera);
        timerSlider.setValue(timerSlider.getValue()+1);
        timerSlider.updateVisualValue();
        stage.draw();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        batch.end();
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
        messageDialog.setVisible(true);
        messageDialog.add(timerSlider).align(Align.top);
        messageDialog.add(okButton).align(Align.bottom).pad(20);
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
