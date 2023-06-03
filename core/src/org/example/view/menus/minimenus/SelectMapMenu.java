package org.example.view.menus.minimenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import org.example.control.Controller;
import org.example.control.menucontrollers.GameMenuController;
import org.example.control.menucontrollers.MapBuilderMenuController;
import org.example.model.DataBase;
import org.example.model.exceptions.CoordinatesOutOfMap;
import org.example.model.exceptions.NotInStoragesException;
import org.example.model.ingame.map.Map;
import org.example.view.enums.Menus;
import org.example.view.menus.Menu;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.badlogic.gdx.Gdx.graphics;

public class SelectMapMenu extends Menu {
    private final SelectBox<String> mapSelectBox = new SelectBox<>(controller.getSkin()), randomMapSelectBox =
            new SelectBox<>(controller.getSkin());
    private final Image backgroundImage;
    private final TextButton buildButton = new TextButton("build", controller.getSkin());
    private final ImageButton randomMapButton, showMapsButton, createBlankMapButton;
    private Image mapImage;

    public SelectMapMenu() {
        randomMapButton = new ImageButton(new TextureRegionDrawable(controller.getRandomMapIcon()));
        backgroundImage = new Image(controller.resizer(graphics.getWidth(), graphics.getHeight(), controller.getMainMenuBackground()));
        showMapsButton = new ImageButton(new TextureRegionDrawable(controller.getShowAllMapsIcon()));
        createBlankMapButton = new ImageButton(new TextureRegionDrawable(controller.resizer(222, 227, controller.getBlankMapIcon())));
    }

    @Override
    public void create() {
        setActors();
        addActors();
        Gdx.input.setInputProcessor(stage);
    }

    private void addActors() {
        stage.clear();
        stage.addActor(backgroundImage);
        stage.addActor(cancelButton);
        stage.addActor(randomMapButton);
        stage.addActor(showMapsButton);
        stage.addActor(createBlankMapButton);
        stage.addActor(okButton);
        stage.addActor(mapImage);
    }

    private void setActors() {
        randomMapButton.setPosition(graphics.getWidth() / 4f - randomMapButton.getWidth(),
                graphics.getHeight() / 3f *2);
        randomMapButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                randomMap();
            }
        });

        showMapsButton.setPosition(graphics.getWidth() / 4f * 2 - showMapsButton.getWidth()/2,
                graphics.getHeight() / 3f *2);
        showMapsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showMaps();
            }
        });

        createBlankMapButton.setPosition(graphics.getWidth() / 4f * 3,
                graphics.getHeight() / 3f *2);
        createBlankMapButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                createMap();
            }
        });

        okButton.setDisabled(true);

        buildButton.setPosition(graphics.getWidth(), graphics.getHeight());

        showMiniPrev();
    }

    private void showMaps() {
        showMiniPrev();
        ArrayList<String > fuck = new ArrayList<>();
        for (Map map : DataBase.getMaps()) {
            fuck.add(makeTextForLabel(map));
        }
        mapSelectBox.setItems(Arrays.toString(fuck.toArray()));
        mapSelectBox.setWidth(graphics.getWidth() / 2f);
        mapSelectBox.setPosition(graphics.getWidth() / 4f, graphics.getHeight() / 3f * 1.9f);
        mapSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setSelectedMap();
            }
        });
        stage.addActor(mapSelectBox);

        okButton.setDisabled(false);
        okButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                next();
            }
        });
    }

    private void setSelectedMap() {
        Controller.setCurrentMap(DataBase.getMapById(extractId(mapSelectBox.getSelected())));
    }

    private void next() {
        GameMenuController.getCurrentGame().setCurrentMap(getSelectedMap());
        controller.changeMenu(Menus.GAME_START_UP_MENU.getMenu(), this);
    }

    private Map getSelectedMap() {
        return Controller.getCurrentMap();
    }

    private String extractId(String selected) {
        Pattern pattern = Pattern.compile("^(?<want>\\.*) |\\.*$");
        Matcher matcher = pattern.matcher(selected);
        return matcher.group("want");
    }

    private String makeTextForLabel(Map map) {
        return map.getId() + " | maximum players: " + map.getCastles().size() + " | map size: " +
                map.getGroundWidth() + " x " + map.getGroundHeight();
    }

    private void createMap() {
        controller.changeMenu(Menus.MAP_EDIT_MENU.getMenu(), this);
    }

    private void randomMap() {
        randomMapSelectBox.setItems("rock land", "island", "normal");
        randomMapSelectBox.setSelected("normal");
        randomMapSelectBox.setPosition(mapSelectBox.getX(), mapSelectBox.getY());
        randomMapSelectBox.setWidth(200);
        randomMapSelectBox.setHeight(100);
        randomMapSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                buildRandomMap(randomMapSelectBox.getSelected());
            }
        });
        stage.addActor(randomMapSelectBox);

        okButton.setDisabled(false);
    }

    private void buildRandomMap(String selected) {
        switch (selected) {
            case "rock land":{
                MapBuilderMenuController.createRockLand();
                break;
            }
            case "island":{
                MapBuilderMenuController.createIsland();
                break;
            }
            case "normal":{
                MapBuilderMenuController.createBaseLand();
                break;
            }
        }

        showMiniPrev();
    }
    private void showMiniPrev() {
        Map selectedMap = getSelectedMap();
        int scale = 2;
        Pixmap pixmap = new Pixmap( selectedMap.getGroundWidth() * scale, selectedMap.getGroundHeight() * scale,
                Pixmap.Format.RGBA8888);
        selectedMap.setUpPixmap(pixmap, scale);
        mapImage = new Image(new Texture(pixmap));
        pixmap.dispose();
        mapImage.setPosition(graphics.getWidth() / 4f * 3, graphics.getHeight() / 4f);
        stage.addActor(mapImage);
    }

}
