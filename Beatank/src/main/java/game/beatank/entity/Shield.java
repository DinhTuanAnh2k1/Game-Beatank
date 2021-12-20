package game.beatank.entity;

import game.beatank.enums.Layer;
import game.beatank.enums.State;
import game.beatank.global.Functions;
import game.beatank.manager.GameObject;
import game.beatank.manager.Handler;
import static game.beatank.manager.Handler.OBJECT;

/**
 *
 * @author Admin
 */
public class Shield extends GameObject {

    public Shield(float x, float y, Layer layer) {
        super(x, y, layer);
        states.add(State.Game);
    }

    @Override
    public void create() {
        image = Handler.spr_shield;
        boxCollider.setSize(image.getWidth()*image_xscale, image.getHeight()*image_yscale);
        image_xscale = image_yscale = 0;
    }

    @Override
    public void update() {
        if(image_xscale < 1){
            image_xscale += 1f/30;
            image_yscale += 1f/30;
            boxCollider.setSize(image.getWidth()*image_xscale, image.getHeight()*image_yscale);
            set_centered_offset();
        }
        for (GameObject obj : OBJECT) {
            if (obj.getLayer() == Layer.Bullet) {
                if (obj.getOwner() != owner) {
                    if (Functions.boxCollision(boxCollider, obj.boxCollider)) {
                        obj.destroySelf();
                        destroySelf();
                        owner = -1;
                        break;
                    }
                }
            }
            if (obj.getLayer() == Layer.Enemy || obj.getLayer() == Layer.Player) {
                if (obj.getId() != owner && obj.getId() != id && layer != obj.getLayer()) {
                    if (Functions.boxCollision(boxCollider, obj.boxCollider)) {
                        obj.destroySelf();
                        destroySelf();
                        owner = -1;
                        break;
                    }
                }
            }
        }
    }

}
