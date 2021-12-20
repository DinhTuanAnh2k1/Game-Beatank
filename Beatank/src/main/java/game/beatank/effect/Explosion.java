package game.beatank.effect;

import game.beatank.enums.Layer;
import game.beatank.enums.State;
import static game.beatank.global.Functions.addAlphaImage;
import static game.beatank.global.Functions.pixelImage;
import game.beatank.manager.GameObject;
import static game.beatank.manager.Handler.spr_explosion;

/**
 *
 * @author Admin
 */
public class Explosion extends GameObject{
    
    public Explosion(float x, float y, Layer layer) {
        super(x, y, layer);
        states.add(State.Game);
    }

    @Override
    public void create() {
        image = pixelImage(spr_explosion, 10, 10);
        set_centered_offset();
    }
    
    int t = 0;
    @Override
    public void update() {
        image_xscale += 0.6f;
        image_yscale += 0.6f;
        x_offset = image.getWidth()*image_xscale/2;
        y_offset = image.getHeight()*image_yscale/2;
        image = addAlphaImage(image, -255/15);
        t++;
        if(t == 15) destroySelf();
    }
    
}
