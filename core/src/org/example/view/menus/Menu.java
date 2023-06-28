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
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import org.example.Main;
import org.example.control.Controller;

public abstract class Menu implements Screen {
//    private static Cursor cursor = Gdx.graphics.newCursor(Controller.getCursorPixmap(), 0, 20);
    protected final TextButton okButton = new TextButton("ok", Controller.getSkin()), cancelButton = new TextButton(

            "cancel", Controller.getSkin());
    protected static final Slider timerSlider = new Slider(0, 500, 1, false, Controller.getSkin());
    protected static final Dialog messageDialog = new Dialog("", Controller.getSkin());
    protected static final Label messageLabel = new Label("", Controller.getSkin());
    private final Label label;

    private String getStageInfo() {
        return "controlling stage: " + currentStage();
    }

    protected Controller controller = Main.getController();
    protected Stage behindStage = new Stage();
    protected Stage frontStage = new Stage();
    protected Stage stage = new Stage();
    protected SpriteBatch batch = new SpriteBatch();
    protected Camera camera = new PerspectiveCamera(75, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    public Menu() {
        TextButton hideButton = new TextButton("hide", Controller.getSkin());

        messageLabel.setColor(Color.BLACK);
        messageDialog.setWidth(Gdx.graphics.getWidth() / 5f);
        messageDialog.setHeight(Gdx.graphics.getHeight() / 4f);
        messageDialog.setX(Gdx.graphics.getWidth() - messageDialog.getWidth());
        messageDialog.setY(Gdx.graphics.getHeight() /2f - messageDialog.getHeight());
        timerSlider.setWidth(messageLabel.getWidth());
        messageDialog.setVisible(false);
        messageDialog.row();
        messageDialog.add(messageLabel);
        messageDialog.row();
        messageDialog.add(hideButton);
        messageDialog.row();
        messageDialog.add(timerSlider).setActorWidth(200);

        hideButton.setHeight(80);
        hideButton.setWidth(150);
        hideButton.setX(Gdx.graphics.getWidth() / 20f * 19 - hideButton.getWidth());
        hideButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                messageDialog.setVisible(false);
                Gdx.input.setInputProcessor(behindStage);
            }
        });

        okButton.setHeight(80);
        okButton.setWidth(150);
        okButton.setX(Gdx.graphics.getWidth() / 20f * 19 - okButton.getWidth());

        cancelButton.setHeight(80);
        cancelButton.setWidth(150);
        cancelButton.setX(Gdx.graphics.getWidth() / 20f);

        timerSlider.setBounds(messageDialog.getX(), messageDialog.getY() - 20, messageDialog.getWidth(), 20);
        timerSlider.setDisabled(true);
        timerSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (((Slider) actor).getValue() >= 500) {
                    messageDialog.setVisible(false);
                    Timer.instance().clear();
                }
            }
        });

        frontStage.addActor(messageDialog);
        Gdx.input.setInputProcessor(behindStage);
        label = new Label(getStageInfo(), Controller.getSkin());
        label.setPosition(Gdx.graphics.getWidth() - label.getWidth(), Gdx.graphics.getHeight() - label.getHeight());
        label.setColor(Color.BLACK);
    }

    public abstract void create();

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        behindStage.draw();
        stage.draw();
        frontStage.draw();
        frontStage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        behindStage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            if (Gdx.input.getInputProcessor().equals(behindStage)) {
                Gdx.input.setInputProcessor(frontStage);
            }
            else {
                Gdx.input.setInputProcessor(behindStage);
            }
            label.setText(getStageInfo());
        }
        batch.begin();
        label.draw(batch, 1);
        batch.end();
    }

    private String currentStage() {
        if (Gdx.input.getInputProcessor() == null) {
            return "no stage";
        }
            if (Gdx.input.getInputProcessor().equals(behindStage))
                return "back stage";
            else
                return "front stage";
    }

    @Override
    public void dispose() {
        behindStage.dispose();
        batch.dispose();
    }

    public static void showMessage(String message) {
        timerSlider.setValue(0);
        messageLabel.setColor(Color.TAN);
        messageLabel.setText(message);
        messageDialog.setVisible(true);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                timerSlider.setValue(timerSlider.getValue() + 1);
                timerSlider.updateVisualValue();
            }
        }, 0.01f,0.01f, 500);
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
