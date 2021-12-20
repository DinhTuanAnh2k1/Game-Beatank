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
public class MainmenuButton extends Button {

    private GameObject parent;
    
    public MainmenuButton(float x, float y, Layer layer) {
        super(x, y, layer);
        states.add(State.Game);
    }
    
    public void setParent(GameObject parent) {
        this.parent = parent;
    }

    @Override
    public void create() {
        image = spr_mm_button;
        boxCollider.setSize(image.getWidth(), image.getHeight());
        setHighlightWithImage(spr_mm_button, spr_mm_button_hl);
    }

    @Override
    public void update() {
        if(isClicked()){
            LevelController.isBackToMainMenu = true;
            parent.destroySelf();
        }
    }
    
}
