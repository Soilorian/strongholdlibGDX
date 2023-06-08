package org.example.model;

import com.badlogic.gdx.scenes.scene2d.Action;
import org.example.model.ingame.map.Tile;

public class GetTileAction extends Action {
    private final Tile tile;

    public GetTileAction(Tile tile) {
        this.tile = tile;
    }

    public Tile getTile() {
        return tile;
    }

    @Override
    public boolean act(float delta) {
        return true;
    }
}
