package org.example.view.menus.minimenus;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.example.model.exceptions.CoordinatesOutOfMap;
import org.example.model.exceptions.NotInStoragesException;
import org.example.view.menus.Menu;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

import static com.badlogic.gdx.Gdx.app;
import static com.badlogic.gdx.Gdx.graphics;

public class TestMenu extends Menu {
    private final Window window;
    private final Slider widthSlider, hieghtSlider;

    public TestMenu() {
        super();
        window = new Window("", controller.getSkin());
        widthSlider = new Slider(200, 400, 1, false, controller.getSkin());
        hieghtSlider = new Slider(200, 400, 1, true, controller.getSkin());
    }

    public void create() {
        //body
        window.setWidth(graphics.getWidth() * getPercentage(widthSlider.getValue()));
        window.setHeight(graphics.getHeight() * getPercentage(hieghtSlider.getValue()));
        window.setX((float) graphics.getWidth() / 2 - window.getWidth() / 2);
        window.setY((float) graphics.getHeight() / 2 - window.getHeight() / 2);

        widthSlider.setX((float) graphics.getWidth() / 2 - (graphics.getWidth() * 0.7f) / 2);
        widthSlider.setY((float) graphics.getHeight() / 10);
        widthSlider.setWidth(graphics.getWidth() * 0.7f);
        widthSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                window.setWidth(widthSlider.getValue());
                window.setX((float) graphics.getWidth() / 2 - window.getWidth() / 2);
                window.setWidth(graphics.getWidth() * getPercentage(widthSlider.getValue()));
                window.setX((float) graphics.getWidth() / 2 - window.getWidth() / 2);
            }
        });

        hieghtSlider.setX((float) graphics.getWidth() / 10);
        hieghtSlider.setY(window.getHeight());
        hieghtSlider.setHeight(graphics.getHeight() * 0.7f);
        hieghtSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                window.setHeight(hieghtSlider.getValue());
                window.setY((float) graphics.getHeight() / 2 - window.getHeight() / 2);
                window.setHeight(graphics.getHeight() * getPercentage(hieghtSlider.getValue()));
                window.setY((float) graphics.getHeight() / 2 - window.getHeight() / 2);
            }
        });

        okButton.setX((float) graphics.getHeight() / 20);
        okButton.setY((float) graphics.getWidth() / 20);
        okButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    run("select size -w " + widthSlider.getValue() + " -h " + hieghtSlider);
                } catch (IOException | UnsupportedAudioFileException | LineUnavailableException | CoordinatesOutOfMap |
                         NotInStoragesException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                app.exit();
            }
        });

        //end
        stage.clear();
        stage.addActor(window);
        stage.addActor(widthSlider);
        stage.addActor(hieghtSlider);
        stage.addActor(okButton);
        TextField type = new TextField("type", controller.getSkin());
        type.setX(100);
        stage.addActor(type);
        stage.addActor(cancelButton);
    }

    public float getPercentage(float length) {
        float derivative = (float) (70 - 30) / (400 - 200);
        return (derivative * (length - 200) + 30)/100;
    }
    @Override
    protected void run(String input) throws IOException, UnsupportedAudioFileException, LineUnavailableException, CoordinatesOutOfMap, NotInStoragesException {

    }
}
