package game.beatank.ui;

import game.beatank.enums.Layer;
import game.beatank.enums.State;
import static game.beatank.global.Functions.*;
import game.beatank.manager.GameObject;
import game.beatank.manager.Handler;
import static game.beatank.manager.Game.gameState;

/**
 *
 * @author Admin
 */
public class Infors extends GameObject{

    public Infors(float x, float y, Layer layer) {
        super(x, y, layer);
        states.add(State.About);
    }

    @Override
    public void create() {
        image = Handler.spr_infors;
        x_offset = image.getWidth();
    }

    @Override
    public void update() {
        if(mouse_up){
            mouse_down = false;
            gameState = State.Menu;
        }
    }
    
}
