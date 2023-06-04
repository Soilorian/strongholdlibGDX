package org.example.view.menus.ingamemenus;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import org.example.control.Controller;
import org.example.control.SoundPlayer;
import org.example.control.enums.GameMenuMessages;
import org.example.control.enums.GameStartUpMenuMessages;
import org.example.control.menucontrollers.EntranceMenuController;
import org.example.control.menucontrollers.GameMenuController;
import org.example.control.menucontrollers.inGameControllers.MapViewMenuController;
import org.example.model.AnimatedSprite;
import org.example.model.ingame.map.Tile;
import org.example.view.enums.Menus;
import org.example.view.enums.Sounds;
import org.example.view.enums.commands.InGameMenuCommands;
import org.example.view.menus.Menu;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class MapViewMenu extends Menu {


    private final ArrayList<ArrayList<Image>> mapImages = new ArrayList<>();
    private AnimatedSprite animation;

    public MapViewMenu() {

    }

    private void addAssets() {
        int zoom = MapViewMenuController.getZoom();
        for (int i = 0; i < zoom; i++)
            for (int j = 0; j < zoom; j++){
                Image actor = mapImages.get(i).get(j);
                stage.addActor(actor);
                Label label = new Label(i + " " + j, controller.getSkin());
                label.setPosition(actor.getX(), actor.getY());
                stage.addActor(label);
            }
    }

    private void setAssets() {
        int zoom = MapViewMenuController.getZoom();
        for (int i = 0; i < zoom; i++) {
            ArrayList<Image> images = new ArrayList<>();
            for (int j = 0; j < zoom; j++) {
                Image image = new Image(makeTextureForTile(MapViewMenuController.getViewingX() - zoom / 2 + j,
                        MapViewMenuController.getViewingY() - zoom / 2 + i, zoom));
                image.setPosition((float) (j * Gdx.graphics.getWidth()) / zoom,
                        (float) ((zoom - i -1) * Gdx.graphics.getHeight()) / zoom);
                images.add(image);
            }
            mapImages.add(images);
        }
        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                checkMovement(keycode);
                return super.keyDown(event, keycode);
            }
        });
        stage.setDebugAll(true);
