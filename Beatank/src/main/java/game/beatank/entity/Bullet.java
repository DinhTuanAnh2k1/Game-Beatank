package game.beatank.entity;

import game.beatank.controller.LevelController;
import game.beatank.effect.Explosion;
import game.beatank.enums.Layer;
import game.beatank.enums.State;
import static game.beatank.global.Functions.*;
import game.beatank.manager.GameObject;
import game.beatank.manager.Handler;
import static game.beatank.manager.Handler.OBJECT;

/**
 *
 * @author Admin
 */
public class Bullet extends GameObject {
    
    private LevelController grid;
    
    public Bullet(float x, float y, Layer layer) {
        super(x, y, layer);
        states.add(State.Game);
    }

    public void setAttributes(float speed, float image_angle, LevelController grid) {
        this.speed = speed;
        this.image_angle = image_angle;
        this.grid = grid;
    }
    
    @Override
    public void destroySelf() {
        new Explosion(x, y, Layer.Effect);
        super.destroySelf();
    }

    @Override
    public void create() {
        image = Handler.spr_bullet;
        boxCollider.setSize(image.getWidth(), image.getHeight());
        set_centered_offset();
    }

    @Override
    public void update() {
        collisionWithObject();
        collisionWithWall();
        outOfScreen();
    }
    
    public void collisionWithObject(){
        for(GameObject obj: OBJECT){
            if(obj.getLayer() == Layer.Enemy && owner != obj.getId() && owner != obj.getOwner()){
                if(boxCollision(boxCollider, obj.boxCollider)){
                    obj.destroySelf();
                    destroySelf();
                    break;
                }
            }
            if(obj.getLayer() == Layer.Player && owner != obj.getId() && owner != obj.getOwner()){
                if(boxCollision(boxCollider, obj.boxCollider)){
                    obj.destroySelf();
                    destroySelf();
                    break;
                }
            }
        }
    }
    
    public void collisionWithWall(){
        int i = real2Grid(y);
        int j = real2Grid(x);
        if(isValid(i, j)){
            if(grid.matrix[i][j] == 1){
                destroySelf();
            }
        }
    }

    public void outOfScreen() {
        int w = image.getWidth();
        int h = image.getHeight();
        if (x != clamp(x, 0 - w, 20 * grid_size + w)
                || y != clamp(y, 0 - h, 16 * grid_size + h)) {
            destroySelf();
        }
    }

}
