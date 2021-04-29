package com.game;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serial;
import java.util.Random;

// core class of the game
public class Game extends Canvas implements Runnable{

    @Serial
    private static final long serialVersionUID = 8738526053709115505L;

    // initialize window size constants
    public static float WIDTH = 1280;              
    public static float HEIGHT = WIDTH / 16 * 9;
    
    // pause variable
    public static boolean paused = false;
    // initialize Game thread
    private Thread thread;                        
    private boolean running = false;
    // initialize background image + sprite sheet variable
    private BufferedImage[][] backGroundImages = new BufferedImage[(int)HEIGHT/64][(int)WIDTH/64];
    private BufferedImage backGround;
    private BufferedImage spriteSheet = null;
    // initialize object handler
    private Handler handler;
    // initialize menu variable
    private Menu menu;
    // initialize heads up display
    private HUD hud;
    // initialize spawner
    private Spawn spawner;

    // list of game states
    public enum STATE{
        Menu,
        Settings,
        HowToPlay,
        Game,
        GameWon,
        GameOver
    }

    // initialize game state
    public STATE gameState = STATE.Menu;

    private Random r = new Random();

    // load sprite sheet
    public void init(){
        BufferedImageLoader loader = new BufferedImageLoader();
        try {
            this.spriteSheet = loader.loadImage("resources/0x72_16x16DungeonTileset.v4.png");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // game constructor
    public Game() {
        // instantiate object handler class object
        handler = new Handler();
        // instantiate HUD class object
        hud = new HUD();
        // instantiate menu
        menu = new Menu(this,handler,hud);
        // asks KeyInput class to listen for key input and assign
        this.addKeyListener(new KeyInput(handler,this));
        // call game window
        new Window(WIDTH, HEIGHT, "Dark Dungeon", this);
        // instantiate Spawner class object
        spawner = new Spawn(handler, hud, this);
        // listens for mouse input from the menu
        this.addMouseListener(menu);
        // set back ground image
        backGround = setBackground(backGroundImages);
    }

    // executes Game thread
    public synchronized void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    // stops Game thread
    public synchronized void stop(){
        try{
            thread.join();
            running = false;
        }catch(Exception e){
            // prints a stack trace if failed
            e.printStackTrace();
        }
    }

    // game loop - synchronizes all game object tick methods
    public void run(){
        init();
        // sets focus to game window when launched
        this.requestFocus();
        /* this loop sets the pace of the game environment */
        // returns:the current value of the running JVM time in nanosecs
        long lastTime = System.nanoTime();
        // seconds in a minute
        double amountOfTicks = 60.0;
        // nanoseconds in a second / seconds in minute
        double ns = 1000000000 / amountOfTicks;
        // variable used to calculate loop timer floatervals
        double delta = 0;
        // returns:the current time in milliseconds
        long timer = System.currentTimeMillis();
        int updates = 0;
        // frames variable for framerate
        int frames = 0;
        // loop runs while game thread is actively running
        while(running){
            // returns:the current value of the running JVM time in nanosecs
            long now = System.nanoTime();
            // calculate time interval of loop
            delta += (now - lastTime) / ns;
            // resets interval
            lastTime = now;
            // if delta is >= new ticks are called until its value falls
            while(delta >= 1){
                tick();
                updates++;
                delta--;
            }
            // renders new screen if game is still running
            if(running){
                try {
                    render();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // increment frame count
            frames++;
            //if (loop start time to now) > 1 second print results
            if(System.currentTimeMillis() - timer > 1000){
                // increment timer by 1 second
                timer += 1000;
                // results string
                System.out.println(updates + " Ticks, FPS: " + frames);
                // refresh frame and update record
                updates = 0;
                frames = 0;
            }
        }
        stop();
    }

    // this method synchronizes all game objects with the game loop
    private void tick(){
        if(gameState == STATE.Game){
            if(!paused) {
                handler.tick();
                hud.tick();
                spawner.tick();
                if (HUD.health <= 0) {
                    HUD.health = 100;
                    gameState = STATE.GameOver;
                }
            }else{
                menu.tick();
            }
        }else{
            menu.tick();
            handler.tick();

        }
    }

    // rendering method called by game loop in run()
    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }

        // creates a graphics context for the drawing buffer
        Graphics g = bs.getDrawGraphics();
        // render back ground image
        g.drawImage(backGround,0,0,null);
        // render all game objects
        handler.render(g);

        // renders proper environment in correspondence with current state
        if(gameState == STATE.Game && !paused){
            hud.render(g);
        }else{
            menu.render(g);
        }

        // releases graphics to be rendered
        g.dispose();
        bs.show();
    }

    // this method can be used to prevents objects from leaving a defined range
    public static float clamp(float var, float min, float max){
        if (var >= max)
            return max;
        else return Math.max(var, min);
    }

    // main method
    public static void main(String[] args) {
        new Game();
    }

    // gets sprite sheet from resources folder
    public BufferedImage getSpriteSheet(){
        return spriteSheet;
    }

    public static int getRandom(int[][] array) {
        return new Random().nextInt(array.length);
    }

    // joins randomly chosen back ground images
    public static BufferedImage joinBufferedImage(BufferedImage[][] img) {

        //create a new buffer and draw two image into the new image
        BufferedImage newImage = new BufferedImage((int)WIDTH,(int)HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = newImage.createGraphics();

        for (int h = 0; h <= HEIGHT/64; h++) {
            for (int w = 0; w < WIDTH/64; w++) {
                if(h < img.length) {
                    g2.drawImage(img[h][w], w * 64, h * 64, 64, 64, null);
                }
            }
        }
        g2.dispose();
        return newImage;
    }

    // randomly picks sprite sheet images for back ground and assigns to an array
    public BufferedImage setBackground(BufferedImage[][] img){
        SpriteSheet ss = new SpriteSheet(this.getSpriteSheet());
        int[][] wallCells = new int[][] {{2,2},{2,2},{2,2},{2,2},{2,4},{2,5},{1,4},{1,5}};
        int[][] floorCells1 = new int[][]{{3,1},{3,2},{3,3}};
        int[][] floorCells2 = new int[][]{{7,1},{7,2},{7,3},{7,4}};
        int[][] floorCells3 = new int[][]{{8,1},{8,2},{8,3},{8,4}};
        int[][] floorCells4 = new int[][]{{9,1},{9,2},{9,3},{9,4}};
        int[] cell;
        for (int row = 0; row < img.length; row++) {
            for (int col = 0; col < img[row].length; col++) {
                if (row < 2) {
                    cell = wallCells[getRandom(wallCells)];
                    if (row == 0 && (cell == wallCells[4] || cell == wallCells[5])) {
                        col--;
                    } else if (row == 1 && (cell == wallCells[6] || cell == wallCells[7])) {
                        col--;
                    } else if (row == 1 && !(cell == wallCells[0] || cell == wallCells[1]
                            || cell == wallCells[2] || cell == wallCells[3])) {
                        img[row][col] = ss.getImage(cell[0], cell[1], ss.getCellSize(), ss.getCellSize());
                        img[row + 1][col] = ss.getImage(3, 4, ss.getCellSize(), ss.getCellSize());
                    } else {
                        img[row][col] = ss.getImage(cell[0], cell[1], ss.getCellSize(), ss.getCellSize());
                    }
                }else if (row == 2){
                    cell = floorCells1[getRandom(floorCells1)];
                    if (img[2][col] == null){
                        img[row][col] = ss.getImage(cell[0], cell[1], ss.getCellSize(), ss.getCellSize());
                    }
                }else if(row == 3){
                    if (col == 0){
                        img[row][col] = ss.getImage(floorCells2[0][0], floorCells2[0][1], ss.getCellSize(), ss.getCellSize());
                    }else if (col == img[3].length-1){
                        img[row][col] = ss.getImage(floorCells2[3][0], floorCells2[3][1], ss.getCellSize(), ss.getCellSize());
                    }else if (col % 2 == 1){
                        img[row][col] = ss.getImage(floorCells2[1][0], floorCells2[1][1], ss.getCellSize(), ss.getCellSize());
                    }else{
                        img[row][col] = ss.getImage(floorCells2[2][0], floorCells2[2][1], ss.getCellSize(), ss.getCellSize());
                    }
                }else if(row > 3 && row < img.length-1){
                    if (col == 0){
                        img[row][col] = ss.getImage(floorCells3[0][0], floorCells3[0][1], ss.getCellSize(), ss.getCellSize());
                    }else if (col == img[3].length-1){
                        img[row][col] = ss.getImage(floorCells3[3][0], floorCells3[3][1], ss.getCellSize(), ss.getCellSize());
                    }else if (col % 2 == 1){
                        img[row][col] = ss.getImage(floorCells3[1][0], floorCells3[1][1], ss.getCellSize(), ss.getCellSize());
                    }else{
                        img[row][col] = ss.getImage(floorCells3[2][0], floorCells3[2][1], ss.getCellSize(), ss.getCellSize());
                    }
                }else{
                    if (col == 0){
                        img[row][col] = ss.getImage(floorCells4[0][0], floorCells4[0][1], ss.getCellSize(), ss.getCellSize());
                    }else if (col == img[3].length-1){
                        img[row][col] = ss.getImage(floorCells4[3][0], floorCells4[3][1], ss.getCellSize(), ss.getCellSize());
                    }else if (col % 2 == 1){
                        img[row][col] = ss.getImage(floorCells4[1][0], floorCells4[1][1], ss.getCellSize(), ss.getCellSize());
                    }else{
                        img[row][col] = ss.getImage(floorCells4[2][0], floorCells4[2][1], ss.getCellSize(), ss.getCellSize());
                    }
                }
            }
        }
        return joinBufferedImage(img);
    }
}
