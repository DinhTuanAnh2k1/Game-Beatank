package game.beatank.effect;

import game.beatank.enums.Layer;
import game.beatank.enums.State;
import static game.beatank.global.Functions.deltaTime;
import game.beatank.manager.GameObject;
import static game.beatank.manager.Handler.explosion;
import java.awt.image.BufferedImage;

/**
 *
 * @author Admin
 */
public class RealExplosion extends GameObject {

    private float delay = 0, t = 0;
    private BufferedImage[] images;
    private int image_id = 0, image_count = 0;
    
    public RealExplosion(float x, float y, Layer layer) {
        super(x, y, layer);
        states.add(State.Game);
    }

    public void setDelay(float delay) {
        this.delay = Math.max(deltaTime, delay);
    }
    
    public void setImages(BufferedImage[] images){
        this.images = images;
        image_count = images.length;
    }

    @Override
    public void create() {
        setDelay(0.1f);
        setImages(explosion);
        image = images[0];
        image_xscale = 1.3f;
        image_yscale = 1.3f;
    }

    @Override
    public void update() {
        t += deltaTime;
        if(t >= delay){
            t -= delay;
            image_id++;
            if(image_id == image_count){
                destroySelf();
            } else {
                image = images[image_id];
            }
        }
        set_centered_offset();
    }
    
}
