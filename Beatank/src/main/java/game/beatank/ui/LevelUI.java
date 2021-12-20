package game.beatank.ui;

import static game.beatank.controller.LevelController.level;
import game.beatank.enums.Layer;
import game.beatank.enums.State;
import static game.beatank.global.Functions.*;
import game.beatank.manager.Handler;
import java.awt.Color;

/**
 *
 * @author Admin
 */
public class LevelUI extends TextArea {

    public LevelUI(float x, float y, Layer layer) {
        super(x, y, layer);
        states.add(State.Game);
    }

    @Override
    public void create() {
        image = Handler.spr_level_ui;
        setFont(Handler.fnt_b_aguda.deriveFont(32f));
        setColor(new Color(255, 192, 0));
    }

    @Override
    public void update() {
        setText(String.format("%02d", level.id));
        set_text_offset(grid_size*3 - text_width/2, grid_size*0.75f);
    }
    
}
