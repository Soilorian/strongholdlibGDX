package org.example.view.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import org.example.control.Controller;
import org.example.control.SoundPlayer;
import org.example.control.enums.EntranceMenuMessages;
import org.example.control.enums.GameMenuMessages;
import org.example.control.enums.GameStartUpMenuMessages;
import org.example.control.menucontrollers.EntranceMenuController;
import org.example.control.menucontrollers.GameMenuController;
import org.example.control.menucontrollers.inGameControllers.MapViewMenuController;
import org.example.model.*;
import org.example.model.exceptions.CoordinatesOutOfMap;
import org.example.model.exceptions.NotInStoragesException;
import org.example.model.ingame.castle.Building;
import org.example.model.ingame.castle.Buildings;
import org.example.model.ingame.humans.army.Troop;
import org.example.model.ingame.humans.army.Troops;
import org.example.model.ingame.map.Tile;
import org.example.view.enums.Menus;
import org.example.view.enums.Sounds;
import org.example.view.enums.commands.GameMenuCommands;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;

import static org.example.control.menucontrollers.inGameControllers.MapViewMenuController.*;

public class GameMenu extends Menu {
    private final ArrayList<ArrayList<Image>> mapImages = new ArrayList<>();
    Window[] menuWindows = new Window[6];
    ImageButton[] imageButtons = new ImageButton[8];
    private Image mapPrevImage = new Image();
    private Window detailWindow = new Window("details", Controller.getSkin());

    public static void reload() {
        ((GameMenu) Menus.GAME_MENU.getMenu()).reloadMap();
    }

    public void reloadMap() {
        behindStage.clear();
        setupMap();
        addMapAssets();
        Gdx.input.setInputProcessor(behindStage);
    }

    private void addAssets() {
        addMapAssets();
        addMiniMapAssets();
        addMenuAssets();
        addWiseManAssets();
    }

    private void addWiseManAssets() {

    }

    private void addMenuAssets() {
        for (Window menuWindow : menuWindows) {
            menuWindow.setVisible(false);
            frontStage.addActor(menuWindow);
            menuWindow.setDebug(true);
        }
        menuWindows[0].setVisible(true);
        for (Window menuWindow : menuWindows) {
            for (ImageButton imageButton : imageButtons) {
                menuWindow.add(imageButton);
            }
        }
        menuWindows[0].add(imageButtons[0]);
    }

    private void addMiniMapAssets() {
        frontStage.addActor(mapPrevImage);
    }

    private void addMapAssets() {
        int zoom = MapViewMenuController.getZoom();
        for (int i = 0; i < zoom; i++) for (int j = 0; j < zoom; j++) behindStage.addActor(mapImages.get(i).get(j));
    }

    private void setAssets() {
        setupMap();
        setupMenus();
        setupMiniMap();
        setupWiseMan();
    }

    private void setupWiseMan() {
        // TODO: 6/6/2023 armiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiin
    }

