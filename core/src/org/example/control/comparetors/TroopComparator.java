package org.example.control.comparetors;

import org.example.model.ingame.humans.army.Troop;

import java.util.Comparator;

public class TroopComparator implements Comparator<Troop> {
    @Override
    public int compare(Troop o1, Troop o2) {
        return o1.getHitPoint() - o2.getHitPoint();
    }
}
