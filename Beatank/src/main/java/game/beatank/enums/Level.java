package game.beatank.enums;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Admin
 */
public enum Level {
    //r_x = rate of x in 100
    //id, target, r_spawn, r_shield, r_enemy2, r_speedup, r_enemy4
    lvl1(1, 10, 7, 33, 0, 0, 0),
    lvl2(2, 15, 6, 33, 20, 0, 0),
    lvl3(3, 20, 6, 100, 15, 0, 0),
    lvl4(4, 20, 5, 20, 0, 100, 0),
    lvl5(5, 25, 5, 80, 20, 20, 5),
    lvl6(6, 25, 4, 30, 20, 70, 30);
    
    public final int id, kill_target, r_shield, r_enemy2, r_speedup, r_enemy3;
    public final float r_spawn;
    public static Map<Integer, Level> levels = new HashMap<>();
    
    static {
        for (Level lvl: values()){
            levels.put(lvl.id, lvl);
        }
    }

    private Level(int id, int kill_target, float r_spawn, int r_shield, int r_enemy2, int r_speedup, int r_enemy3) {
        this.id = id;
        this.kill_target = kill_target;
        this.r_spawn = r_spawn;
        this.r_shield = r_shield;
        this.r_enemy2 = r_enemy2;
        this.r_speedup = r_speedup;
        this.r_enemy3 = r_enemy3;
    }
    
    public static Level getLevel(int i){
        return levels.get(i);
    }
}
