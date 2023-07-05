package org.example.model.ingame.castle.details;

import org.example.model.ingame.castle.Building;
import org.example.model.ingame.castle.Buildings;
import org.example.model.ingame.map.resourses.Resources;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public enum Storage implements Serializable {
    STOCKPILE("stockpile", Buildings.STOCKPILE, new Resources[]{Resources.WOOD, Resources.STONE,
            Resources.PITCH, Resources.IRON, Resources.FLOUR, Resources.HOPS, Resources.OIL, Resources.ALE}),
    GRANARY("granary", Buildings.GRANARY, new Resources[]{Resources.MEAT, Resources.BREAD, Resources.APPLE, Resources.CHEESE}),
    ARMOURY("armoury", Buildings.ARMOURY, new Resources[]{Resources.SPEAR, Resources.BOW,
            Resources.CROSSBOW, Resources.PIKE, Resources.CHESTPLATE, Resources.LEATHER_VEST, Resources.SWORD, Resources.MACE}),
    MAIN_CASTLE("main castle", Buildings.KEEP, new Resources[]{Resources.GOLD, Resources.HORSE});
    private final String name;
    private final Buildings storingPlace;
    private final ArrayList<Resources> storingResources = new ArrayList<>();

    Storage(String name, Buildings storingPlace, Resources[] resources) {
        this.name = name;
        this.storingPlace = storingPlace;
        this.storingResources.addAll(List.of(resources));
    }

    public static boolean IsStorage(Building building) {
        for (Storage value : values()) {
            if (value.getStoringPlace().equals(building.getBuilding()))
                return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public Buildings getStoringPlace() {
        return storingPlace;
    }

    public ArrayList<Resources> getStoringResources() {
        return storingResources;
    }
}
