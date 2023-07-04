package org.example.model.utils;

import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import org.example.model.ingame.map.Tile;

public class MyTextTooltip extends TextTooltip {
    private final Tile tile;
    public MyTextTooltip(String s, Skin skin, Tile tile) {
        super(s, skin);
        this.tile = tile;
    }

    @Override
    public Container<Label> getContainer() {
        return super.getContainer();
    }
}