//        animation = new AnimatedSprite(new Texture[]{controller.getLock(), controller.getDefaultMap(),
//                controller.getEntranceBack()}, 2);

    }


    private void checkMovement(int keycode) {
        switch (keycode) {
            case Input.Keys.W: {
                moveUp();
                break;
            }
            case Input.Keys.S: {
                MapViewMenuController.changeViewingY(-1);
                stage.clear();
                mapImages.clear();
                setAssets();
                addAssets();
                break;
            }
            case Input.Keys.I: {
                Gdx.app.exit();
            }
            default:
                return;
        }
    }

    private void moveUp() {
        Array<Actor> actors = stage.getActors();
        int zoom = MapViewMenuController.getZoom();
        for (int i = 0; i < zoom; i++) actors.removeValue(mapImages.get(0).get(i), true);

        for (int i = 0; i < zoom - 1; i++) mapImages.set(i + 1, mapImages.get(i));

        ArrayList<Image> images = new ArrayList<>();
        for (int j = 0; j < zoom; j++) {
            Image image = new Image(makeTextureForTile(MapViewMenuController.getViewingX() - zoom / 2 + j,
                    MapViewMenuController.getViewingY() - zoom / 2, zoom));
            image.setPosition((float) (j * Gdx.graphics.getWidth()) / zoom,
                    0);
            images.add(image);
            stage.addActor(image);
        }
        mapImages.set(0, images);
    }

    private Texture makeTextureForTile(int x, int y, int z) {
        System.out.printf("loading %d , %d with zoom %d\n", x, y, z);
        Tile tile = GameMenuController.getCurrentGame().getCurrentMap().getTile(y, x);
        if (tile != null) {
            return tile.getTexture(z);
        } else
            return Controller.resizer((float) Gdx.graphics.getWidth() / z, (float) Gdx.graphics.getHeight() / z,
                    controller.getBlackMap());
    }


    public void run(String input) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        System.out.println("tell me where do you want to view");
        do {
            Matcher matcher;
            if (input.equalsIgnoreCase("show menu"))
                System.out.println(Menus.getNameByObj(this) + " first do while");
            else if (InGameMenuCommands.getMatcher(input, InGameMenuCommands.BACK) != null)
                return;
            else if ((matcher = InGameMenuCommands.getMatcher(input, InGameMenuCommands.showMeXY)) != null) {
                if (showXY(matcher)) break;
            } else {
                SoundPlayer.play(Sounds.AKHEY);
                System.out.println("invalid command");
            }
        } while (true);
        do {// TODO: 6/1/2023 split to 2 menus
            Matcher matcher;
            if ((matcher = InGameMenuCommands.getMatcher(input, InGameMenuCommands.MOVE_MAP)) != null)
                moveMap(matcher);
            else if (input.equalsIgnoreCase("show menu"))
                System.out.println(Menus.getNameByObj(this) + " second do while");
            else if (InGameMenuCommands.getMatcher(input, InGameMenuCommands.BACK) != null)
                break;
            else if ((matcher = InGameMenuCommands.getMatcher(input, InGameMenuCommands.SHOW_DETAILS)) != null)
                showDetails(matcher);
            else if (input.equalsIgnoreCase("show current coords"))
                System.out.println(MapViewMenuController.getViewingY() + " , " + MapViewMenuController.getViewingX());
            else {
                SoundPlayer.play(Sounds.AKHEY);
                System.out.println("invalid command");
            }
        } while (true);
    }

    @Override
    public void create() {
        setAssets();
        addAssets();
        Gdx.input.setInputProcessor(stage);
    }

    private boolean showXY(Matcher matcher) {
        String xToString = Controller.removeQuotes(matcher.group("X"));
        String yToString = Controller.removeQuotes(matcher.group("Y"));
        if (!checkCoordinates(xToString, yToString))
            return false;
        MapViewMenuController.setViewingX(Integer.parseInt(xToString));
        MapViewMenuController.setViewingY(Integer.parseInt(yToString));
        System.out.println(MapViewMenuController.printMap());
        return true;
    }

    private void showDetails(Matcher matcher) {
        String xToString = Controller.removeQuotes(matcher.group("X"));
        String yToString = Controller.removeQuotes(matcher.group("Y"));
        if (checkCoordinates(xToString, yToString))
            System.out.println(MapViewMenuController.showDetails(Integer.parseInt(xToString), Integer.parseInt(yToString)));
    }

    private boolean checkCoordinates(String x, String y) {
        if (Controller.isFieldEmpty(x, y))
            System.out.println(GameStartUpMenuMessages.EMPTY_FIELD);
        else if (!EntranceMenuController.isDigit(x, y))
            System.out.println("not valid coordinates!\ncoordinates should only contain digits");
        else return true;
        return false;
    }

    private void moveMap(Matcher matcher) {
        String[] moves = Controller.removeQuotes(matcher.group("Direction")).split(" ");
        for (String move : moves) {
            if (!move.matches("(up)|(down)|(right)|(left)")) {
                System.out.println(GameMenuMessages.WRONG_DIRECTION);
                return;
            }
        }
        int lastX = MapViewMenuController.getViewingX();
        int lastY = MapViewMenuController.getViewingY();
        for (String move : moves) {
            switch (move) {
                case "up": {
                    lastY--;
                    break;
                }
                case "down": {
                    lastY++;
                    break;
                }
                case "left": {
                    lastX--;
                    break;
                }
                case "right": {
                    lastX++;
                    break;
                }
            }
        }
        if (!Controller.getCurrentMap().isInRange(lastX, lastY))
            System.out.println(GameMenuMessages.NOT_IN_RANGE);
        else {
            MapViewMenuController.setViewingX(lastX);
            MapViewMenuController.setViewingY(lastY);
            System.out.println(MapViewMenuController.printMap());
        }
    }
}


