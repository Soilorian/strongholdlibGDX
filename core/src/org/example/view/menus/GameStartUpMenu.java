package org.example.view.menus;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
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
import org.example.model.ingame.castle.Empire;
import org.example.view.enums.Menus;
import org.example.view.enums.Sounds;
import org.example.view.enums.commands.GameStartUpMenuCommands;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Set;
import java.util.regex.Matcher;

import static com.badlogic.gdx.Gdx.graphics;


public class GameStartUpMenu extends Menu {
    private final SelectBox<Colors> colorsSelectBox;
    private final SelectBox<Integer> castleSelectBox;
    private final TextField addUsername;
    private final TextButton addUser;
    private final Window playersWindow;
    private final Image background;
    private int choosingPlayer = 0;
    private Image mapImage;
    private Table selectionTable;

    public GameStartUpMenu() {
        super();
        this.colorsSelectBox = new SelectBox<>(Controller.getSkin());
        this.castleSelectBox = new SelectBox<>(Controller.getSkin());
        this.addUsername = new TextField("", Controller.getSkin());
        this.addUser = new TextButton("add", Controller.getSkin());
        playersWindow = new Window("players", Controller.getSkin());
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
        Gdx.input.setInputProcessor(behindStage);
    }

    private void addActors() {
        behindStage.clear();
        behindStage.addActor(background);
        behindStage.addActor(playersWindow);
        behindStage.addActor(selectionTable);
        behindStage.addActor(okButton);
        behindStage.addActor(cancelButton);
    }

    private void setActors() {
        playersWindow.setWidth(Gdx.graphics.getWidth() / 3f * 2);
        playersWindow.setHeight(Gdx.graphics.getHeight() / 2f);
        playersWindow.setPosition(0, Gdx.graphics.getHeight() / 2f);
        playersWindow.setMovable(false);
        playersWindow.setRound(true);

        selectionTable = new Table(Controller.getSkin());

        addUsername.setMessageText("username");
        addUser.setPosition(addUsername.getX(), addUsername.getY() - addUser.getHeight() - 10);
        addUser.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                addPlayer();
            }
        });

        colorsSelectBox.setWidth(100);
        colorsSelectBox.setItems(Colors.values());
        colorsSelectBox.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectColor();
            }
        });

        castleSelectBox.setWidth(100);
        castleSelectBox.setItems(turnToArray(GameMenuController.getCurrentGame().getCurrentMap().getCastles().keySet()));
        castleSelectBox.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectCastle();
            }
        });

        selectionTable.add(addUsername).pad(10);
        selectionTable.add(new Label("select your color: ", Controller.getSkin())).pad(10);
        selectionTable.add(colorsSelectBox).pad(10);
        selectionTable.add(new Label("select your castle: ", Controller.getSkin())).pad(10);
        selectionTable.add(castleSelectBox).pad(10).row();
        selectionTable.add(addUser).pad(10);
        selectionTable.setPosition(graphics.getWidth() / 6f, graphics.getHeight() / 3f);
        okButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                next();
            }
        });

        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                cancelGameStartUp();
            }
        });
        showMiniPrev();
        updatePlayerWindow();
    }

    private void showMiniPrev() {
        if (mapImage != null) {
            stage.getActors().removeValue(mapImage, true);
            mapImage.remove();
        }
        mapImage = new Image(controller.addBoarder(Controller.resizer(graphics.getWidth() / 3f, graphics.getHeight() / 2f,
                GameMenuController.getMapPrev(Controller.getCurrentMap(), 4))));
        mapImage.setPosition(graphics.getWidth() - mapImage.getWidth(), graphics.getHeight() - mapImage.getHeight());
        stage.addActor(mapImage);
    }

    private Array<Integer> turnToArray(Set<Integer> integers) {
        Array<Integer> res = new Array<>();
        for (Integer integer : integers) {
            res.add(integer);
        }
        return res;
    }

    private void selectCastle() {
        Integer selected = castleSelectBox.getSelected();
        if (selected == null) return;
        Array<Integer> items = castleSelectBox.getItems();
        items.removeValue(selected, true);
        if (DataBase.getCurrentEmpire().getCastle() != null)
            items.add(GameMenuController.getCurrentGame().getCurrentMap().getCastleNumber(DataBase.getCurrentEmpire().getCastle()));
        GameStartUpMenuController.selectCastle(selected);
        updatePlayerWindow();
        items.sort();
        castleSelectBox.setItems(items);
    }

    private void selectColor() {
        Array<Colors> items = colorsSelectBox.getItems();
        items.removeValue(colorsSelectBox.getSelected(), false);
        if (DataBase.getCurrentEmpire().getColor() != null)
            items.add(DataBase.getCurrentEmpire().getColor());
        GameStartUpMenuController.selectColor(colorsSelectBox.getSelected());
        updatePlayerWindow();
        items.sort();
        colorsSelectBox.setItems(items);
    }

    private String makeLabelForEmpire(Empire empire) {
        Player player = empire.getOwner();
        String result = player.getNickname() + " | @" + player.getUsername() + " | " + player.getSlogan();
        if (empire.getCastle() != null)
            result += " | castle No. " + GameMenuController.getCurrentGame().getCurrentMap().getCastleNumber(empire.getCastle());
        if (empire.getColor() != null)
            result += " | color " + empire.getColor();
        return result;
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
        GameStartUpMenuMessages string = GameStartUpMenuController.addPlayer(addUsername.getText());
        if (string.equals(GameStartUpMenuMessages.SUCCESS))
            updatePlayerWindow();
        else
            showMessage(string.toString());
    }

    private void updatePlayerWindow() {
        playersWindow.clear();
        for (Empire empire : GameMenuController.getCurrentGame().getEmpires()) {
            Label label = new Label(makeLabelForEmpire(empire), Controller.getSkin());
            playersWindow.add(label).top().row();
        }
    }

/*
    private void selectCastle(Matcher matcher) {
        String castle = Controller.removeQuotes(matcher.group("Id"));
        if (Controller.isFieldEmpty(castle))
            System.out.println(GameStartUpMenuMessages.EMPTY_FIELD);
        else if (isNotDigit(castle))
            System.out.println("castle number should be a digit!");
        else
            System.out.println(GameStartUpMenuController.selectCastle(Integer.parseInt(castle)));
    }
*/


    private void cancelGameStartUp() {
        GameStartUpMenuController.cancel();
        controller.changeMenu(Menus.SELECT_MAP_MENU.getMenu(), this);
    }
}
