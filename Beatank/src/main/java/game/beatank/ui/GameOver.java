package game.beatank.ui;

import game.beatank.enums.Layer;
import game.beatank.enums.State;
import static game.beatank.global.Functions.*;
import game.beatank.manager.GameObject;
import game.beatank.manager.Handler;

/**
 *
 * @author Admin
 */
public class GameOver extends GameObject {

    private RestartButton rb;
    private MainmenuButton mb;
    private float t = 0;

    public GameOver(float x, float y, Layer layer) {
        super(x, y, layer);
        states.add(State.Game);
    }

    @Override
    public void create() {

    }

    @Override
    public void destroySelf() {
        pause = false;
        rb.destroySelf();
        mb.destroySelf();
        super.destroySelf();
    }

    @Override
    public void update() {
        if (t < 60) {
            t++;
            if (t == 60) {
                image = Handler.spr_gameover;
                rb = new RestartButton(grid_size * 5.35f, grid_size * 8.7f, layer);
                mb = new MainmenuButton(grid_size * 10.05f, grid_size * 8.7f, layer);
                rb.setParent(this);
                mb.setParent(this);
            }
        }
        pause = true;
    }

}
