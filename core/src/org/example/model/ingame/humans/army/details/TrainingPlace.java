package org.example.model.ingame.humans.army.details;

import org.example.model.ingame.castle.Buildings;
import org.example.model.ingame.humans.army.Troops;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public enum TrainingPlace implements Serializable {
    BARRACK(new Troops[]{Troops.SPEARMAN, Troops.ARCHER, Troops.PIKEMAN, Troops.MACEMAN,
            Troops.SWORDSMAN, Troops.KNIGHT, Troops.CROSSBOWMAN, Troops.SLAVE}, Buildings.BARRACK),
    MERCENARY_POST(new Troops[]{Troops.ASSASSIN, Troops.ARABIAN_SWORDSMAN, Troops.HORSE_ARCHER,
            Troops.ARCHER_BOW, Troops.FIRE_THROWER, Troops.SLINGER}, Buildings.MERCENARY_POST);
    private final ArrayList<Troops> trainedHere = new ArrayList<>();
    private final Buildings building;

    TrainingPlace(Troops[] troops, Buildings building) {
        this.building = building;
        trainedHere.addAll(List.of(troops));
    }

    public boolean doesntHaveUnit(String type) {
        for (Troops troop : trainedHere)
            if (troop.getName().equalsIgnoreCase(type))
                return false;
        return true;
    }

    public Buildings getBuilding() {
        return building;
    }
}
