package game.beatank.manager;

import game.beatank.controller.LevelController;
import game.beatank.enums.Layer;
import game.beatank.enums.State;
import static game.beatank.manager.Handler.OBJECT;
import static game.beatank.global.Functions.*;
import game.beatank.ui.UserInterface;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

/**
 *
 * @author ADMIN
 */
public class Game extends Canvas implements Runnable{
    
    public static final int WIDTH = grid_size*20 + 15, HEIGHT = grid_size*16 + 40;
    public static final String TITLE = "BEATANK";
    public static State gameState = State.Menu;
    
    private Thread thread;
    private boolean running = false;
    
    private Handler handler;
    
    public static void clearObjects(){
        OBJECT.clear();
    }
    
    public void initObjects(){
        new UserInterface(0, 0, Layer.UI);
        new LevelController(0, 0, Layer.Background);
    }
    
    public Game(){
        handler = new Handler();
        new Window(WIDTH, HEIGHT, TITLE, this);
    }
    
    public synchronized void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
    }
    
    public synchronized void stop(){
        try{
            thread.join();
            running = false;
        } catch(Exception e){
            e.printStackTrace();
        }
        running = false;
    }
    
    @Override
    public void run(){
        //Init
        handler.initSortingLayer();
        handler.loadImages();
        initObjects();
        MouseInput mouseInput = new MouseInput();
        this.addMouseListener(mouseInput);
        this.addMouseMotionListener(mouseInput);
        this.addKeyListener(new KeyInput());
        this.setFocusable(true);
        //Running...
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000/amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now-lastTime)/ns;
            lastTime = now;
            while(delta >= 1){
                handler.tick();
                delta--;
            }
            if(running){
                render();
            }
            frames++;
            if(System.currentTimeMillis()-timer > 1000){
                timer += 1000;
                System.out.println("FPS: " + frames + " OBJS: " + OBJECT.size());
                frames = 0;
            }
        }
        stop();
    }
    
    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics2D g = (Graphics2D) bs.getDrawGraphics();
        g.setColor(new Color(0, 5, 10));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        handler.render(g);
        g.dispose();
        bs.show();
    }
    
    public static void main(String args[]){
        new Game();
    }
    
}
