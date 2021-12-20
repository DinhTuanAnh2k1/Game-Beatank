package game.beatank.ui;

import game.beatank.controller.LevelController;
import game.beatank.enums.Layer;
import game.beatank.enums.State;
import static game.beatank.manager.Game.gameState;
import game.beatank.manager.Handler;

/**
 *
 * @author Admin
 */
public class PlayButton extends Button {

    public PlayButton(float x, float y, Layer layer) {
        super(x, y, layer);
        states.add(State.Menu);
    }

    @Override
    public void create() {
        image = Handler.spr_play_button;
        x_offset = image.getWidth();
        boxCollider.setSize(image.getWidth(), image.getHeight());
    }

    @Override
    public void update() {
        if(isClicked()){
            LevelController.isRestart = true;
            gameState = State.Game;
        }
    }
    
}
