package game.beatank.entity;

import game.beatank.controller.LevelController;
import game.beatank.effect.Explosion;
import game.beatank.effect.RealExplosion;
import game.beatank.effect.RingExplosion;
import game.beatank.enums.Dir;
import game.beatank.enums.Layer;
import game.beatank.enums.State;
import game.beatank.manager.GameObject;
import static game.beatank.global.Functions.*;
import game.beatank.manager.BoxCollider;
import static game.beatank.manager.Handler.*;
import game.beatank.ui.GameOver;

/**
 *
 * @author Admin
 */
public class Player extends GameObject {

    private float target_angle = 0;
    private final float reloadTime = 0.5f;
    private final float mainSpeed = 1.5f*grid_size/37f;
    private float tReload = 0;
    private final LevelController grid;
    private final GridPosition gridPosition;

    public Player(float x, float y, Layer layer, LevelController grid) {
        super(x, y, layer);
        states.add(State.Game);
        this.grid = grid;
        gridPosition = new GridPosition((int) x, (int) y);
        this.x = grid2Real((int) y) + grid_size / 2;
        this.y = grid2Real((int) x) + grid_size / 2;
    }

    @Override
    public void create() {
        image = pixelImage(spr_player, grid_size, (int) (grid_size / 4.0 * 3));
        x_offset = image.getHeight() / 2;
        y_offset = image.getHeight() / 2;
        boxCollider.setSize(image.getHeight(), image.getHeight());
        updateMatrix();
    }

    @Override
    public void update() {
        image_angle += Math.sin(Math.toRadians(target_angle - image_angle)) * 30;
        if (KEY[dir.getId()] != true) {
            boolean haveKeyPress = false;
            for (int i = 0; i < 4; i++) {
                if (KEY[i] == true) {
                    dir = toDir(i);
                    haveKeyPress = true;
                    break;
                }
            }
            if (!haveKeyPress) {
                dir = Dir.None;
            }
        }
        if (dir != Dir.None) {
            move(mainSpeed);
        } else {
            speed = 0;
        }
        shoot();
    }
    
    @Override
    public void destroySelf() {
        LevelController.isGameOver = true;
        new GameOver(0, 0, Layer.UI);
        new RealExplosion(x, y, Layer.Effect);
        new Explosion(x, y, Layer.Effect);
        new RingExplosion(x, y, Layer.Effect);
        grid.matrix[gridPosition.i][gridPosition.j] = 0;
        if(shield != null) shield.destroySelf();
        super.destroySelf();
    }
    
    private Shield shield = null;

    public Shield getShield() {
        return shield;
    }
    
    public void createShield(){
        shield = new Shield(x, y, layer);
        shield.setOwner(id);
    }
    
    public void moveWithShield(){
        if(shield != null){
            shield.setX(x);
            shield.setY(y);
        }
    }
    
    public void collisionWithEnemy() {
        for (GameObject obj : OBJECT) {
            if (obj.getLayer() == Layer.Enemy) {
                if (boxCollision(boxCollider, obj.boxCollider)) {
                    destroySelf();
                    obj.destroySelf();
                    break;
                }
            }
        }
    }
    
    public void shoot(){
        if(tReload < reloadTime) tReload += deltaTime;
        if(KEY[5] == true && tReload >= reloadTime && angle_different(image_angle, target_angle) < 1f){
            tReload -= reloadTime;
            Bullet bullet = new Bullet(x, y, Layer.Bullet);
            bullet.setAttributes(5*grid_size/37f, image_angle, grid);
            bullet.setOwner(id);
        }
    }

    @Override
    public void lateUpdate() {
        limit_position();
        collisionWithEnemy();
        onWallCollision();
        updateMatrix();
        moveWithShield();
    }
    
    public void updateMatrix(){
        int i = real2Grid(y), j = real2Grid(x);
        if(grid.matrix[i][j] == 0){
            grid.matrix[gridPosition.i][gridPosition.j] = 0;
            gridPosition.i = i; gridPosition.j = j;
            grid.matrix[gridPosition.i][gridPosition.j] = 2;
        }
    }

    public void move(float speed) {
        switch (dir.getId()) {
            case 0 -> {
                if (target_angle == 180) {
                    image_angle++;
                }
                target_angle = 0;
                dir = Dir.Right;
            }
            case 1 -> {
                if (target_angle == 270) {
                    image_angle++;
                }
                target_angle = 90;
                dir = Dir.Down;
            }
            case 2 -> {
                if (target_angle == 0) {
                    image_angle++;
                }
                target_angle = 180;
                dir = Dir.Left;
            }
            case 3 -> {
                if (target_angle == 90) {
                    image_angle++;
                }
                target_angle = 270;
                dir = Dir.Up;
            }
            default -> {
            }
        }
        if (angle_different(target_angle, image_angle) < 0.1f) {
            this.speed = speed;
        }
    }

    public void limit_position() {
        x = clamp(x, x_offset, grid_size * 20 - x_offset);
        y = clamp(y, y_offset, grid_size * 16 - y_offset);
    }

    public void onWallCollision() {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == j && i == 0) {
                    continue;
                }
                int ii = real2Grid(y) + i;
                int jj = real2Grid(x) + j;
                if (isValid(ii, jj)) {
                    if (grid.matrix[ii][jj] == 1) {
                        BoxCollider tmp = new BoxCollider(grid2Real(jj), grid2Real(ii), grid_size, grid_size);
                        while (boxCollision(boxCollider, tmp)) {
                            if(dir == Dir.Up){
                                y += 0.5f;
                            }
                            if(dir == Dir.Down){
                                y -= 0.5f;
                            }
                            if(dir == Dir.Left){
                                x += 0.5f;
                            }
                            if(dir == Dir.Right){
                                x -= 0.5f;
                            }
                            boxCollider.setPosition(x - x_offset, y - y_offset);
                        }
                    }
                }
            }
        }
    }
    public GridPosition getGridPosition() {
        return gridPosition;
    }
}
