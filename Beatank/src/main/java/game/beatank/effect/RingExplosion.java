package game.beatank.effect;

import game.beatank.enums.Layer;
import game.beatank.enums.State;
import static game.beatank.global.Functions.addAlphaImage;
import game.beatank.manager.GameObject;
import static game.beatank.manager.Handler.spr_ring;

/**
 *
 * @author Admin
 */
public class RingExplosion extends GameObject{
    
    public RingExplosion(float x, float y, Layer layer) {
        super(x, y, layer);
        states.add(State.Game);
    }

    @Override
    public void create() {
        circleCollider.setPosition(x, y);
        image = spr_ring;
        image_xscale = 8f/image.getWidth();
        image_yscale = 8f/image.getHeight();
        x_offset = image.getWidth()*image_xscale/2;
        y_offset = image.getHeight()*image_yscale/2;
    }
    
    int t = 0;
    @Override
    public void update() {
        image_xscale += 0.05f;
        image_yscale += 0.05f;
        x_offset = image.getWidth()*image_xscale/2;
        y_offset = image.getHeight()*image_yscale/2;
        image = addAlphaImage(image, -255/15);
        t++;
        if(t == 17) destroySelf();
    }
    
}
