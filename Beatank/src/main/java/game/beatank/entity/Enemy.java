package game.beatank.entity;

import game.beatank.controller.LevelController;
import game.beatank.controller.EnemyController;
import game.beatank.effect.Explosion;
import game.beatank.effect.RingExplosion;
import game.beatank.enums.Dir;
import game.beatank.enums.Layer;
import game.beatank.enums.MoveState;
import game.beatank.enums.State;
import game.beatank.manager.GameObject;
import static game.beatank.global.Functions.*;
import static game.beatank.manager.Handler.*;
import static game.beatank.controller.EnemyController.collect_count;
import static game.beatank.controller.LevelController.*;
import game.beatank.effect.RealExplosion;

/**
 *
 * @author Admin
 */
public class Enemy extends GameObject {

    private final LevelController grid;
    private final EnemyController ec;
    private float target_angle;
    private String path = "";
    private float range = 3;
    private final float reloadTime = 1.5f;
    private float tReload = 0;
    private int path_step = -1;
    private MoveState moveState = MoveState.Guard;
    private float mainSpeed = 1.15f * grid_size / 37f, kSpeed = 1f;
    private boolean doubleShoot = false;
    private GridPosition target_pos = new GridPosition(0, 0);
    private final GridPosition gridPosition;

    public Enemy(float x, float y, Layer layer, LevelController grid, EnemyController ec) {
        super(x, y, layer);
        states.add(State.Game);
        this.grid = grid;
        this.ec = ec;
        gridPosition = new GridPosition((int) x, (int) y);
        target_pos = new GridPosition((int) x, (int) y);
        this.x = grid2Real((int) y) + grid_size / 2;
        this.y = grid2Real((int) x) + grid_size / 2;
    }

    private Shield shield = null;
    private SpeedUp speedup = null;

    @Override
    public void create() {
        image = spr_enemy1;
        x_offset = image.getHeight() / 2;
        y_offset = image.getHeight() / 2;
        boxCollider.setSize(image.getHeight(), image.getHeight());
        dir = toDir(randomRange(0, 3));
        image_angle = dir.getId() * 90;
        target_angle = image_angle;
        if (chanceOf(level.r_shield, 100)) {
            shield = new Shield(x, y, layer);
            shield.setOwner(id);
        }
        if (chanceOf(level.r_enemy2, 100)) {
            range = 5;
            image = spr_enemy2;
            mainSpeed *= 1.3f;
        } else if (chanceOf(level.r_enemy3, 100 - level.r_enemy2)){
            range = 6;
            image = spr_enemy3;
            mainSpeed *= 1.1f;
            doubleShoot = true;
        }
        if (chanceOf(level.r_speedup, 100)){
            speedup = new SpeedUp(x, y, Layer.Effect);
            kSpeed = 1.25f;
        }
    }

    @Override
    public void update() {
        moveAfterTurned();
        controlState();
        if (moveState == MoveState.Trace) {
            moveFollowPath();
        }
        if (moveState == MoveState.Guard) {
            moveBacknForth();
        }
        shoot();
    }

    public void moveWithPowerUp() {
        if (shield != null) {
            shield.setX(x);
            shield.setY(y);
        }
        if (speedup != null){
            speedup.setX(x);
            speedup.setY(y);
            speedup.setImage_angle(image_angle);
            if(moveState == MoveState.Guard){
                speedup.changeState(1);
            } else {
                speedup.changeState(0);
            }
        }
    }

    @Override
    public void lateUpdate() {
        updateMatrix();
        moveWithPowerUp();
    }

    @Override
    public void destroySelf() {
        new RealExplosion(x, y, Layer.Effect);
        new Explosion(x, y, Layer.Effect);
        new RingExplosion(x, y, Layer.Effect);
        grid.matrix[gridPosition.i][gridPosition.j] = 0;
        kill++;
        if (chanceOf(3, 10)) {
            if (collect_count < 2) {
                collect_count++;
                new Collect(x, y, Layer.Tile, ec.getTarget());
            }
        }
        if (shield != null) {
            shield.destroySelf();
        }
        if (speedup != null){
            speedup.destroySelf();
        }
        super.destroySelf();
    }

    public boolean findPlayer() {
        int i = gridPosition.i, j = gridPosition.j;
        while (i > 0) {
            if (dir == Dir.Down) {
                break;
            }
            i--;
            if (grid.matrix[i][j] == 1) {
                break;
            }
            if (grid.matrix[i][j] == 2) {
                return true;
            }
        }
        i = gridPosition.i;
        j = gridPosition.j;
        while (i < 16 - 1) {
            if (dir == Dir.Up) {
                break;
            }
            i++;
            if (grid.matrix[i][j] == 1) {
                break;
            }
            if (grid.matrix[i][j] == 2) {
                return true;
            }
        }
        i = gridPosition.i;
        j = gridPosition.j;
        while (j > 0) {
            if (dir == Dir.Right) {
                break;
            }
            j--;
            if (grid.matrix[i][j] == 1) {
                break;
            }
            if (grid.matrix[i][j] == 2) {
                return true;
            }
        }
        i = gridPosition.i;
        j = gridPosition.j;
        while (j < 20 - 1) {
            if (dir == Dir.Left) {
                break;
            }
            j++;
            if (grid.matrix[i][j] == 1) {
                break;
            }
            if (grid.matrix[i][j] == 2) {
                return true;
            }
        }
        return false;
    }

