package game.beatank.ui;

import game.beatank.enums.Layer;
import game.beatank.enums.State;
import game.beatank.manager.Handler;
import static game.beatank.global.Functions.*;

/**
 *
 * @author Admin
 */
public class PauseButton extends Button{
    
    private ResumeButton resumeButton;
    private GamePaused gamePaused;

    public void setResumeButton(ResumeButton resumeButton) {
        this.resumeButton = resumeButton;
    }
    
    public PauseButton(float x, float y, Layer layer) {
        super(x, y, layer);
        states.add(State.Game);
    }

    @Override
    public void create() {
        image = Handler.spr_pause;
        boxCollider.setSize(image.getWidth(), image.getHeight());
        x_offset = image.getWidth();
        setHighlightWithImage(Handler.spr_pause, Handler.spr_pause_hl);
        gamePaused = new GamePaused(0, 0, layer);
        gamePaused.setActive(false);
        resumeButton.setGamePaused(gamePaused);
    }

    @Override
    public void update() {
        if(isClicked()){
            gamePaused = new GamePaused(0, 0, layer);
            resumeButton.setGamePaused(gamePaused);
            mouse_down = false;
            pause = true;
        }
    }
    
}
