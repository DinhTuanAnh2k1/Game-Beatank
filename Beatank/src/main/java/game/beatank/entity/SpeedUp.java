package game.beatank.entity;

import game.beatank.enums.Layer;
import game.beatank.enums.State;
import game.beatank.global.Functions;
import game.beatank.manager.GameObject;
import game.beatank.manager.Handler;
import java.awt.image.BufferedImage;

/**
 *
 * @author Admin
 */
public class SpeedUp extends GameObject{

    private int state = 1;
    private BufferedImage[] imageState = new BufferedImage[2];
    
    public SpeedUp(float x, float y, Layer layer) {
        super(x, y, layer);
        states.add(State.Game);
    }
    
    public void changeState(int i){
        if(state != i){
            image = imageState[i];
            state = i;
        }
    }

    @Override
    public void create() {
        imageState[0] = Handler.spr_speed_up;
        imageState[1] = Functions.addAlphaImage(Handler.spr_speed_up, -60);
        image = imageState[1];
        set_centered_offset();
    }

    @Override
    public void update() {
    }
    
}
