package game.beatank.entity;

import game.beatank.enums.Layer;
import game.beatank.enums.State;
import game.beatank.manager.GameObject;
import static game.beatank.manager.Handler.tiles;

/**
 *
 * @author Admin
 */
public class Tile extends GameObject {

    private int tileId;

    public Tile(float x, float y, Layer layer, int tileId) {
        super(x, y, layer);
        states.add(State.Game);
        this.tileId = tileId;
    }

    @Override
    public void create() {
        if (tileId != -1) {
            image = tiles[tileId];
        }
    }

    @Override
    public void update() {
    }

}
