package org.example.model;

import com.badlogic.gdx.scenes.scene2d.Action;
import org.example.model.ingame.castle.Buildings;

public class BuildingReturnerAction extends Action {
    Buildings buildings;

    public BuildingReturnerAction(Buildings buildings) {
        this.buildings = buildings;
    }

    @Override
    public boolean act(float delta) {
        return false;
    }

    public Buildings getBuildings() {
        return buildings;
    }
}
