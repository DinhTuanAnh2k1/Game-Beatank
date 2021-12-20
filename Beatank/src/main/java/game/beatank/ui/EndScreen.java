package game.beatank.ui;

import game.beatank.enums.Layer;
import game.beatank.enums.State;
import game.beatank.manager.Handler;

/**
 *
 * @author Admin
 */
public class EndScreen extends Button {

    public EndScreen(float x, float y, Layer layer) {
        super(x, y, layer);
        states.add(State.End);
    }

    @Override
    public void create() {
        image = Handler.spr_end;
        boxCollider.setSize(image.getWidth(), image.getHeight());
        //setHighlightWithImage(image, image);
    }

    @Override
    public void update() {
    }
    
}
