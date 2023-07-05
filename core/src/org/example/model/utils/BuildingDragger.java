package org.example.model.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import org.example.control.enums.GameMenuMessages;
import org.example.control.menucontrollers.GameMenuController;
import org.example.control.menucontrollers.inGameControllers.MapViewMenuController;
import org.example.model.ingame.map.enums.Direction;

import java.io.Serializable;

import static org.example.control.menucontrollers.inGameControllers.MapViewMenuController.zoom;

public class BuildingDragger extends DragListener implements Serializable {
    Image clone;
    @Override
    public void dragStart(InputEvent event, float x, float y, int pointer) {
        clone = new Image(((Image) event.getTarget()).getDrawable());
        event.getTarget().getStage().addActor(clone);
    }

    @Override
    public void drag(InputEvent event, float x, float y, int pointer) {
        clone.setPosition(Gdx.input.getX(), y);
    }

    @Override
    public void dragStop(InputEvent event, float x, float y, int pointer) {

    }
}
