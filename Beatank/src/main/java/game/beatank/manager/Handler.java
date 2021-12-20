package game.beatank.manager;

import static game.beatank.global.Functions.*;
import game.beatank.enums.Layer;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class Handler {

    public static LinkedList<GameObject> OBJECT = new LinkedList<GameObject>();
    public static BufferedImage spr_player, spr_enemy1, spr_enemy2, spr_enemy3, spr_grid, spr_bullet, spr_background, spr_menu_bg,
            spr_play_button, spr_infors_button, spr_infors, spr_explosion, spr_ring, spr_warning, spr_login, spr_shield, 
            spr_speed_up, spr_c_shield, spr_level_ui, spr_kill_ui, spr_gameover, spr_gamepaused, spr_restart_button, 
            spr_mm_button, spr_restart_button_hl, spr_mm_button_hl, spr_pause, spr_pause_hl, spr_resume, spr_resume_hl, spr_end;
    public static Font fnt_r_aguda, fnt_b_aguda;
    public static BufferedImage[] tiles = new BufferedImage[30];
    public static BufferedImage[] explosion = new BufferedImage[5];

    public void loadImages() {
        BufferedImageLoader loader = new BufferedImageLoader();
        try {
            for (int i = 0; i < 5; i++) {
                explosion[i] = loader.loadImage("/explosion/" + i + ".png");
            }
            for (int i = 0; i < 30; i++) {
                tiles[i] = pixelImage(loader.loadImage("/tiles/" + i + ".png"), grid_size, grid_size);
            }
            spr_player = loader.loadImage("/spr_player.png");
            spr_background = pixelImage(loader.loadImage("/spr_background.png"), grid_size * 20, grid_size * 16);
            spr_bullet = pixelImage(loader.loadImage("/spr_bullet.png"), grid_size / 5, grid_size / 5);
            spr_enemy1 = pixelImage(loader.loadImage("/spr_enemy1.png"), grid_size, grid_size / 4f * 3);
            spr_enemy2 = pixelImage(loader.loadImage("/spr_enemy2.png"), grid_size, grid_size / 4f * 3);
            spr_enemy3 = pixelImage(loader.loadImage("/spr_enemy3.png"), grid_size, grid_size / 4f * 3);
            spr_menu_bg = pixelImage(loader.loadImage("/spr_menu_background.png"), grid_size * 20, grid_size * 16 + 1);
            spr_play_button = pixelImage(loader.loadImage("/spr_play_button.png"), grid_size*3.7f, grid_size*1.5f);
            spr_infors_button = pixelImage(loader.loadImage("/spr_infors_button.png"), grid_size*4.7f, grid_size*1.5f);
            spr_explosion = loader.loadImage("/spr_explosion.png");
            spr_ring = loader.loadImage("/spr_ring.png");
            spr_warning = pixelImage(loader.loadImage("/spr_warning_square.png"), grid_size, grid_size);
            spr_login = pixelImage(loader.loadImage("/spr_login_square.png"), grid_size, grid_size);
            spr_shield = pixelImage(loader.loadImage("/spr_shield.png"), grid_size, grid_size);
            spr_speed_up = pixelImage(loader.loadImage("/spr_speed_up.png"), grid_size*5f/6, grid_size*5f/6);
            spr_c_shield = pixelImage(loader.loadImage("/spr_c_shield.png"), grid_size*3f/4, grid_size*3f/4);
            spr_infors = pixelImage(loader.loadImage("/spr_infors.png"), grid_size*8f, grid_size*5f);
            spr_level_ui = pixelImage(loader.loadImage("/spr_level_ui.png"), grid_size*4f, grid_size*1f);
            spr_kill_ui = pixelImage(loader.loadImage("/spr_kill_ui.png"), grid_size*5f, grid_size*1f);
            spr_gameover = pixelImage(loader.loadImage("/spr_gameover.png"), grid_size*20f + 1, grid_size*16f);
            spr_gamepaused = pixelImage(loader.loadImage("/spr_gamepaused.png"), grid_size*20f + 1, grid_size*16f);
            spr_restart_button = pixelImage(loader.loadImage("/spr_restart.png"), grid_size*4.6f, grid_size*1.2f);
            spr_mm_button = pixelImage(loader.loadImage("/spr_mainmenu.png"), grid_size*4.6f, grid_size*1.2f);
            spr_restart_button_hl = pixelImage(loader.loadImage("/spr_restart_hl.png"), grid_size*4.6f, grid_size*1.2f);
            spr_mm_button_hl = pixelImage(loader.loadImage("/spr_mainmenu_hl.png"), grid_size*4.6f, grid_size*1.2f);
            spr_pause = pixelImage(loader.loadImage("/spr_pause.png"), grid_size*3.8f, grid_size);
            spr_pause_hl = pixelImage(loader.loadImage("/spr_pause_hl.png"), grid_size*3.8f, grid_size);
            spr_resume = pixelImage(loader.loadImage("/spr_resume.png"), grid_size*3.8f, grid_size);
            spr_resume_hl = pixelImage(loader.loadImage("/spr_resume_hl.png"), grid_size*3.8f, grid_size);
            spr_end = pixelImage(loader.loadImage("/spr_end.png"), grid_size * 20 + 1, grid_size * 16);
        } catch (IOException e) {
        }
        loadFonts();
    }
    
    private void loadFonts(){
        try {
            fnt_r_aguda = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/java/game/beatank/font/SVN-Aguda Regular.ttf"));
            fnt_b_aguda = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/java/game/beatank/font/SVN-Aguda Black.ttf"));
        } catch (FontFormatException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void tick() {
        for (int i = 0; i < OBJECT.size(); i++) {
            OBJECT.get(i).tick();
        }
        mouse_up = false;
        mouse_down = false;
    }

    private final LinkedList<Layer> sortingLayer = new LinkedList<>();

    public void initSortingLayer() {
        sortingLayer.add(Layer.Background);
        sortingLayer.add(Layer.Tile);
        sortingLayer.add(Layer.Bullet);
        sortingLayer.add(Layer.Player);
        sortingLayer.add(Layer.Enemy);
        sortingLayer.add(Layer.Effect);
        sortingLayer.add(Layer.UI);
    }

    public void render(Graphics2D g) {
        for (Layer layer : sortingLayer) {
            for (GameObject tmp : OBJECT) {
                if (tmp.getLayer() == layer) {
                    tmp.render(g);
                }
            }
        }
    }

    public void addObject(GameObject object) {
        OBJECT.add(object);
    }

    public void removeObject(GameObject object) {
        OBJECT.remove(object);
    }

}
