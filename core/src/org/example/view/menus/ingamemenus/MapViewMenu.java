package org.example.view.menus.ingamemenus;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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

import static org.example.control.menucontrollers.inGameControllers.MapViewMenuController.zoom;

public class MapViewMenu extends Menu {


    private final ArrayList<ArrayList<Image>> mapImages = new ArrayList<>();
    private AnimatedSprite animation;

    public MapViewMenu() {

    }

    private void addAssets() {
        int zoom = MapViewMenuController.getZoom();
        for (int i = 0; i < zoom; i++) for (int j = 0; j < zoom; j++) stage.addActor(mapImages.get(i).get(j));
    }

    private void setAssets() {
        int zoom = MapViewMenuController.getZoom();
        for (int i = 0; i < zoom; i++) {
            ArrayList<Image> images = new ArrayList<>();
            for (int j = 0; j < zoom; j++) {
                Image image = new Image(makeTextureForTile(MapViewMenuController.getViewingX() - zoom / 2 + j,
                        MapViewMenuController.getViewingY() - zoom / 2 + i, zoom));
                image.setPosition((float) (j * Gdx.graphics.getWidth()) / zoom,
                        (float) ((zoom - i - 1) * Gdx.graphics.getHeight()) / zoom);
                images.add(image);
//                image.addListener(new ClickListener(){
//                    @Override
//                    public void clicked(InputEvent event, float x, float y) {
//
//                    }
//                });
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
            default:
                return;
        }
    }

    private void moveUp() {
        Array<Actor> actors = stage.getActors();
        int zoom = MapViewMenuController.getZoom();
        for (int i = 0; i < zoom; i++) actors.removeValue(mapImages.get(zoom - 1).get(i), true); // 4 --> dir
        for (int i = 0; i < zoom - 1; i++)
            for (int j = 0; j < zoom; j++) moveTileVertically(mapImages.get(i).get(j), i + 1); // +1 --> dir
        for (int i = zoom - 2; i >= 0; i--) mapImages.set(i + 1, mapImages.get(i)); // --> dir
        ArrayList<Image> images = new ArrayList<>();
        MapViewMenuController.changeViewingY(-1); // --> dir
        for (int j = 0; j < zoom; j++) { // --> dir
            Image image = new Image(makeTextureForTile(MapViewMenuController.getViewingX() - zoom / 2 + j,
                    MapViewMenuController.getViewingY() - zoom / 2, zoom));
            image.setPosition((float) (j * Gdx.graphics.getWidth()) / zoom,
                    (float) ((zoom - 1) * Gdx.graphics.getHeight()) / zoom);
            images.add(image);
            stage.addActor(image);
        }
        mapImages.set(0, images); // 0 --> dir
    }

    private void moveDown() {
        Array<Actor> actors = stage.getActors();
        int zoom = MapViewMenuController.getZoom();
        for (int i = 0; i < zoom; i++) actors.removeValue(mapImages.get(0).get(i), true); // 4 --> dir
        for (int i = 1; i < zoom; i++)
            for (int j = 0; j < zoom; j++) moveTileVertically(mapImages.get(i).get(j), i - 1); // +1 --> dir
        for (int i = 1; i < zoom; i++) mapImages.set(i - 1, mapImages.get(i)); // --> dir
        ArrayList<Image> images = new ArrayList<>();
        MapViewMenuController.changeViewingY(1); // --> dir
        for (int j = 0; j < zoom; j++) { // --> dir
            Image image = new Image(makeTextureForTile(MapViewMenuController.getViewingX() - zoom / 2 + j,
                    MapViewMenuController.getViewingY() - zoom / 2 + zoom - 1, zoom));
            image.setPosition((float) (j * Gdx.graphics.getWidth()) / zoom,
                    0);
            images.add(image);
            stage.addActor(image);
        }
        mapImages.set(zoom - 1, images); // 0 --> dir
    }

    private void moveLeft() {
        Array<Actor> actors = stage.getActors();
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
            Image image = new Image(makeTextureForTile(MapViewMenuController.getViewingX() - zoom / 2,
                    MapViewMenuController.getViewingY() - zoom / 2 + j, zoom));
            image.setPosition(0, (float) ((zoom - j - 1) * Gdx.graphics.getHeight()) / zoom);
            images.add(image);
            stage.addActor(image);
        }
        for (int i = 0; i < zoom; i++) {
            mapImages.get(i).set(0, images.get(i));
        } // 0 --> dir
    }

    private void moveRight() {
        Array<Actor> actors = stage.getActors();
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
            Image image = new Image(makeTextureForTile(MapViewMenuController.getViewingX() - zoom / 2 + zoom - 1,
                    MapViewMenuController.getViewingY() - zoom / 2 + j, zoom));
            image.setPosition((float) ((zoom - 1) * Gdx.graphics.getWidth()) / zoom,
                    (float) ((zoom - j - 1) * Gdx.graphics.getHeight()) / zoom);
            images.add(image);
            stage.addActor(image);
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


