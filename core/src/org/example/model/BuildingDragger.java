package org.example.model;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Clipboard;
import org.example.control.menucontrollers.GameMenuController;
import org.example.control.menucontrollers.inGameControllers.MapViewMenuController;
import org.example.model.ingame.map.enums.Direction;

public class BuildingDragger extends DragListener {
    Image clone;
    @Override
    public void dragStart(InputEvent event, float x, float y, int pointer) {
        clone = (Image) event.getTarget();
        event.getTarget().getStage().addActor(clone);
    }

    @Override
    public void drag(InputEvent event, float x, float y, int pointer) {
        clone.setPosition(x, y);
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
        GameMenuController.dropBuildingGameMenu(Math.floorDiv((int) (MapViewMenuController.getViewingX() - x),
                MapViewMenuController.zoom) , Math.floorDiv((int) (MapViewMenuController.getViewingY() - y),
                MapViewMenuController.zoom),  buildingReturnerAction.buildings, Direction.SOUTH);
    }
}
