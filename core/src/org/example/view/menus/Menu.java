package org.example.view.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import org.example.Main;
import org.example.control.Controller;

public abstract class Menu implements Screen {
    protected Controller controller = Main.getController();
    protected final TextButton okButton = new TextButton("ok", controller.getSkin()), cancelButton = new TextButton(

            "cancel", controller.getSkin());
    protected final Slider timerSlider = new Slider(0, 500, 1, false, controller.getSkin());
    protected final Dialog messageDialog = new Dialog("", controller.getSkin());
    protected final Label messageLabel = new Label("", controller.getSkin());
    protected Stage behindStage = new Stage();
    protected Stage frontStage = new Stage();
    protected Stage stage = new Stage();
    protected SpriteBatch batch = new SpriteBatch();
    protected Camera camera = new PerspectiveCamera(75, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

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
        okButton.addListener(new ClickListener() {
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
                if (((Slider) actor).isOver()) {
                    messageDialog.setVisible(false);
                }
            }
        });

        Gdx.input.setInputProcessor(behindStage);
    }

    public abstract void create();

    @Override
    public void render(float delta) {
        if (!Controller.manager.isFinished()) {
            batch.begin();
            batch.draw(controller.getLoadingBG(), 0, 0);
            Slider slider = new Slider(0, 1, 0.001f, false, controller.getSkin());
            slider.setValue(Controller.manager.getProgress());
            slider.updateVisualValue();
            slider.setDisabled(true);
            slider.setWidth(Gdx.graphics.getWidth() / 4f * 3);
            slider.setPosition(Gdx.graphics.getWidth() / 2f - slider.getWidth() / 2f, Gdx.graphics.getHeight() / 4f);
            behindStage.addActor(slider);
            behindStage.draw();
            behindStage.act();
        } else {
            ScreenUtils.clear(Color.BLACK);
            batch.begin();
            timerSlider.setValue(timerSlider.getValue() + 1);
            timerSlider.updateVisualValue();
            behindStage.draw();
            stage.draw();
            frontStage.draw();
            frontStage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
            stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));
            behindStage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
            batch.end();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)){
            if (Gdx.input.getInputProcessor().equals(behindStage))
                Gdx.input.setInputProcessor(frontStage);
            else
                Gdx.input.setInputProcessor(behindStage);
        }
    }

    @Override
    public void dispose() {
        behindStage.dispose();
        batch.dispose();
    }

    protected void showMessage(String message) {
        timerSlider.setValue(0);
        messageLabel.setColor(Color.TAN);
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
    public void show() {

    }
}
