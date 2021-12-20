package game.beatank.manager;

import game.beatank.enums.Dir;
import static game.beatank.global.Functions.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author Admin
 */
public class KeyInput extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP -> {
                KEY[Dir.Up.getId()] = true;
            }
            case KeyEvent.VK_DOWN -> {
                KEY[Dir.Down.getId()] = true;
            }
            case KeyEvent.VK_LEFT -> {
                KEY[Dir.Left.getId()] = true;
            }
            case KeyEvent.VK_RIGHT -> {
                KEY[Dir.Right.getId()] = true;
            }
            case KeyEvent.VK_SPACE -> {
                KEY[5] = true;
            }
            default -> {
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP -> {
                KEY[Dir.Up.getId()] = false;
            }
            case KeyEvent.VK_DOWN -> {
                KEY[Dir.Down.getId()] = false;
            }
            case KeyEvent.VK_LEFT -> {
                KEY[Dir.Left.getId()] = false;
            }
            case KeyEvent.VK_RIGHT -> {
                KEY[Dir.Right.getId()] = false;
            }
            case KeyEvent.VK_SPACE -> {
                KEY[5] = false;
            }
            default -> {
            }
        }
    }
}
