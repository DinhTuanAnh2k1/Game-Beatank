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
public class GamePaused extends GameObject{

    private RestartButton rb;
    private MainmenuButton mb;
    
    public GamePaused(float x, float y, Layer layer) {
        super(x, y, layer);
        states.add(State.Game);
    }

    @Override
    public void create() {
        image = Handler.spr_gamepaused;
        rb = new RestartButton(grid_size*5.35f, grid_size*8.7f, layer);
        mb = new MainmenuButton(grid_size*10.05f, grid_size*8.7f, layer);
        rb.setParent(this); mb.setParent(this);
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
    }
    
}
