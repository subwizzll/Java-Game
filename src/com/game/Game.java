package com.game;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serial;
import java.util.Random;


public class Game extends Canvas implements Runnable{

    @Serial
    private static final long serialVersionUID = 8738526053709115505L;

    public static float WIDTH = 1280;              // initialize window size constants
    public static float HEIGHT = WIDTH / 16 * 9;  //
    public static boolean paused = false;
    private Thread thread;                        // initialize Game thread
    private boolean running = false;              //
    private BufferedImage[][] wallImage = new BufferedImage[(int)HEIGHT/64][(int)WIDTH/64];
    private BufferedImage spriteSheet = null;
    private BufferedImage spriteSheet2 = null;
    private Handler handler;                      // initialize object handler
    private Menu menu;     // initialize menu variable
    private HUD hud;                              // initialize heads up display
    private Spawn spawner;                        // initialize spawner
    public enum STATE{                            // handles shift between launch menu and game
        Menu,                                     //
        Settings,                                 //
        HowToPlay,                                //
        Game,                                     //
        GameWon,
        GameOver
        }
    public STATE gameState = STATE.Menu;
    private Random r = new Random();

    public void init(){
        BufferedImageLoader loader = new BufferedImageLoader();
        try {
            this.spriteSheet = loader.loadImage("resources/0x72_16x16DungeonTileset.v4.png");
            this.spriteSheet2 = loader.loadImage("resources/0x72_16x16DungeonTileset_walls.v2.png");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public Game() {                                     // game constructor
        handler = new Handler();                        // instantiate object handler class object
        hud = new HUD();                                // instantiate HUD class object
        menu = new Menu(this,handler,hud);
        this.addKeyListener(new KeyInput(handler,this));     // asks KeyInput class to listen for key input and assign
        new Window(WIDTH, HEIGHT, "Let's Build a Game!", this); // call game window
        spawner = new Spawn(handler, hud, this);  // instantiate Spawner class object
        this.addMouseListener(menu);                    // listens for mouse input from the menu
        setWalls();
    }

    public synchronized void start(){                   // executes Game thread
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop(){                    // stops Game thread
        try{                                            // prints a stack trace if failed
            thread.join();
            running = false;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void run(){
        init();
        this.requestFocus();                            // sets focus to game window when launched
        /* this loop sets the pace of the game environment */
        long lastTime = System.nanoTime();              // returns:the current value of the running JVM time in nanosecs
        double amountOfTicks = 60.0;                    // seconds in a minute
        double ns = 1000000000 / amountOfTicks;         // nanoseconds in a second / seconds in minute
        double delta = 0;                               // variable used to calculate loop timer floatervals
        long timer = System.currentTimeMillis();        // returns:the current time in milliseconds
        int updates = 0;
        int frames = 0;                                 // frames variable for framerate
        while(running){                                 // loop runs while game thread is actively running
            long now = System.nanoTime();               // returns:the current value of the running JVM time in nanosecs
            delta += (now - lastTime) / ns;             // calculate time interval of loop
            lastTime = now;                             // resets interval
            while(delta >= 1){                          // if delta is >= new ticks are called until its value falls
                tick();
                updates++;
                delta--;
            }
            if(running){                                // renders new screen if game is still running
                render();
            }
            frames++;                                   // increment frame count

            if(System.currentTimeMillis() - timer > 1000){  //if (loop start time to now) > 1 second print results
                timer += 1000;                              // increment timer by 1 second
                System.out.println(updates + " Ticks, FPS: " + frames); // results string
                updates = 0;                            // refresh frame and update record
                frames = 0;                             //
            }
        }
        stop();
    }

    private void tick(){                                // this method synchronizes all game objects
        if(gameState == STATE.Game){                    //
            if(!paused) {
                handler.tick();                             // with the game loop
                hud.tick();                                 //
                spawner.tick();                             //
                if (HUD.health <= 0) {
                    handler.removePlayer();
                    HUD.health = 100;
                    gameState = STATE.GameOver;
                }
            }else{
                menu.tick();
            }
        }else{                                          //
            menu.tick();                                //
            handler.tick();                             // with the game loop

        }
    }

    private void render(){                              // rendering method called by game loop in run()
        BufferStrategy bs = this.getBufferStrategy();   //
        if(bs == null){                                 //
            this.createBufferStrategy(3);     //
            return;                                     //
        }

        Graphics g = bs.getDrawGraphics();              // creates a graphics context for the drawing buffer

        for (int h = 0; h <= HEIGHT/64; h++) {
            for (int w = 0; w < WIDTH/64; w++) {
                if(h < wallImage.length) {
                    g.drawImage(wallImage[h][w], w * 64, h * 64, 64, 64, null);
                }
            }
        }
        handler.render(g);                              // render all game objects

        if(gameState == STATE.Game && !paused){         // renders proper environment in correspondence with current
            hud.render(g);                              // STATE
        }else{                                          //
            menu.render(g);                             //
        }


        g.dispose();                                    // releases graphics to be rendered
        bs.show();                                      //
    }

    public static float clamp(float var, float min, float max){  // this method can be used to prevents objects
        if (var >= max)                                          // from leaving a defined range
            return max;
        else return Math.max(var, min);
    }

    public static void main(String[] args) {
        new Game();
    }

    public BufferedImage getSpriteSheet(){
        return spriteSheet;
    }
    public BufferedImage getSpriteSheet2(){
        return spriteSheet2;
    }
    public static int getRandom(int[][] array) {
        return new Random().nextInt(array.length);
    }

    public void setWalls(){
        SpriteSheet ss = new SpriteSheet(this.getSpriteSheet());
        int[][] wallCells = new int[][] {{2,2},{2,2},{2,2},{2,2},{2,4},{2,5},{1,4},{1,5}};
        int[][] floorCells1 = new int[][]{{3,1},{3,2},{3,3}};
        int[][] floorCells2 = new int[][]{{7,1},{7,2},{7,3},{7,4}};
        int[][] floorCells3 = new int[][]{{8,1},{8,2},{8,3},{8,4}};
        int[][] floorCells4 = new int[][]{{9,1},{9,2},{9,3},{9,4}};
        int[] cell;
        for (int row = 0; row < wallImage.length; row++) {
            for (int col = 0; col < wallImage[row].length; col++) {
                if (row < 2) {
                    cell = wallCells[getRandom(wallCells)];
                    if (row == 0 && (cell == wallCells[4] || cell == wallCells[5])) {
                        col--;
                    } else if (row == 1 && (cell == wallCells[6] || cell == wallCells[7])) {
                        col--;
                    } else if (row == 1 && !(cell == wallCells[0] || cell == wallCells[1]
                            || cell == wallCells[2] || cell == wallCells[3])) {
                        this.wallImage[row][col] = ss.getImage(cell[0], cell[1], ss.getCellSize(), ss.getCellSize());
                        this.wallImage[row + 1][col] = ss.getImage(3, 4, ss.getCellSize(), ss.getCellSize());
                    } else {
                        this.wallImage[row][col] = ss.getImage(cell[0], cell[1], ss.getCellSize(), ss.getCellSize());
                    }
                }else if (row == 2){
                    cell = floorCells1[getRandom(floorCells1)];
                    if (wallImage[2][col] == null){
                        this.wallImage[row][col] = ss.getImage(cell[0], cell[1], ss.getCellSize(), ss.getCellSize());
                    }
                }else if(row == 3){
                    if (col == 0){
                        this.wallImage[row][col] = ss.getImage(floorCells2[0][0], floorCells2[0][1], ss.getCellSize(), ss.getCellSize());
                    }else if (col == wallImage[3].length-1){
                        this.wallImage[row][col] = ss.getImage(floorCells2[3][0], floorCells2[3][1], ss.getCellSize(), ss.getCellSize());
                    }else if (col % 2 == 1){
                        this.wallImage[row][col] = ss.getImage(floorCells2[1][0], floorCells2[1][1], ss.getCellSize(), ss.getCellSize());
                    }else{
                        this.wallImage[row][col] = ss.getImage(floorCells2[2][0], floorCells2[2][1], ss.getCellSize(), ss.getCellSize());
                    }
                }else if(row > 3 && row < wallImage.length-1){
                    if (col == 0){
                        this.wallImage[row][col] = ss.getImage(floorCells3[0][0], floorCells3[0][1], ss.getCellSize(), ss.getCellSize());
                    }else if (col == wallImage[3].length-1){
                        this.wallImage[row][col] = ss.getImage(floorCells3[3][0], floorCells3[3][1], ss.getCellSize(), ss.getCellSize());
                    }else if (col % 2 == 1){
                        this.wallImage[row][col] = ss.getImage(floorCells3[1][0], floorCells3[1][1], ss.getCellSize(), ss.getCellSize());
                    }else{
                        this.wallImage[row][col] = ss.getImage(floorCells3[2][0], floorCells3[2][1], ss.getCellSize(), ss.getCellSize());
                    }
                }else{
                    if (col == 0){
                        this.wallImage[row][col] = ss.getImage(floorCells4[0][0], floorCells4[0][1], ss.getCellSize(), ss.getCellSize());
                    }else if (col == wallImage[3].length-1){
                        this.wallImage[row][col] = ss.getImage(floorCells4[3][0], floorCells4[3][1], ss.getCellSize(), ss.getCellSize());
                    }else if (col % 2 == 1){
                        this.wallImage[row][col] = ss.getImage(floorCells4[1][0], floorCells4[1][1], ss.getCellSize(), ss.getCellSize());
                    }else{
                        this.wallImage[row][col] = ss.getImage(floorCells4[2][0], floorCells4[2][1], ss.getCellSize(), ss.getCellSize());
                    }
                }
            }
        }
    }
}
