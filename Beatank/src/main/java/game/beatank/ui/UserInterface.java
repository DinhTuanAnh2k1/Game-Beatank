package game.beatank.ui;

import game.beatank.controller.LevelController;
import game.beatank.enums.Layer;
import game.beatank.enums.State;
import static game.beatank.global.Functions.*;
import game.beatank.manager.Game;
import game.beatank.manager.GameObject;
import game.beatank.manager.Handler;
import java.awt.Graphics2D;

/**
 *
 * @author Admin
 */
public class UserInterface extends GameObject {

    public UserInterface(float x, float y, Layer layer) {
        super(x, y, layer);
        states.add(State.Menu);
        states.add(State.About);
        states.add(State.Game);
    }

    private PauseButton pb;
    private ResumeButton rb;

    @Override
    public void create() {
        image = Handler.spr_menu_bg;
        pb = new PauseButton(grid_size * 20, 0, layer);
        rb = new ResumeButton(grid_size * 20, 0, layer);
        pb.setResumeButton(rb);
        rb.setActive(false);
        new LevelUI(0, 0, layer);
        new KillUI(grid_size * 4 - 10, 0, layer);
        new Infors(grid_size * 19f, grid_size * 10f, layer);
        new PlayButton(grid_size * 19f, grid_size * 10.8f, layer);
        new InforsButton(grid_size * 19f, grid_size * 12.2f, layer);
        new EndScreen(0, 0, layer);
    }

    @Override
    public void update() {
        if (LevelController.isGameOver) {
            pb.setActive(false);
            rb.setActive(false);
        } else {
            if (pause) {
                pb.setActive(false);
                rb.setActive(true);
            } else {
                pb.setActive(true);
                rb.setActive(false);
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        if (Game.gameState == State.Game) {
            return;
        }
        super.render(g);
    }

}
