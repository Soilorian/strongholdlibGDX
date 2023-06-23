package org.example.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Clipboard;
import org.example.control.menucontrollers.GameMenuController;
import org.example.control.menucontrollers.inGameControllers.MapViewMenuController;
import org.example.model.ingame.map.enums.Direction;
import org.example.view.menus.Menu;

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
        System.out.println(x + "   " + y + "  " +pointer);
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
        GameMenuController.dropBuildingGameMenu(Math.floorDiv((int) (x),
                        Gdx.graphics.getWidth() / zoom) + MapViewMenuController.getViewingX() - zoom/2,
                Math.floorDiv((int) (y),
                        Gdx.graphics.getHeight() / zoom) + MapViewMenuController.getViewingY() - zoom/2,
                buildingReturnerAction.buildings, Direction.SOUTH);
    }
}