    public void moveAfterTurned() {
        grid.matrix[gridPosition.i][gridPosition.j] = 0;
        image_angle += Math.sin(Math.toRadians(target_angle - image_angle)) * 20;
        if (angle_different(image_angle, target_angle) < 0.1f) {
            speed = mainSpeed;
            if(moveState == MoveState.Trace) speed *= kSpeed;
        } else {
            speed = 0;
        }
    }

    public void updateMatrix() {
        int i = real2Grid(y), j = real2Grid(x);
        if (grid.matrix[i][j] == 0) {
            grid.matrix[gridPosition.i][gridPosition.j] = 0;
            gridPosition.i = i;
            gridPosition.j = j;
            grid.matrix[gridPosition.i][gridPosition.j] = 3;
        }
    }

    public void shoot() {
        if (tReload < reloadTime) {
            tReload += deltaTime;
        }
        if (tReload >= reloadTime && moveState == MoveState.Trace) {
            float xx = ec.getTarget().getX();
            float yy = ec.getTarget().getY();
            float angle = point_direction(x, y, xx, yy);
            if (angle_different(image_angle, angle) <= 5 && angle_different(image_angle, target_angle) < 1f) {
                tReload -= reloadTime;
                Bullet bullet = new Bullet(x, y, Layer.Bullet);
                bullet.setAttributes((float) (3.5f * grid_size / 37f * Math.sqrt(kSpeed)), image_angle, grid);
                bullet.setOwner(id);
                if(doubleShoot){
                    Bullet bullet2 = new Bullet(x + lengthdirX(13, image_angle), y + lengthdirY(13, image_angle), Layer.Bullet);
                    bullet2.setAttributes((float) (3.5f * grid_size / 37f * Math.sqrt(kSpeed)), image_angle, grid);
                    bullet2.setOwner(id);
                }
            }
        }
    }

    public void controlState() {
        float xx = ec.getTarget().getX();
        float yy = ec.getTarget().getY();
        if (moveState == MoveState.Guard) {
            target_pos = new GridPosition(real2Grid(y), real2Grid(x));
        }
        if (point_distance(x, y, xx, yy) <= grid_size * range * kSpeed || findPlayer()) {
            if (path_step == path.length() || path.equals("")) {
                if (nearGridPosition(target_pos.i, target_pos.j, mainSpeed)) {
                    moveState = MoveState.Trace;
                    path_step = -1;
                    path = ec.findPathToPlayer(real2Grid(y), real2Grid(x));
                }
            }
        } else {
            if (path_step == path.length() && moveState != MoveState.Guard) {
                moveState = MoveState.Guard;
            }
        }
    }

    public boolean nearGridPosition(int i, int j, float amo) {
        float xx = grid2Real(j) + grid_size / 2;
        float yy = grid2Real(i) + grid_size / 2;
        return Math.abs(xx - x) <= amo && Math.abs(yy - y) <= amo;
    }

    public void moveFollowPath() {
        if (grid.matrix[target_pos.i][target_pos.j] == 1 || grid.matrix[target_pos.i][target_pos.j] == 3) {
            moveState = MoveState.Guard;
            path_step = path.length();
        }
        if (nearGridPosition(target_pos.i, target_pos.j, mainSpeed*kSpeed)) {
            x = grid2Real(target_pos.j) + grid_size / 2;
            y = grid2Real(target_pos.i) + grid_size / 2;
            path_step = Math.min(path_step + 1, path.length());
            if (path_step == path.length()) {
                speed = 0;
                return;
            }
            char k = path.charAt(path_step);
            target_pos.i += dirI[toDir(k).getId()];
            target_pos.j += dirJ[toDir(k).getId()];
            if (dir == toDir(k).getOppositeDir()) {
                image_angle++;
            }
            dir = toDir(k);
            target_angle = char2Angle(k);
        }
    }

    public boolean isAvainable(int i, int j) {
        if (!isValid(i, j)) {
            return false;
        }
        return grid.matrix[i][j] == 0;
    }

    public void moveBacknForth() {
        int i = real2Grid(y - dirI[dir.getId()] * grid_size / 2);
        int j = real2Grid(x - dirJ[dir.getId()] * grid_size / 2);
        if (null != dir) {
            switch (dir) {
                case Up -> {
                    if (!isAvainable(i - 1, j)) {
                        turnToAnotherWay();
                    }
                }
                case Down -> {
                    if (!isAvainable(i + 1, j)) {
                        turnToAnotherWay();
                    }
                }
                case Left -> {
                    if (!isAvainable(i, j - 1)) {
                        turnToAnotherWay();
                    }
                }
                case Right -> {
                    if (!isAvainable(i, j + 1)) {
                        turnToAnotherWay();
                    }
                }
                default -> {
                }
            }
        }
    }

    public void turnToAnotherWay() {

        int choice = randomRange(0, 2);
        switch (choice) {
            case 0 ->
                dir = dir.getRightDir();
            case 1 ->
                dir = dir.getLeftDir();
            case 2 -> {
                image_angle++;
                dir = dir.getOppositeDir();
            }
            default -> {
            }
        }
        target_angle = dir.getId() * 90;
    }

}
