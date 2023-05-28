package org.example.model.ingame.castle.traps;

import org.example.model.ingame.castle.Empire;
import org.example.model.ingame.humans.army.Troop;

public interface Trap {
    void doEffect(Troop troop);

    Empire getEmpire();

    String getName();
}
