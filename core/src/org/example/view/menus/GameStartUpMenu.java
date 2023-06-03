package org.example.view.menus;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.StringBuilder;
import org.example.control.Controller;
import org.example.control.SoundPlayer;
import org.example.control.enums.GameStartUpMenuMessages;
import org.example.control.menucontrollers.GameMenuController;
import org.example.control.menucontrollers.GameStartUpMenuController;
import org.example.model.DataBase;
import org.example.model.Player;
import org.example.model.exceptions.CoordinatesOutOfMap;
import org.example.model.exceptions.NotInStoragesException;
import org.example.model.ingame.castle.Colors;
import org.example.view.enums.Menus;
import org.example.view.enums.Sounds;
import org.example.view.enums.commands.GameStartUpMenuCommands;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.regex.Matcher;


public class GameStartUpMenu extends Menu {
    private final SelectBox<Colors> colorsSelectBox;
    private final SelectBox<Integer> castleSelectBox;
    private final TextField addUsername;
    private final TextButton addUser;
    private final Window playersWindow;
    private final ArrayList<Label> playerLabels = new ArrayList<>();
    private final Image background;
    private int choosingPlayer = 0;

    public GameStartUpMenu() {
        this.colorsSelectBox = new SelectBox<>(controller.getSkin());
        this.castleSelectBox = new SelectBox<>(controller.getSkin());
        this.addUsername = new TextField("",controller.getSkin());
        this.addUser = new TextButton("add", controller.getSkin());
        playersWindow = new Window("players",controller.getSkin());
        background = new Image(controller.getGameStartBG());
    }

    public boolean isNotDigit(String str) {
        return !str.matches("\\d+");
    }

    public void next() {
        GameStartUpMenuMessages message = GameStartUpMenuController.nextTurn();
        showMessage(message.toString());
        if (message.equals(GameStartUpMenuMessages.SUCCESS))
            choosingPlayer++;
        if (message.equals(GameStartUpMenuMessages.DONE))
            controller.changeMenu(Menus.GAME_MENU.getMenu(), this);
    }

    @Override
    public void create() {
        setActors();
        addActors();
        Gdx.input.setInputProcessor(stage);
    }

    private void addActors() {
        stage.clear();
        stage.addActor(background);
        stage.addActor(playersWindow);
        stage.addActor(colorsSelectBox);
        stage.addActor(castleSelectBox);
        stage.addActor(addUsername);
        stage.addActor(addUser);
        stage.addActor(okButton);
        stage.addActor(cancelButton);
    }

    private void setActors() {


        playersWindow.setWidth(Gdx.graphics.getWidth() / 4f * 3 - 20);
        playersWindow.setHeight(Gdx.graphics.getHeight() / 2f);
        playersWindow.setPosition(0, Gdx.graphics.getHeight()/ 2f);
        Label label = new Label(makeLabelForPlayer(DataBase.getCurrentPlayer()), controller.getSkin());
        playerLabels.add(label);
        playersWindow.addActor(label);



        colorsSelectBox.setPosition(Gdx.graphics.getWidth() / 4f, Gdx.graphics.getHeight() / 3f);
        colorsSelectBox.setWidth(100);
        colorsSelectBox.setItems(Colors.values());
        colorsSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                selectColor();
            }
        });

        castleSelectBox.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 3f);
        castleSelectBox.setWidth(100);
        castleSelectBox.setItems(turnToArray(GameMenuController.getCurrentGame().getCurrentMap().getCastles().keySet()));
        castleSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                selectCastle();
            }
        });

        addUsername.setPosition(Gdx.graphics.getWidth() / 5f - addUsername.getWidth(), Gdx.graphics.getHeight() / 3f);
        addUsername.setMessageText("username");
        addUser.setPosition(addUsername.getX(), addUsername.getY() - addUser.getHeight() - 10);
        addUser.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                addPlayer();
            }
        });

        okButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                next();
            }
        });

        cancelButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                cancelGameStartUp();
            }
        });

    }

    private Array<Integer> turnToArray(Set<Integer> integers) {
        Array<Integer> res = new Array<>();
        for (Integer integer : integers) {
            res.add(integer);
        }
        return res;
    }

    private void selectCastle(){
        Label label = playerLabels.get(choosingPlayer);
        StringBuilder text = label.getText();
        if (!isNotDigit((text.substring(text.length - 2))))
            castleSelectBox.getItems().add(Integer.parseInt((text.substring(text.length - 2))));
        label.setText(text + " | castle No. " + castleSelectBox.getSelected());
        castleSelectBox.getItems().removeValue(castleSelectBox.getSelected(), true);
    }

    private void selectColor() {
        Label label = playerLabels.get(choosingPlayer);
        Colors colors = Colors.turnToColors(label.getColor());
        if (colors != null)
            colorsSelectBox.getItems().add(colors);
        label.setColor(colorsSelectBox.getSelected().toColor());
        GameStartUpMenuController.selectColor(colorsSelectBox.getSelected());
        colorsSelectBox.getItems().removeValue(colorsSelectBox.getSelected(), true);
    }

    private String  makeLabelForPlayer(Player player) {
        return player.getNickname() + " | @" + player.getUsername() + " | " + player.getSlogan();
    }


    private boolean selectMapMenu(String input) throws UnsupportedAudioFileException, CoordinatesOutOfMap,
            LineUnavailableException, NotInStoragesException, IOException {
        System.out.println("select the map you want");
        do {
            Matcher matcher;
            if (input.equalsIgnoreCase("show menu"))
                System.out.println(Menus.getNameByObj(this));
            else if ((matcher = GameStartUpMenuCommands.getMatcher(input, GameStartUpMenuCommands.SELECT_MAP)) != null) {
                if (selectMap(matcher)) {
                    return true;
                }
            } else if (GameStartUpMenuCommands.getMatcher(input, GameStartUpMenuCommands.SHOW_MAPS) != null)
                showMaps();
            else if (GameStartUpMenuCommands.getMatcher(input, GameStartUpMenuCommands.CANCEL) != null) {
                return false;
            } else {
                SoundPlayer.play(Sounds.AKHEY);
                System.out.println("invalid command");
            }
        } while (true);
    }



    private void showMaps() {
        System.out.println(GameStartUpMenuController.showAllMap());
    }

    private boolean selectMap(Matcher matcher) throws IOException, UnsupportedAudioFileException, LineUnavailableException, CoordinatesOutOfMap, NotInStoragesException {
        String id = Controller.removeQuotes(matcher.group("Id"));
        if (Controller.isFieldEmpty(id))
            System.out.println(GameStartUpMenuMessages.EMPTY_FIELD);
        else {
            GameStartUpMenuMessages x = GameStartUpMenuController.selectMap(id);
            System.out.println(x);
            if (x.equals(GameStartUpMenuMessages.SUCCESS)) {
                GameMenuController.getCurrentGame().setCurrentMap(Controller.getCurrentMap());
                return true;
            }
        }
        return false;
    }

    private void addPlayer() {
        showMessage(GameStartUpMenuController.addPlayer(addUsername.getText()).toString());
    }

    private void selectCastle(Matcher matcher) {
        String castle = Controller.removeQuotes(matcher.group("Id"));
        if (Controller.isFieldEmpty(castle))
            System.out.println(GameStartUpMenuMessages.EMPTY_FIELD);
        else if (isNotDigit(castle))
            System.out.println("castle number should be a digit!");
        else
            System.out.println(GameStartUpMenuController.selectCastle(Integer.parseInt(castle)));
    }


    private void cancelGameStartUp() {
        GameStartUpMenuController.cancel();
    }
}
