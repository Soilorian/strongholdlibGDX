package org.example.view.menus.minimenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import org.example.control.Controller;
import org.example.control.menucontrollers.GameMenuController;
import org.example.control.menucontrollers.MapBuilderMenuController;
import org.example.model.DataBase;
import org.example.model.ingame.map.Map;
import org.example.view.enums.Menus;
import org.example.view.menus.Menu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.badlogic.gdx.Gdx.graphics;

public class SelectMapMenu extends Menu {
    private final SelectBox<String> mapSelectBox = new SelectBox<>(Controller.getSkin()), randomMapSelectBox =
            new SelectBox<>(Controller.getSkin());
    private final Image backgroundImage;
    private final TextButton buildButton = new TextButton("build", Controller.getSkin());
    private final ImageButton randomMapButton, showMapsButton, createBlankMapButton;
    private Image mapImage;

    public SelectMapMenu() {
        randomMapButton = new ImageButton(new TextureRegionDrawable(controller.getRandomMapIcon()));
        backgroundImage = new Image(Controller.resizer(graphics.getWidth(), graphics.getHeight(), controller.getMainMenuBackground()));
        showMapsButton = new ImageButton(new TextureRegionDrawable(controller.getShowAllMapsIcon()));
        createBlankMapButton = new ImageButton(new TextureRegionDrawable(Controller.resizer(222, 227, controller.getBlankMapIcon())));
    }

    @Override
    public void create() {
        setActors();
        addActors();
        Gdx.input.setInputProcessor(behindStage);
    }

    private void addActors() {
        behindStage.clear();
        behindStage.addActor(backgroundImage);
        behindStage.addActor(cancelButton);
        behindStage.addActor(randomMapButton);
        behindStage.addActor(showMapsButton);
        behindStage.addActor(createBlankMapButton);
        behindStage.addActor(okButton);
        behindStage.addActor(mapImage);
    }

    private void setActors() {
        randomMapButton.setPosition(graphics.getWidth() / 4f - randomMapButton.getWidth(),
                graphics.getHeight() / 3f * 2);
        randomMapButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                randomMap();
            }
        });

        showMapsButton.setPosition(graphics.getWidth() / 4f * 2 - showMapsButton.getWidth() / 2,
                graphics.getHeight() / 3f * 2);
        showMapsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showMaps();
            }
        });

        createBlankMapButton.setPosition(graphics.getWidth() / 4f * 3,
                graphics.getHeight() / 3f * 2);
        createBlankMapButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                createMap();
            }
        });

        okButton.setDisabled(true);
        okButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                goToGameStartUp();
            }
        });

        cancelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                back();
            }
        });

        buildButton.setPosition(graphics.getWidth(), graphics.getHeight());

        mapSelectBox.setWidth(graphics.getWidth() / 2f);
        mapSelectBox.setHeight(graphics.getHeight() / 8f);
        mapSelectBox.setPosition(graphics.getWidth() / 4f, graphics.getHeight() / 3f * 1.9f);
        mapSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setSelectedMap();
            }
        });

        randomMapSelectBox.setItems("rock land", "island", "normal");
        randomMapSelectBox.setSelected("normal");
        randomMapSelectBox.setPosition(mapSelectBox.getX(), mapSelectBox.getY() / 2);
        randomMapSelectBox.setWidth(100);
        randomMapSelectBox.setHeight(80);
        randomMapSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                buildRandomMap(randomMapSelectBox.getSelected());
            }
        });
        showMiniPrev();
    }

    private void back() {
        controller.changeMenu(Menus.SELECT_SIZE_MENU.getMenu(), this);
    }

    private void goToGameStartUp() {
        GameMenuController.getCurrentGame().setCurrentMap(Controller.getCurrentMap());
        controller.changeMenu(Menus.GAME_START_UP_MENU.getMenu(), this);
    }

    private void showMaps() {
        for (Map map : DataBase.getMaps()) {
            mapSelectBox.getItems().add((makeTextForLabel(map)));
        }
        behindStage.addActor(mapSelectBox);
        okButton.setDisabled(false);
        okButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                next();
            }
        });
    }

    private void setSelectedMap() {
        Controller.setCurrentMap(DataBase.getMapById(extractId(mapSelectBox.getSelected())));
        showMiniPrev();
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
        behindStage.addActor(randomMapSelectBox);

        okButton.setDisabled(false);
    }

    private void buildRandomMap(String selected) {
        switch (selected) {
            case "rock land": {
                MapBuilderMenuController.createRockLand();
                break;
            }
            case "island": {
                MapBuilderMenuController.createIsland();
                break;
            }
            case "normal": {
                MapBuilderMenuController.createBaseLand();
                break;
            }
        }
        showMiniPrev();
    }

    private void showMiniPrev() {
        stage.clear();
        mapImage = new Image(GameMenuController.getMapPrev(getSelectedMap(), 4));
        mapImage.setPosition(graphics.getWidth() / 2f - mapImage.getWidth() / 2f, 0);
        stage.addActor(mapImage);
    }


}
