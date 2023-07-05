package org.example.model.ingame.castle.traps;

import com.badlogic.gdx.graphics.Texture;
import org.example.model.ingame.castle.Empire;
import org.example.model.ingame.humans.army.Troop;

import java.io.Serializable;

public interface Trap extends Serializable {
    void doEffect(Troop troop);

    Empire getEmpire();

    String getName();

    Texture getTexture();
}
