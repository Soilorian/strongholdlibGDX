package org.example.model.ingame.humans.specialworkers;

import org.example.control.enums.GameMenuMessages;
import org.example.model.DataBase;
import org.example.model.exceptions.CoordinatesOutOfMap;
import org.example.model.exceptions.NotInStoragesException;
import org.example.model.ingame.castle.Empire;
import org.example.model.ingame.humans.Peasant;
import org.example.model.ingame.humans.army.Troop;
import org.example.model.ingame.humans.army.details.Damage;
import org.example.model.ingame.map.Tile;
import org.example.model.ingame.map.resourses.Resource;
import org.example.model.ingame.map.resourses.Resources;

public class Engineer extends Peasant {
    private static final String name = "engineer";
    private boolean oil = false;

    public Engineer(Empire empire, Tile tile) {
        super(empire, tile);
    }

    public static String getName() {
        return name;
    }

    public static int getPrice() {
        return 80;
    }

    public void pourOil(Tile tile) {
        for (Troop troop : tile.getTroops()) troop.takeDamage(Damage.HIGH.getDamage());
    }

    @Override
    public GameMenuMessages update() throws CoordinatesOutOfMap, NotInStoragesException {
        if (oil)
            return GameMenuMessages.READY_TO_ATTACK;
        if (getDestinationBuilding() == null)
            setDestinationBuilding(DataBase.getCurrentEmpire().whereToGet(new Resource(Resources.OIL, 1), getCurrentTile().getX(), getCurrentTile().getY()));
        if (getDestinationBuilding().atBuilding(getCurrentTile().getX(), getCurrentTile().getY())) {
            oil = true;
            return GameMenuMessages.HAVE_OIL;
        }
        // TODO: 5/11/2023 move function needed
        return GameMenuMessages.ON_THE_WAY;
    }
}