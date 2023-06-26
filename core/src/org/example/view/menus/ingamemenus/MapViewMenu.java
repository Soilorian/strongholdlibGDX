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
import org.example.control.menucontrollers.GameMenuController;
import org.example.control.menucontrollers.inGameControllers.MapViewMenuController;
import org.example.model.ingame.map.Tile;
import org.example.view.menus.Menu;

import java.util.ArrayList;

import static org.example.control.menucontrollers.inGameControllers.MapViewMenuController.zoom;

public class MapViewMenu extends Menu {
    private final ArrayList<ArrayList<Image>> mapImages = new ArrayList<>();

    private void addAssets() {
        int zoom = MapViewMenuController.getZoom();
        for (int i = 0; i < zoom; i++) for (int j = 0; j < zoom; j++) behindStage.addActor(mapImages.get(i).get(j));
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
        Array<Actor> actors = behindStage.getActors();
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
            Image image = new Image(makeTextureForTile(MapViewMenuController.getViewingX() - zoom / 2 + j,
                    MapViewMenuController.getViewingY() - zoom / 2 + zoom - 1, zoom));
            image.setPosition((float) (j * Gdx.graphics.getWidth()) / zoom,
                    0);
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
            Image image = new Image(makeTextureForTile(MapViewMenuController.getViewingX() - zoom / 2,
                    MapViewMenuController.getViewingY() - zoom / 2 + j, zoom));
            image.setPosition(0, (float) ((zoom - j - 1) * Gdx.graphics.getHeight()) / zoom);
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
            Image image = new Image(makeTextureForTile(MapViewMenuController.getViewingX() - zoom / 2 + zoom - 1,
                    MapViewMenuController.getViewingY() - zoom / 2 + j, zoom));
            image.setPosition((float) ((zoom - 1) * Gdx.graphics.getWidth()) / zoom,
                    (float) ((zoom - j - 1) * Gdx.graphics.getHeight()) / zoom);
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
        return tile.getTexture(z);
//        if (tile != null) {
//            return tile.getTexture(z);
//        } else
//            return Controller.resizer((float) Gdx.graphics.getWidth() / z, (float) Gdx.graphics.getHeight() / z,
//                    controller.getBlackMap());
    }

    @Override
    public void create() {
        setAssets();
        addAssets();
        Gdx.input.setInputProcessor(behindStage);
    }
}


