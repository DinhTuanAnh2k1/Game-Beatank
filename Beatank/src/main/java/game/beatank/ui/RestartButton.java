package game.beatank.ui;

import game.beatank.controller.LevelController;
import game.beatank.enums.Layer;
import game.beatank.enums.State;
import game.beatank.manager.GameObject;
import static game.beatank.manager.Handler.*;

/**
 *
 * @author Admin
 */
public class RestartButton extends Button {

    private GameObject parent;
    
    public RestartButton(float x, float y, Layer layer) {
        super(x, y, layer);
        states.add(State.Game);
    }

    public void setParent(GameObject parent) {
        this.parent = parent;
    }

    @Override
    public void create() {
        image = spr_restart_button;
        boxCollider.setSize(image.getWidth(), image.getHeight());
        setHighlightWithImage(spr_restart_button, spr_restart_button_hl);
    }

    @Override
    public void update() {
        if(isClicked()){
            LevelController.isRestart = true;
            parent.destroySelf();
        }
    }
    
}
