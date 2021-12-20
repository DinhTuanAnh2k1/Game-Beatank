package game.beatank.effect;

import game.beatank.enums.Layer;
import game.beatank.enums.State;
import static game.beatank.global.Functions.*;
import game.beatank.manager.GameObject;
import static game.beatank.manager.Handler.spr_warning;

/**
 *
 * @author Admin
 */
public class WarningSquare extends GameObject{

    public WarningSquare(float x, float y, Layer layer) {
        super(x, y, layer);
        states.add(State.Game);
        this.x = grid2Real((int) y) + grid_size / 2;
        this.y = grid2Real((int) x) + grid_size / 2;
    }
    
    @Override
    public void create() {
        image = addAlphaImage(spr_warning, -255);
        image_xscale = 7;
        image_yscale = 7;
        set_centered_offset();
    }
    
    int t = 0;
    @Override
    public void update() {
        t++;
        if(t <= 30){
            image_xscale -= 0.2f;
            image_yscale -= 0.2f;
            image = addAlphaImage(spr_warning, +255/60*t - 120);
        }
        if(t >= 60) {
            image_xscale += 0.1f;
            image_yscale += 0.1f;
            image = addAlphaImage(image, -255/40);
        }
        set_centered_offset();
        if(t == 80) destroySelf();
    }
}
