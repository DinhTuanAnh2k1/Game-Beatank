package game.beatank.controller;

import game.beatank.effect.LoginSquare;
import game.beatank.entity.Player;
import game.beatank.entity.Tile;
import game.beatank.enums.Layer;
import game.beatank.enums.State;
import static game.beatank.global.Functions.*;
import game.beatank.manager.GameObject;
import game.beatank.manager.Handler;
import static game.beatank.manager.Handler.OBJECT;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import game.beatank.enums.Level;
import java.util.logging.Logger;
import static game.beatank.manager.Game.gameState;

/**
 *
 * @author Admin
 */
public class LevelController extends GameObject {

    public static int kill = 0;
    public static Level level = Level.lvl2;
    public static boolean isRestart = false, isBackToMainMenu = false, isGameOver = false;

    private Player player = null;
    private EnemyController ec = null;
    private GridPosition playerStartPos, playerTargetPos;

    public LevelController(float x, float y, Layer layer) {
        super(x, y, layer);
        states.add(State.Game);
    }

    public int[][] matrix = new int[16][20];

    private Scanner jin;

    public void loadMap(int id) throws FileNotFoundException {
        ArrayList<ArrayList<Integer>> data = new ArrayList<>();
        File f = new File("src/main/java/game/beatank/data/level" + id + ".txt");
        jin = new Scanner(f);
        while (jin.hasNext()) {
            String s = jin.nextLine();
            String[] line = s.split("\\,\\s*");
            ArrayList<Integer> tmp = new ArrayList<>();
            for (String word : line) {
                tmp.add(Integer.parseInt(word));
            }
            data.add(tmp);
        }
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 20; j++) {
                new Tile(grid2Real(j), grid2Real(i), Layer.Tile, data.get(i).get(j));
                for (int oid : obstacle_id) {
                    if (data.get(i).get(j) == oid) {
                        matrix[i][j] = 1;
                        break;
                    }
                    matrix[i][j] = 0;
                }
                if (data.get(i).get(j) == 12 || data.get(i).get(j) == 13) {
                    playerStartPos = new GridPosition(i, j);
                }
                if (data.get(i).get(j) == 10 || data.get(i).get(j) == 11) {
                    playerTargetPos = new GridPosition(i, j);
                }
            }
        }
    }

    @Override
    public void create() {
        image = Handler.spr_background;
    }

    @Override
    public void update() {
        //Back to main menu
        if (isBackToMainMenu) {
            kill = 0;
            isGameOver = false;
            isBackToMainMenu = false;
            gameState = State.Menu;
            clearGrid();
        }

        //Restart
        if (isRestart) {
            kill = 0;
            isGameOver = false;
            isRestart = false;
            try {
                loadLevel();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(LevelController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        }

        //Next level
        if (player != null) {
            if (player.getGridPosition().i == playerTargetPos.i
                    && player.getGridPosition().j == playerTargetPos.j
                    && kill >= level.kill_target) {
                kill = 0;
                isGameOver = false;
                if (level.id + 1 > 6) {
                    gameState = State.End;
                } else {
                    level = game.beatank.enums.Level.getLevel(level.id + 1);
                    try {
                        loadLevel();
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(LevelController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    public void loadLevel() throws FileNotFoundException {
        clearGrid();
        loadMap(level.id);
        ec = new EnemyController(0, 0, Layer.Enemy, this);
        new LoginSquare(playerStartPos.i, playerStartPos.j, Layer.Effect);
        player = new Player(playerStartPos.i, playerStartPos.j, Layer.Player, this);
        ec.setTarget(player);
    }

    public void clearGrid() {
        int i = OBJECT.size() - 1;
        while (i >= 0) {
            Layer tmp = OBJECT.get(i).getLayer();
            if (tmp != Layer.Background && tmp != Layer.UI) {
                OBJECT.remove(OBJECT.get(i));
            }
            i--;
        }
    }

}
