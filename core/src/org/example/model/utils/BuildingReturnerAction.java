package org.example.model.utils;

import com.badlogic.gdx.scenes.scene2d.Action;
import org.example.model.ingame.castle.Buildings;

import java.io.Serializable;

public class BuildingReturnerAction extends Action implements Serializable {
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
