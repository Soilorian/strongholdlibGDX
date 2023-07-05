package org.example.model.ingame.castle.traps;

import com.badlogic.gdx.graphics.Texture;
import org.example.control.menucontrollers.GameMenuController;
import org.example.model.ingame.castle.Building;
import org.example.model.ingame.castle.Buildings;
import org.example.model.ingame.castle.Empire;
import org.example.model.ingame.humans.army.Troop;

import java.io.Serializable;
import java.util.ArrayList;

public class KillingPit extends Building implements Trap, Serializable {
    private final Empire empire;

    public KillingPit(Empire empire) {
        super(Buildings.KILLING_PIT);
        this.empire = empire;
    }

    @Override
    public Empire getEmpire() {
        return empire;
    }

    @Override
    public String getName() {
        return "killing pit";
    }

    @Override
    public Texture getTexture() {
        return null;
    }

    @Override
    public void doEffect(Troop troop) {
        troop.kill();
    }

    @Override
    public void update() {
        final ArrayList<Troop> troops = GameMenuController.lookAround(tileUnder.getX(), tileUnder.getY(), owner, 0);
        //noinspection SwitchStatementWithTooFewBranches
        switch (buildingStatus) {
            case NOT_ACTIVE : {
                if (!troops.isEmpty())
                    ((Trap) this).doEffect(troops.get(0));
                break;
            }
        }
    }
}
