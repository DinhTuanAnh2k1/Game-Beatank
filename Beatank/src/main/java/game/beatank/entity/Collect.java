package game.beatank.entity;

import static game.beatank.controller.EnemyController.collect_count;
import game.beatank.enums.Layer;
import game.beatank.enums.State;
import game.beatank.global.Functions;
import game.beatank.manager.GameObject;
import static game.beatank.manager.Handler.spr_c_shield;

/**
 *
 * @author Admin
 */
public class Collect extends GameObject {

    private Player player;

    public Collect(float x, float y, Layer layer, Player player) {
        super(x, y, layer);
        states.add(State.Game);
        this.player = player;
    }

    @Override
    public void destroySelf() {
        collect_count--;
        super.destroySelf();
    }

    @Override
    public void create() {
        image = spr_c_shield;
        boxCollider.setSize(image.getWidth(), image.getHeight());
        set_centered_offset();
    }

    @Override
    public void update() {
        image_angle++;
        if (player != null) {
            if (player.getShield() == null || player.getShield().getOwner() == -1) {
                if (Functions.boxCollision(boxCollider, player.boxCollider)) {
                    player.createShield();
                    destroySelf();
                }
            }
        }
    }

}
