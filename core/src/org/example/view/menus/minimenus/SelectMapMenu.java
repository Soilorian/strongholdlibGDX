package org.example.view.menus.minimenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import org.example.control.menucontrollers.GameMenuController;
import org.example.model.DataBase;
import org.example.model.exceptions.CoordinatesOutOfMap;
import org.example.model.exceptions.NotInStoragesException;
import org.example.model.ingame.map.Map;
import org.example.view.enums.Menus;
import org.example.view.enums.commands.EntranceMenuCommands;
import org.example.view.menus.GameStartUpMenu;
import org.example.view.menus.Menu;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SelectMapMenu extends Menu {
    private final ImageButton randomMapButton, showMapsButton, createBlankMapButton;
    private final SelectBox<String > mapSelectBox = new SelectBox<>(controller.getSkin());

    public SelectMapMenu() {
        randomMapButton = new ImageButton(new TextureRegionDrawable(controller.getRandomMapIcon()));
        showMapsButton = new ImageButton(new TextureRegionDrawable(controller.getShowAllMapsIcon()));
        createBlankMapButton = new ImageButton(new TextureRegionDrawable(controller.resizer(222, 227, controller.getBlankMapIcon())));
    }

    @Override
    protected void run(String input) throws IOException, UnsupportedAudioFileException, LineUnavailableException, CoordinatesOutOfMap, NotInStoragesException {

    }

    @Override
    public void create() {
        setActors();
        addActors();
        Gdx.input.setInputProcessor(stage);
    }

    private void addActors() {
        stage.clear();
        stage.addActor(cancelButton);
        stage.addActor(randomMapButton);
        stage.addActor(showMapsButton);
        stage.addActor(createBlankMapButton);
        stage.addActor(okButton);
    }

    private void setActors() {

        randomMapButton.setPosition(Gdx.graphics.getWidth() / 4f - randomMapButton.getWidth(),
                Gdx.graphics.getHeight() / 3f *2);
        randomMapButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                randomMap();
            }
        });

        showMapsButton.setPosition(Gdx.graphics.getWidth() / 4f * 2 - showMapsButton.getWidth()/2,
                Gdx.graphics.getHeight() / 3f *2);
        showMapsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showMaps();
            }
        });

        createBlankMapButton.setPosition(Gdx.graphics.getWidth() / 4f * 3,
                Gdx.graphics.getHeight() / 3f *2);
        createBlankMapButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                createMap();
            }
        });

        okButton.setDisabled(true);
    }

    private void showMaps() {
        Map selectedMap = getSelectedMap();
        int scale = 2;
        Pixmap pixmap = new Pixmap( selectedMap.getGroundWidth() * scale, selectedMap.getGroundHeight() * scale,
                Pixmap.Format.RGBA8888);
        selectedMap.setUpPixmap(pixmap, scale);
        Image mapPrev = new Image(new Texture(pixmap));
        mapPrev.setPosition(Gdx.graphics.getWidth() / 4f * 3, Gdx.graphics.getHeight() / 4f);
        stage.addActor(mapPrev);
        ArrayList<String > fuck = new ArrayList<>();
        for (Map map : DataBase.getMaps()) {
            fuck.add(makeTextForLabel(map));
        }
        mapSelectBox.setItems(Arrays.toString(fuck.toArray()));
        mapSelectBox.setWidth(Gdx.graphics.getWidth() / 2f);
        mapSelectBox.setPosition(Gdx.graphics.getWidth() / 4f, Gdx.graphics.getHeight() / 3f * 1.9f);
        stage.addActor(mapSelectBox);
        okButton.setDisabled(false);
        okButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                next();
            }
        });
    }

    private void next() {
        GameMenuController.getCurrentGame().setCurrentMap(getSelectedMap());
        controller.changeMenu(Menus.GAME_START_UP_MENU.getMenu(), this);
    }

    private Map getSelectedMap() {
        return DataBase.getMapById(extractId(mapSelectBox.getSelected()));
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
        controller.changeMenu(Menus.RANDOM_MAP_MENU.getMenu(), this);
    }
}
