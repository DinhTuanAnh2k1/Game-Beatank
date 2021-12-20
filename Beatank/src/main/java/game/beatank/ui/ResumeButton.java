package game.beatank.ui;


import game.beatank.enums.Layer;
import game.beatank.enums.State;
import game.beatank.manager.Handler;
import static game.beatank.global.Functions.*;


/**
 *
 * @author Admin
 */
public class ResumeButton extends Button {

    private GamePaused gamePaused;

    public void setGamePaused(GamePaused gamePaused) {
        this.gamePaused = gamePaused;
    }
    
    public ResumeButton(float x, float y, Layer layer) {
        super(x, y, layer);
        states.add(State.Game);
    }
    
    @Override
    public void create() {
        image = Handler.spr_resume;
        boxCollider.setSize(image.getWidth(), image.getHeight());
        x_offset = image.getWidth();
        setHighlightWithImage(Handler.spr_resume, Handler.spr_resume_hl);
    }

    @Override
    public void update() {
        if(isClicked()){
            gamePaused.destroySelf();
            mouse_down = false;
            pause = false;
        }
    }
}
