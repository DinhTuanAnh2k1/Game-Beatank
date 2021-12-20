package game.beatank.ui;

import game.beatank.enums.Layer;
import static game.beatank.global.Functions.*;
import game.beatank.manager.GameObject;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author Admin
 */
public abstract class Button extends GameObject {

    protected int hightlightOption = 0;
    protected boolean isHighlight = false;
    protected Color color = Color.orange;
    protected BufferedImage originImage, highlightImage;

    public Button(float x, float y, Layer layer) {
        super(x, y, layer);
    }
    
    public void setHighlightWithImage(BufferedImage origin, BufferedImage highlight){
        hightlightOption = 1;
        originImage = origin;
        highlightImage = highlight;
    }

    public boolean isBelowMousePosition() {
        if (inBoxCollider(mouse_x, mouse_y, boxCollider)) {
            if (!isHighlight) {
                isHighlight = true;
                if (hightlightOption == 0) {
                    image = colorImage(image, color);
                } else {
                    image = highlightImage;
                }
            }
            return true;
        }
        if (isHighlight) {
            isHighlight = false;
            if (hightlightOption == 0) {
                image = colorImage(image, Color.white);
            } else {
                image = originImage;
            }
        }
        return false;
    }

    public boolean isClicked() {
        return isBelowMousePosition() && mouse_down;
    }

    public boolean isReleased() {
        return isBelowMousePosition() && mouse_up;
    }
}
