package org.example.view.menus.minimenus;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import org.example.control.Controller;
import org.example.control.SoundPlayer;
import org.example.control.menucontrollers.GameStartUpMenuController;
import org.example.model.exceptions.CoordinatesOutOfMap;
import org.example.model.exceptions.NotInStoragesException;
import org.example.view.enums.Menus;
import org.example.view.enums.Sounds;
import org.example.view.enums.commands.GameStartUpMenuCommands;
import org.example.view.menus.Menu;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.regex.Matcher;

import static com.badlogic.gdx.Gdx.graphics;

public class SelectSizeMenu extends Menu {
    private final Window window;
    private final Slider widthSlider, hieghtSlider;
    private final Label widthLabel, hieghtLabel;
    private final Table previewTable;


    public SelectSizeMenu() {
        super();
        window = new Window("", controller.getSkin());
        widthSlider = new Slider(200, 400, 1, false, controller.getSkin());
        hieghtSlider = new Slider(200, 400, 1, true, controller.getSkin());
        widthLabel = new Label("200", controller.getSkin());
        hieghtLabel = new Label("200", controller.getSkin());
        previewTable = new Table(controller.getSkin());
    }

    public void create() {
        //body
        window.setWidth(graphics.getWidth() * getPercentage(widthSlider.getValue()));
        window.setHeight(graphics.getHeight() * getPercentage(hieghtSlider.getValue()));
        window.setX((float) graphics.getWidth() / 2 - window.getWidth() / 2);
        window.setY((float) graphics.getHeight() / 2 - window.getHeight() / 2);
        window.add(previewTable);

        previewTable.background(new TextureRegionDrawable(controller.resizer(window.getWidth(), window.getHeight(),
                controller.getDefaultMap())));
        previewTable.add(hieghtLabel);
        previewTable.add(widthLabel).expand().bottom();


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
                widthLabel.setText((int) widthSlider.getValue());
                previewTable.background(new TextureRegionDrawable(controller.resizer(window.getWidth(), window.getHeight(),
                        controller.getDefaultMap())));
            }
        });

        hieghtSlider.setX((float) graphics.getWidth() / 10);
        hieghtSlider.setY((float) graphics.getHeight() / 2 - (graphics.getHeight() * 0.7f) / 2);
        hieghtSlider.setHeight(graphics.getHeight() * 0.7f);
        hieghtSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                window.setHeight(hieghtSlider.getValue());
                window.setY((float) graphics.getHeight() / 2 - window.getHeight() / 2);
                window.setHeight(graphics.getHeight() * getPercentage(hieghtSlider.getValue()));
                window.setY((float) graphics.getHeight() / 2 - window.getHeight() / 2);
                hieghtLabel.setText(((int) hieghtSlider.getValue()));
                previewTable.background(new TextureRegionDrawable(controller.resizer(window.getWidth(), window.getHeight(),
                        controller.getDefaultMap())));
            }
        });

        okButton.setX((float) graphics.getHeight() / 20);
        okButton.setY((float) graphics.getWidth() / 20);
        okButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    run("select size -w " + ((int) widthSlider.getValue()) + " -h " + ((int) hieghtSlider.getValue()));
                } catch (IOException | UnsupportedAudioFileException | LineUnavailableException | CoordinatesOutOfMap |
                         NotInStoragesException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                cancelSizeSelection();
            }
        });

        //end
        stage.clear();
        stage.addActor(window);
        stage.addActor(widthSlider);
        stage.addActor(hieghtSlider);
        stage.addActor(okButton);
        stage.addActor(cancelButton);
    }

    public float getPercentage(float length) {
        float derivative = (float) (70 - 30) / (400 - 200);
        return (derivative * (length - 200) + 30)/100;
    }


    void selectSize(Matcher matcher) {
        String widthInString = Controller.removeQuotes(matcher.group("Width"));
        String heightInString = Controller.removeQuotes(matcher.group("Height"));
        int width = Integer.parseInt(widthInString);
        int height = Integer.parseInt(heightInString);
        GameStartUpMenuController.selectSize(width, height);
        showSuccess("map size " + width + " x " + height + " selected!");
        controller.setScreen(Menus.SELECT_MAP_MENU.getMenu());
        controller.changeMenu(this, this);
    }

    @Override
    public void run(String input) throws IOException, UnsupportedAudioFileException, LineUnavailableException, CoordinatesOutOfMap, NotInStoragesException {
        Matcher matcher;
        if ((matcher = GameStartUpMenuCommands.getMatcher(input, GameStartUpMenuCommands.SELECT_SIZE)) != null)
            selectSize(matcher);
        else
            System.out.println("something wrong happened in "+Menus.getNameByObj(this));
    }

    private void cancelSizeSelection() {
        controller.changeMenu(Menus.MAIN_MENU.getMenu(), this);
    }
}
