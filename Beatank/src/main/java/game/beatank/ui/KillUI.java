package game.beatank.ui;

import static game.beatank.controller.LevelController.kill;
import static game.beatank.controller.LevelController.level;
import game.beatank.enums.Layer;
import game.beatank.enums.State;
import static game.beatank.global.Functions.grid_size;
import game.beatank.manager.Handler;
import java.awt.Color;

/**
 *
 * @author Admin
 */
public class KillUI extends TextArea {

    public KillUI(float x, float y, Layer layer) {
        super(x, y, layer);
        states.add(State.Game);
    }

    @Override
    public void create() {
        image = Handler.spr_kill_ui;
        setFont(Handler.fnt_b_aguda.deriveFont(32f));
        setColor(new Color(255, 192, 0));
    }

    @Override
    public void update() {
        setText(String.format("%02d", kill) + "/" + String.format("%02d", level.kill_target));
        set_text_offset(grid_size*3.5f - text_width/2, grid_size*0.75f);
    }
    
}
