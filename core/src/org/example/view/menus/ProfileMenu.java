package org.example.view.menus;


import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.example.control.Controller;
import org.example.control.SoundPlayer;
import org.example.control.enums.EntranceMenuMessages;
import org.example.control.menucontrollers.ProfileMenuController;
import org.example.model.exceptions.CoordinatesOutOfMap;
import org.example.model.exceptions.NotInStoragesException;
import org.example.view.enums.Menus;
import org.example.view.enums.Sounds;
import org.example.view.enums.commands.EntranceMenuCommands;
import org.example.view.enums.commands.ProfileMenuCommands;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.regex.Matcher;

import static com.badlogic.gdx.Gdx.app;
import static com.badlogic.gdx.Gdx.graphics;
import static org.example.view.enums.commands.ProfileMenuCommands.*;

public class ProfileMenu extends Menu {
    private final Window window;
    private final Slider widthSlider, hieghtSlider;


    public ProfileMenu() {
        //begin
        super();
        window = new Window("", controller.getSkin());
        widthSlider = new Slider(200, 400, 1, false, controller.getSkin());
        hieghtSlider = new Slider(200, 400, 1, true, controller.getSkin());

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
    public void run(String command) throws IOException, UnsupportedAudioFileException, LineUnavailableException,
            CoordinatesOutOfMap, NotInStoragesException {
        System.out.println("entered " + Menus.getNameByObj(this));
        Matcher matcher;
        while (true) {
            if ((matcher = ProfileMenuCommands.getMatcher(command, CHANGE_USERNAME)) != null) {
                changeUsername(matcher);
            } else if (command.equalsIgnoreCase("show menu")) {
                System.out.println(Menus.getNameByObj(this));
            } else if ((matcher = ProfileMenuCommands.getMatcher(command, CHANGE_NICKNAME)) != null) {
                changeNickname(matcher);
            } else if ((matcher = ProfileMenuCommands.getMatcher(command, CHANGE_EMAIL)) != null) {
                changeEmail(matcher);
            } else if ((matcher = ProfileMenuCommands.getMatcher(command, CHANGE_PASSWORD)) != null) {
                changePassword(matcher);
            } else if ((matcher = ProfileMenuCommands.getMatcher(command, CHANGE_SLOGAN)) != null) {
                changeSlogan(matcher);
            } else if ((ProfileMenuCommands.getMatcher(command, REMOVE_SLOGAN)) != null) {
                System.out.println(ProfileMenuController.removeSlogan());
            } else if ((matcher = ProfileMenuCommands.getMatcher(command, DISPLAY_PROFILE)) != null) {
                showProfile(matcher);
            } else if (command.equals("back")) {
                Controller.setCurrentMenu(Menus.MAIN_MENU);
                break;
            } else if (EntranceMenuCommands.getMatcher(command, EntranceMenuCommands.EXIT) != null) {
                Controller.setCurrentMenu(null);
                break;
            } else if ((command.equalsIgnoreCase("open music player"))) {
                controller.setScreen(Menus.MUSIC_CONTROL_MENU.getMenu());
                controller.changeMenu(this, this);
            }else {
                SoundPlayer.play(Sounds.AKHEY);
                System.out.println("invalid command");
            }
        }
    }

    @Override
    public void create() {

    }

    private void changeUsername(Matcher matcher) {
        String username = Controller.removeQuotes(matcher.group("Username"));
        System.out.println(ProfileMenuController.changeUsername(username));
    }

    private void changeNickname(Matcher matcher) {
        String nickname = Controller.removeQuotes(matcher.group("Nickname"));
        System.out.println(ProfileMenuController.changeNickname(nickname));
    }

    private void changeEmail(Matcher matcher) {
        String email = Controller.removeQuotes(matcher.group("Email"));
        System.out.println(ProfileMenuController.changeEmail(email));
    }

    private void changePassword(Matcher matcher) {
        String oldPassword = Controller.removeQuotes(matcher.group("OldPassword"));
        String newPassword = Controller.removeQuotes(matcher.group("NewPassword"));
        System.out.println(ProfileMenuController.changePassword(oldPassword, newPassword));
    }

    private void changeSlogan(Matcher matcher) {
        String slogan = Controller.removeQuotes(matcher.group("Slogan"));
        System.out.println(ProfileMenuController.changeSlogan(slogan));
    }

    private void showProfile(Matcher matcher) {
        String field = null;
        String option = null;
        try {
            option = matcher.group("Option1");
        } catch (Exception ignored) {
        }
        try {
            field = matcher.group("Field");
        } catch (Exception ignored) {
        }
        if (option != null) {
            if (field == null || Controller.isFieldEmpty(field))
                System.out.println(EntranceMenuMessages.EMPTY_FIELD);
            else
                System.out.println(ProfileMenuController.showProfile(field));
        } else
            System.out.println(ProfileMenuController.showProfile("all"));
    }
}
