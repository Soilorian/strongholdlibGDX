package org.example.view.menus.minimenus;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import org.example.control.Controller;
import org.example.control.SoundPlayer;
import org.example.control.enums.GameStartUpMenuMessages;
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
import static org.example.view.menus.GameStartUpMenu.isNotDigit;

public class SelectMapMenu extends Menu {
    private final Window window;
    private final Slider xSlider, ySlider;


    public SelectMapMenu() {
        //begin
        window = new Window("", controller.getSkin());
        xSlider = new Slider(200, 400, 1, false, controller.getSkin());
        ySlider = new Slider(200, 400, 1, true, controller.getSkin());

        //body
        window.setX((float) graphics.getWidth() / 2 - window.getWidth() / 2);
        window.setY((float) graphics.getHeight() / 2 - window.getHeight() / 2);

        xSlider.setX(window.getX());
        xSlider.setY((float) graphics.getHeight() / 10);
        xSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                window.setWidth(xSlider.getValue());
                window.setX((float) graphics.getWidth() / 2 - window.getWidth() / 2);
            }
        });

        ySlider.setX((float) graphics.getWidth() / 10);
        ySlider.setY(window.getHeight());
        ySlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                window.setHeight(ySlider.getValue());
                window.setY((float) graphics.getHeight() / 2 - window.getHeight() / 2);
            }
        });

        //end
        stage.addActor(window);
        stage.addActor(xSlider);
        stage.addActor(ySlider);
    }

    static void selectSize(Matcher matcher) {
        String widthInString = Controller.removeQuotes(matcher.group("Width"));
        String heightInString = Controller.removeQuotes(matcher.group("Height"));
        if (Controller.isFieldEmpty(widthInString) || Controller.isFieldEmpty(heightInString))
            System.out.println(GameStartUpMenuMessages.EMPTY_FIELD);
        else if (isNotDigit(widthInString))
            System.out.println("not valid width\nwidth should only contain digits");
        else if (isNotDigit(heightInString))
            System.out.println("not valid height!\nheight should only contain digits");
        else {
            int width = Integer.parseInt(widthInString);
            int height = Integer.parseInt(heightInString);
            if (width < 200 || width > 400)
                System.out.println("width not in valid range!");
            else if (height < 200 || height > 400)
                System.out.println("height not in valid range!");
            else {
                GameStartUpMenuController.selectSize(width, height);
                System.out.println("map size " + width + " x " + height + " selected!");
            }
        }
    }

    @Override
    public void run(String input) throws IOException, UnsupportedAudioFileException, LineUnavailableException, CoordinatesOutOfMap, NotInStoragesException {
        Matcher matcher;
        if ((matcher = GameStartUpMenuCommands.getMatcher(input, GameStartUpMenuCommands.SELECT_SIZE)) != null) {
            selectSize(matcher);
        } else if (input.equalsIgnoreCase("show menu"))
            System.out.println(Menus.getNameByObj(this));
        else if (GameStartUpMenuCommands.getMatcher(input, GameStartUpMenuCommands.CANCEL) != null) {
            cancel();
        } else {
            SoundPlayer.play(Sounds.AKHEY);
            System.out.println("invalid command");
        }
    }

    private void cancel() {
        GameStartUpMenuController.cancel();
    }
}
