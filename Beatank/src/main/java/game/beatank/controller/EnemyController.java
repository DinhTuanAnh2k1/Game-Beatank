package game.beatank.controller;

import static game.beatank.controller.LevelController.level;
import game.beatank.effect.WarningSquare;
import game.beatank.entity.Enemy;
import game.beatank.entity.Player;
import game.beatank.enums.Layer;
import game.beatank.enums.State;
import static game.beatank.global.Functions.*;
import game.beatank.manager.GameObject;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Admin
 */
public class EnemyController extends GameObject {

    private final LevelController grid;
    private Player target;
    private float ts = 0, td = -1;
    public static int collect_count = 0;

    public EnemyController(float x, float y, Layer layer, LevelController grid) {
        super(x, y, layer);
        states.add(State.Game);
        this.grid = grid;
    }

    public Player getTarget() {
        return target;
    }

    public void setTarget(Player target) {
        this.target = target;
    }

    private LinkedList<GridPosition> eSpawnPos = new LinkedList<>();

    @Override
    public void create() {
        resetSpawnPosition();
    }

    public void resetSpawnPosition() {
        eSpawnPos.clear();
        for (int i = 1; i < 15; i++) {
            for (int j = 1; j < 19; j++) {
                if (grid.matrix[i][j] != 1) {
                    eSpawnPos.push(new GridPosition(i, j));
                }
            }
        }
    }

    private GridPosition nextSpawnPos;

    @Override
    public void update() {
        ts += deltaTime;
        if (ts >= level.r_spawn) {
            ts -= level.r_spawn;
            nextSpawnPos = eSpawnPos.get(randomRange(0, eSpawnPos.size() - 1));
            new WarningSquare(nextSpawnPos.i, nextSpawnPos.j, Layer.Effect);
            td = 60;
        }
        td = clamp(td - 1, -1, 60);
        if (td == 0) {
            new Enemy(nextSpawnPos.i, nextSpawnPos.j, layer, grid, this);
        }
    }

    private final boolean[][] vis = new boolean[16][20];

    private void reset() {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 20; j++) {
                vis[i][j] = false;
                pre[i][j] = '#';
            }
        }
    }

    private boolean isAvainable(int i, int j) {
        if (!isValid(i, j)) {
            return false;
        }
        if (grid.matrix[i][j] == 1) {
            return false;
        }
        return !vis[i][j];
    }

    private final char[][] pre = new char[16][20];

    public String findPathToPlayer(int ii, int jj) {
        reset();
        GridPosition target_pos = new GridPosition(-1, -1);
        Queue<GridPosition> q = new LinkedList<>();
        q.add(new GridPosition(ii, jj));
        vis[ii][jj] = true;
        while (!q.isEmpty()) {
            GridPosition u = q.poll();
            for (int i = 0; i < 4; i++) {
                GridPosition v = new GridPosition(u.i + dirI[i], u.j + dirJ[i]);
                if (isAvainable(v.i, v.j)) {
                    vis[v.i][v.j] = true;
                    pre[v.i][v.j] = toDir(i).toChar();
                    q.add(v);
                    if (grid.matrix[v.i][v.j] == 2) {
                        target_pos = v;
                        break;
                    }
                }
            }
            if (target_pos.i != -1 && target_pos.j != -1) {
                break;
            }
        }
        if (target_pos.i == -1 || target_pos.j == -1) {
            return "";
        }
        String res = "";
        while (pre[target_pos.i][target_pos.j] != '#') {
            char k = pre[target_pos.i][target_pos.j];
            res = k + res;
            target_pos.i -= dirI[toDir(k).getId()];
            target_pos.j -= dirJ[toDir(k).getId()];
        }
        return res;
    }

}
