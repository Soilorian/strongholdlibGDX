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

import static org.example.control.menucontrollers.inGameControllers.MapViewMenuController.zoom;

public class BuildingDragger extends DragListener {
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
        BuildingReturnerAction buildingReturnerAction = null;
        for (Action action : event.getTarget().getActions()) {
            if (action instanceof BuildingReturnerAction)
                buildingReturnerAction = ((BuildingReturnerAction) action);
        }
        if (buildingReturnerAction == null) {
            System.out.println("wait what?!?");
            return;
        }
        event.getStage().getActors().removeValue(clone, true);
        int placingX = Math.floorDiv((int) (Gdx.input.getX()),
                Gdx.graphics.getWidth() / zoom) + MapViewMenuController.getViewingX() - zoom / 2;
        int placingY = Math.floorDiv((int) (y),
                Gdx.graphics.getHeight() / zoom) + MapViewMenuController.getViewingY() - zoom / 2;
        System.out.println("ending x: " + Gdx.input.getX() + "ending y: " + y);
        System.out.println(placingX + "    " + placingY);
        GameMenuMessages message = GameMenuController.dropBuildingGameMenu(placingX, placingY,
                buildingReturnerAction.buildings, Direction.SOUTH);
    }
}