    private void setupMenus() {
        Window window = null;
        Buildings[] values = Buildings.values();
        for (int i = 0; i < values.length; i++) {
            if (i % 8 == 0) {
                window = new Window("buildings", Controller.getSkin());
                window.setWidth(Gdx.graphics.getWidth() - 800);
                window.setHeight(400);
                window.setPosition(400, 0);
                final int i1 = i / 8;
                menuWindows[i1] = window;
                ImageButton imageButton = new ImageButton(new TextureRegionDrawable(Controller.getPictureOf(i1 + 1)));
                imageButton.setPosition(getButtonXForMenu(i1), getButtonYForMenu(i1));
                imageButtons[i1] = imageButton;
                imageButtons[i1].addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        changeWindow(i1);
                    }
                });
            }
            Buildings buildings = values.clone()[i];
            Image image = new Image(Controller.resizer(80, 80, buildings.getTexture()));
            image.addAction(new BuildingReturnerAction(buildings));
            image.addListener(new BuildingDragger());
            image.setPosition(getRelativeX(i), getRelativeY(i));
            window.add(image);
        }
    }

    private float getButtonYForMenu(int i) {
        i %= 3;
        return 400 / 3f * (2 - i);
    }

    private float getButtonXForMenu(int i) {
        if (i < 3) return 0;
        else return Gdx.graphics.getWidth() - 800 - 400 / 3f;
    }

    private float getRelativeY(int i) {
        i %= 2;
        if (i == 0) return mapPrevImage.getHeight() / 4;
        else return mapPrevImage.getHeight() * 3 / 4;
    }

    private float getRelativeX(int i) {
        i %= 8;
        switch (i / 2) {
            case 0:
                return mapPrevImage.getWidth() + ((float) (Gdx.graphics.getWidth() - 800) / 6);
            case 1:
                return mapPrevImage.getWidth() + ((float) (Gdx.graphics.getWidth() - 800) / 3);
            case 2:
                return mapPrevImage.getWidth() + ((float) (Gdx.graphics.getWidth() - 800) / 2);
            case 3:
                return mapPrevImage.getWidth() + ((float) (Gdx.graphics.getWidth() - 800) * 2 / 3);
            default:
                System.out.println("ey vay, getRelativeX");
        }
        return 0;
    }

    private void changeWindow(int i1) {
        for (Window menuWindow : menuWindows) {
            menuWindow.setVisible(false);
        }
        menuWindows[i1].setVisible(true);
    }

    private void setupMap() {
        MapViewMenuController.setViewingY(GameMenuController.getCurrentGame().getCurrentMap().getGroundHeight()/2);
        MapViewMenuController.setViewingX(GameMenuController.getCurrentGame().getCurrentMap().getGroundWidth()/2);
        for (int i = 0; i < zoom; i++) {
            ArrayList<Image> images = new ArrayList<>();
            for (int j = 0; j < zoom; j++) {
                Image image = getImage(i, j);
                images.add(image);
            }
            mapImages.add(images);

        }
        behindStage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                checkMovement(keycode);
                return super.keyDown(event, keycode);
            }
        });
    }

    private Image getImage(int i, int j) {
        Image image = new Image(makeTextureForTile(MapViewMenuController.getViewingX() - zoom / 2 + j,
                MapViewMenuController.getViewingY() - zoom / 2 + i, zoom));
        image.setPosition((float) (j * Gdx.graphics.getWidth()) / zoom,
                (float) ((zoom - i - 1) * Gdx.graphics.getHeight()) / zoom);
        Tile tile = getTile(getViewingX() - zoom / 2 + j,
                getViewingY() - zoom / 2 + i);
        image.addAction(new GetTileAction(tile));
        TextTooltip listener = new MyTextTooltip("", Controller.getSkin(), tile);
        image.addListener(listener);
        image.addListener(new SelectListener());
        return image;
    }

    private Tile getTile(int x, int y) {
        return GameMenuController.getCurrentGame().getCurrentMap().getTile(y, x);
    }

    private void hideDetails() {

    }


    private void checkMovement(int keycode) {
        switch (keycode) {
            case Input.Keys.W: {
                moveUp();
                break;
            }
            case Input.Keys.S: {
                moveDown();
                break;
            }
            case Input.Keys.I: {
                Gdx.app.exit();
                break;
            }
            case Input.Keys.A: {
                moveLeft();
                break;
            }
            case Input.Keys.D: {
                moveRight();
                break;
            }
            case Input.Keys.V: {
                viewPosition();
                break;
            }
            case Input.Keys.C:{
                copy();
                break;
            }
            case Input.Keys.P:{
                paste();
                break;
            }
            default:
                return;
        }
        setupMiniMap();
        addMiniMapAssets();
    }

    private void paste() {
        GameMenuController.placeCopiedBuildings();
    }

    private void copy() {
        GameMenuController.transferSelectedIntoCopy();
    }

    private void viewPosition() {
        {
            Window window = new Window("give me", Controller.getSkin());
            window.setWidth(2000);
            window.setHeight(200);
            window.add(new Label("x: ", Controller.getSkin()));
            TextField xField = new TextField("", Controller.getSkin());
            window.add(xField);
            window.add(new Label("y: ", Controller.getSkin()));
            TextField yField = new TextField("", Controller.getSkin());
            window.add(yField);
            window.add(okButton);
            okButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (!xField.getText().isEmpty() && !yField.getText().isEmpty()) {
                        setViewingX(Integer.parseInt(xField.getText()));
                        setViewingY(Integer.parseInt(yField.getText()));
                        create();
                    }
                }
            });
            frontStage.addActor(window);

        }
    }

    private void moveUp() {
        Array<Actor> actors = behindStage.getActors();
        int zoom = MapViewMenuController.getZoom();
        for (int i = 0; i < zoom; i++) actors.removeValue(mapImages.get(zoom - 1).get(i), true);
        for (int i = 0; i < zoom - 1; i++)
            for (int j = 0; j < zoom; j++) moveTileVertically(mapImages.get(i).get(j), i + 1); // +1 --> dir
        for (int i = zoom - 2; i >= 0; i--) mapImages.set(i + 1, mapImages.get(i)); // --> dir
        ArrayList<Image> images = new ArrayList<>();
        MapViewMenuController.changeViewingY(-1); // --> dir
        for (int j = 0; j < zoom; j++) { // --> dir
            Image image = getImage(0, j);
            images.add(image);
            behindStage.addActor(image);
        }
        mapImages.set(0, images); // 0 --> dir
    }

    private void moveDown() {
        Array<Actor> actors = behindStage.getActors();
        int zoom = MapViewMenuController.getZoom();
        for (int i = 0; i < zoom; i++) actors.removeValue(mapImages.get(0).get(i), true); // 4 --> dir
        for (int i = 1; i < zoom; i++)
            for (int j = 0; j < zoom; j++) moveTileVertically(mapImages.get(i).get(j), i - 1); // +1 --> dir
        for (int i = 1; i < zoom; i++) mapImages.set(i - 1, mapImages.get(i)); // --> dir
        ArrayList<Image> images = new ArrayList<>();
        MapViewMenuController.changeViewingY(1); // --> dir
        for (int j = 0; j < zoom; j++) { // --> dir
            Image image = getImage( zoom - 1, j);
            images.add(image);
            behindStage.addActor(image);
        }
        mapImages.set(zoom - 1, images); // 0 --> dir
    }

    private void moveLeft() {
        Array<Actor> actors = behindStage.getActors();
        int zoom = MapViewMenuController.getZoom();
        for (int i = 0; i < zoom; i++) actors.removeValue(mapImages.get(i).get(zoom - 1), true); // 4 --> dir
        for (int i = 0; i < zoom; i++)
            for (int j = 0; j < zoom - 1; j++) moveTileHorizontally(mapImages.get(i).get(j), j + 1); // +1 --> dir
        for (int i = zoom - 2; i >= 0; i--)
            for (int j = 0; j < zoom; j++) {
                mapImages.get(j).set(i + 1, mapImages.get(j).get(i));
            } // --> dir
        ArrayList<Image> images = new ArrayList<>();
        MapViewMenuController.changeViewingX(-1); // --> dir
        for (int j = 0; j < zoom; j++) { // --> dir
            Image image = getImage( j, 0);
            images.add(image);
            behindStage.addActor(image);
        }
        for (int i = 0; i < zoom; i++) {
            mapImages.get(i).set(0, images.get(i));
        } // 0 --> dir
    }

    private void moveRight() {
        Array<Actor> actors = behindStage.getActors();
        int zoom = MapViewMenuController.getZoom();
        for (int i = 0; i < zoom; i++) actors.removeValue(mapImages.get(i).get(0), true); // 4 --> dir
        for (int i = 0; i < zoom; i++)
            for (int j = 1; j < zoom; j++) moveTileHorizontally(mapImages.get(i).get(j), j - 1); // +1 --> dir
        for (int i = 1; i < zoom; i++)
            for (int j = 0; j < zoom; j++) {
                mapImages.get(j).set(i - 1, mapImages.get(j).get(i));
            } // --> dir
        ArrayList<Image> images = new ArrayList<>();
        MapViewMenuController.changeViewingX(1); // --> dir
        for (int j = 0; j < zoom; j++) { // --> dir
            Image image = getImage(j,zoom - 1);
            images.add(image);
            behindStage.addActor(image);
        }
        for (int i = 0; i < zoom; i++) {
            mapImages.get(i).set(zoom - 1, images.get(i));
        } // 0 --> dir
    }


    private void moveTileVertically(Image image, int row) {
        image.setY((float) (zoom - row - 1) * Gdx.graphics.getHeight() / zoom);
    }

    private void moveTileHorizontally(Image image, int col) {
        image.setX((float) col * Gdx.graphics.getWidth() / zoom);
    }


    private Texture makeTextureForTile(int x, int y, int z) {
        Tile tile = GameMenuController.getCurrentGame().getCurrentMap().getTile(y, x);
        if (tile != null) {
            return tile.getTexture(z);
        } else
            return Controller.resizer((float) Gdx.graphics.getWidth() / z, (float) Gdx.graphics.getHeight() / z,
                    controller.getBlackMap());
    }


    @Override
    public void create() {
        behindStage.clear();
        mapPrevImage.clear();
        mapImages.clear();
        setAssets();
        addAssets();
        Gdx.input.setInputProcessor(behindStage);
    }

    private void setupMiniMap() {
        Pixmap pixmap = GameMenuController.getMapPrevPixmap(GameMenuController.getCurrentGame().getCurrentMap(),
                2);
        pixmap.setColor(Color.WHITE);
        pixmap.drawRectangle(MapViewMenuController.getViewingX() * 2, getViewingY() * 2,
                zoom, zoom);
        Texture mapPrev = new Texture(pixmap);
        this.mapPrevImage = new Image(controller.addBoarder(mapPrev));
        mapPrevImage.setPosition(0, 0);
        mapPrevImage.setWidth(400);
        mapPrevImage.setHeight(400);
        mapPrevImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                quickMove(x, y);
            }
        });
    }

    private void quickMove(float x, float y) {
        MapViewMenuController.setViewingX((int) (x / 2));
        MapViewMenuController.setViewingY((int) (200 - y / 2));
        reloadMap();
    }

    private void exitApp() {
        controller.exitGame();
    }

    public void run(String command) throws IOException, UnsupportedAudioFileException, LineUnavailableException, CoordinatesOutOfMap, NotInStoragesException {
        Matcher matcher;
        while (true) {
            if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.MOVE_UNIT)) != null)
                moveUnit(matcher);
            else if (command.equalsIgnoreCase("show menu"))
                System.out.println(Menus.getNameByObj(this));
            else if (GameMenuCommands.getMatcher(command, GameMenuCommands.SHOW_POPULARITY_FACTORS) != null)
                showPopularityFactors();
            else if (GameMenuCommands.getMatcher(command, GameMenuCommands.REPAIR) != null)
                repair();
            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.CHANGE_FEAR_RATE)) != null)
                setFearRate(matcher);
            else if ((GameMenuCommands.getMatcher(command, GameMenuCommands.SHOW_FEAR_RATE) != null))
                showFearRate();
            else if ((GameMenuCommands.getMatcher(command, GameMenuCommands.SHOW_POPULARITY) != null))
                showPopularity();
            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.SELECT_BUILDING)) != null)
                selectBuilding(matcher);
            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.SELECT_UNIT)) != null)
                selectUnit(matcher);
            else if (GameMenuCommands.getMatcher(command, GameMenuCommands.SHOW_MAP) != null)
                showMap();
            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.PATROL_UNIT)) != null)
                patrolUnit(matcher);
            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.SET_STRATEGY)) != null)
                setUnitStrategy(matcher);
            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.ATTACK)) != null)
                attack(matcher);
            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.POUR_OIL)) != null)
                pourOil(matcher);
            else if (GameMenuCommands.getMatcher(command, GameMenuCommands.DISBAND) != null)
                disbandUnit();
            else if (GameMenuCommands.getMatcher(command, GameMenuCommands.TRADE) != null) {
                controller.setScreen(Menus.TRADE_MENU.getMenu());
                controller.changeMenu(this, this);
            } else if (GameMenuCommands.getMatcher(command, GameMenuCommands.NEXT_TURN) != null) {
                if (nextTurn())
                    break;
            } else if (GameMenuCommands.getMatcher(command, GameMenuCommands.OPEN_TRADE_MENU) != null)
                GameMenuController.openTradeMenu();
            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.DIG_TUNNEL)) != null)
                digTunnel(matcher);
            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.GET_GOLD)) != null)
                getGold(matcher);
            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.GET_ITEM)) != null)
                getItem(matcher);
            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.SPAWN_TROOP)) != null)
                spawnTroop(matcher);
            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.CHANGE_POPULARITY)) != null)
                changePopularity(matcher);
            else if ((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.CHOOSE_PLAYER)) != null)
                choosePlayer(matcher);
            else if (GameMenuCommands.getMatcher(command, GameMenuCommands.INFINITE_HEALTH) != null)
                infiniteHealth();
            else if ((GameMenuCommands.getMatcher(command, GameMenuCommands.SHOW_PLAYER)) != null)
                showCurrentPlayer();
            else if ((command.equalsIgnoreCase("open music player"))) {
                controller.setScreen(Menus.MUSIC_CONTROL_MENU.getMenu());
                controller.changeMenu(this, this);
            } else if (command.equalsIgnoreCase("show castle coordinates"))
                System.out.println(GameMenuController.showCastleCoordinates());
            else {
                SoundPlayer.play(Sounds.AKHEY);
                System.out.println("invalid command");
            }
        }


    }

    private boolean nextTurn() throws CoordinatesOutOfMap, NotInStoragesException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        return GameMenuController.nextTurn();
    }

    private void attack(Matcher matcher) {
        String x = Controller.removeQuotes(matcher.group("X"));
        String y = Controller.removeQuotes(matcher.group("Y"));
        boolean attackingABuilding = false;
        try {
            if (matcher.group("option") != null)
                attackingABuilding = true;
        } catch (Exception ignored) {
        }
        if (checkCoordinates(x, y))
            if (attackingABuilding)
                System.out.println(GameMenuController.attackBuilding(Integer.parseInt(x), Integer.parseInt(y)));
            else {
                System.out.println(GameMenuController.attackTroop(Integer.parseInt(x), Integer.parseInt(y)));
            }
    }

    private void showMap() throws IOException, UnsupportedAudioFileException, LineUnavailableException, CoordinatesOutOfMap, NotInStoragesException {
        controller.setScreen(Menus.MAP_VIEW_MENU.getMenu());
        controller.changeMenu(this, this);
    }

    private void infiniteHealth() {
        System.out.println(GameMenuController.infiniteHealth() + "immune troops");
    }

    private void choosePlayer(Matcher matcher) {
        String username = Controller.removeQuotes(matcher.group("Player"));
        GameMenuMessages result = GameMenuController.choosePlayer(username);
        if (result.equals(GameMenuMessages.ENJOY)) System.out.println(result + "choice");
        else System.out.println(result);
    }

    private void changePopularity(Matcher matcher) {
        String amount = Controller.removeQuotes(matcher.group("Popularity"));
        if (Controller.isFieldEmpty(amount))
            System.out.println(EntranceMenuMessages.EMPTY_FIELD);
        else if (!EntranceMenuController.isDigit(amount)) {
            System.out.println("invalid amount\ndummy you can't write in english here");
            return;
        }
        GameMenuMessages result = GameMenuController.changePopularity(Integer.parseInt(amount));
        if (result.equals(GameMenuMessages.ENJOY))
            System.out.println(result + amount + " popularity");
        else System.out.println(result);
    }

    private void spawnTroop(Matcher matcher) {
        String x = Controller.removeQuotes(matcher.group("X"));
        String y = Controller.removeQuotes(matcher.group("Y"));
        String amount = Controller.removeQuotes(matcher.group("Amount"));
        String troop = Controller.removeQuotes(matcher.group("Troop"));
        if (Controller.isFieldEmpty(amount, x, y, troop))
            System.out.println(EntranceMenuMessages.EMPTY_FIELD);
        else if (!EntranceMenuController.isDigit(amount, x, y))
            System.out.println("invalid amount\ndummy you can't write in english here");
        else if (checkCoordinates(x, y)) {
            GameMenuMessages result = GameMenuController.spawnUnit(troop, Integer.parseInt(amount), Integer.parseInt(x), Integer.parseInt(y));
            if (result.equals(GameMenuMessages.ENJOY)) System.out.println(result + "new " + amount + " " + troop);
            System.out.println(result);
        }
    }

    private void getItem(Matcher matcher) {
        String amount = Controller.removeQuotes(matcher.group("Amount"));
        String item = Controller.removeQuotes(matcher.group("Item"));
        if (Controller.isFieldEmpty(amount, item))
            System.out.println(EntranceMenuMessages.EMPTY_FIELD);
        else if (!EntranceMenuController.isDigit(amount)) {
            System.out.println("invalid amount\ndummy you can't write in english here");
            return;
        }
        GameMenuMessages result = GameMenuController.getItem(item, Integer.parseInt(amount));
        if (result.equals(GameMenuMessages.ENJOY))
            System.out.println(result + amount + " of " + item);
        else System.out.println(result);
    }

    private void getGold(Matcher matcher) {
        String amount = Controller.removeQuotes(matcher.group("Amount"));
        if (Controller.isFieldEmpty(amount))
            System.out.println(EntranceMenuMessages.EMPTY_FIELD);
        else if (!EntranceMenuController.isDigit(amount)) {
            System.out.println("invalid amount\ndummy you can't write in english here");
            return;
        }
        GameMenuMessages result = GameMenuController.getGold(Integer.parseInt(amount));
        if (result.equals(GameMenuMessages.ENJOY))
            System.out.println(result + amount + " Golds");
        else System.out.println(result);
    }

    private void showPopularityFactors() {
        System.out.println(GameMenuMessages.POPULARITY_FACTORS);
    }

    private void showCurrentPlayer() {
        System.out.println(DataBase.getCurrentEmpire().getOwner().getNickname() + ", " + DataBase.getCurrentEmpire().getColor());
    }

    private void showPopularity() {
        System.out.println(GameMenuMessages.POPULARITY.toString()
                + (DataBase.getCurrentEmpire().getPopularity()));
    }

    private void showFearRate() {
        System.out.println(GameMenuMessages.FEAR_RATE.toString() + DataBase.getCurrentEmpire().getFear());
    }

    private void setFearRate(Matcher matcher) {
        String fear = matcher.group("Rate");
        System.out.println(GameMenuController.setFearRate(fear));
    }

    public void selectBuilding(Matcher matcher) throws IOException, UnsupportedAudioFileException, LineUnavailableException, CoordinatesOutOfMap, NotInStoragesException {
        String x = Controller.removeQuotes(matcher.group("X"));
        String y = Controller.removeQuotes(matcher.group("Y"));
        if (checkCoordinates(x, y))
            System.out.println(GameMenuController.selectBuilding(Integer.parseInt(x), Integer.parseInt(y)));
    }

    private boolean checkCoordinates(String x, String y) {
        if (Controller.isFieldEmpty(x, y))
            System.out.println(GameStartUpMenuMessages.EMPTY_FIELD);
        else if (!EntranceMenuController.isDigit(x, y))
            System.out.println("not valid coordinates!\ncoordinates should only contain digits");
        else return true;
        return false;
    }


    public void repair() {
        System.out.println(GameMenuController.repair());
    }

    public void selectUnit(Matcher matcher) {
        String x = Controller.removeQuotes(matcher.group("X"));
        String y = Controller.removeQuotes(matcher.group("Y"));
        if (checkCoordinates(x, y))
            System.out.println(GameMenuController.selectUnit(Integer.parseInt(x), Integer.parseInt(y)));
    }

    public void moveUnit(Matcher matcher) {
        String x = Controller.removeQuotes(matcher.group("X"));
        String y = Controller.removeQuotes(matcher.group("Y"));
        if (checkCoordinates(x, y))
            System.out.println(GameMenuController.moveUnit(Integer.parseInt(x), Integer.parseInt(y)));
    }

    public void patrolUnit(Matcher matcher) {
        String x = Controller.removeQuotes(matcher.group("X"));
        String y = Controller.removeQuotes(matcher.group("Y"));
        if (checkCoordinates(x, y))
            System.out.println(GameMenuController.patrolUnit(Integer.parseInt(x), Integer.parseInt(y)));
    }

    public void setUnitStrategy(Matcher matcher) {
        String strategy = Controller.removeQuotes(matcher.group("Strategy"));
        System.out.println(GameMenuController.setUnitStrategy(strategy));
    }

    public void pourOil(Matcher matcher) {
        String direction = Controller.removeQuotes(matcher.group("Direction"));
        System.out.println(GameMenuController.pourOil(direction));
    }

    public void digTunnel(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("X"));
        int y = Integer.parseInt(matcher.group("Y"));
        System.out.println(GameMenuController.digTunnel(x, y));
    }

    public void disbandUnit() {
        GameMenuController.disbandUnit();
    }

    public void showSelectedDetails() {
        ArrayList<Tile> tiles = GameMenuController.getSelectedTiles();
        detailWindow.clear();
        HashMap<Troops, Integer> troops = new HashMap<>();
        for (Tile tile : tiles) {
            Building building = tile.getBuilding();
            if (building.getOwner().equals(DataBase.getCurrentEmpire()))
                detailWindow.add(new Image(Controller.resizer(20, 20, building.getTexture()))).row();
            for (Troop troop : tile.getTroops()) {
                if (troop.getKing().equals(DataBase.getCurrentEmpire())) {
                    if (troops.containsKey(troop.getTroop())) {
                        troops.put(troop.getTroop(), 1+ troops.get(troop.getTroop()));
                    } else
                        troops.put(troop.getTroop(), 1);
                }
            }
            for (Troops troop : troops.keySet()) {
                detailWindow.add(new Image(troop.getTexture()));
            }
        }
    }


    private class SelectListener extends ClickListener {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)) {
                Image target = (Image) event.getTarget();
                if (GameMenuController.addSelectedTile(getTile(target))) {
                    super.clicked(event, x, y);
                    return;
                }
                target.setDrawable(new TextureRegionDrawable(Controller.addHighlight(Objects.requireNonNull(getTile(target)).getTexture(zoom))));
                showSelectedDetails();
                super.clicked(event, x, y);
            }
        }

        private Tile getTile(Image target) {
            Action action = target.getActions().get(target.getActions().indexOf(GetTileAction.getInstance(), false));
            return ((GetTileAction) action).getTile();
        }
    }
}
