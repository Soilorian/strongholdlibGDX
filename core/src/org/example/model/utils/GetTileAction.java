package org.example.model.utils;

import com.badlogic.gdx.scenes.scene2d.Action;
import org.example.model.ingame.map.Tile;

import java.util.Objects;

public class GetTileAction extends Action {
    private final Tile tile;
    private static Action action = null;

    public GetTileAction(Tile tile) {
        this.tile = tile;
    }

    public static Action getInstance() {
        if (action == null)
            return action = new GetTileAction(null);
        return action;
    }

    public Tile getTile() {
        return tile;
    }

    @Override
    public boolean act(float delta) {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof GetTileAction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tile);
    }
}
