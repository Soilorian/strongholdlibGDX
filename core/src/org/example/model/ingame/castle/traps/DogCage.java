package org.example.model.ingame.castle.traps;

import com.badlogic.gdx.graphics.Texture;
import org.example.control.menucontrollers.GameMenuController;
import org.example.model.ingame.castle.Building;
import org.example.model.ingame.castle.Buildings;
import org.example.model.ingame.castle.Empire;
import org.example.model.ingame.humans.army.Troop;
import org.example.model.ingame.humans.army.Troops;

import java.io.Serializable;
import java.util.ArrayList;

public class DogCage extends Building implements Trap, Serializable {
    private final Empire empire;

    public DogCage(Empire empire) {
        super(Buildings.CAGED_WAR_DOGS);
        this.empire = empire;
    }

    @Override
    public void doEffect(Troop troop) {
        for (int i = 0; i < 4; i++)
            troop.getCurrentTile().getTroops().add(new Troop(Troops.DOG, empire, troop.getCurrentTile()));
    }

    @Override
    public Empire getEmpire() {
        return empire;
    }

    @Override
    public String getName() {
        return "dog cage";
    }

    @Override
    public Texture getTexture() {
        return null;
    }

    @Override
    public void update() {
        final ArrayList<Troop> troops = GameMenuController.lookAround(tileUnder.getX(), tileUnder.getY(), owner, 5);
        //noinspection SwitchStatementWithTooFewBranches
        switch (buildingStatus) {
            case NOT_ACTIVE : {
                if (!troops.isEmpty())
                    ((Trap) this).doEffect(troops.get(0));
            }
        }
    }
}
