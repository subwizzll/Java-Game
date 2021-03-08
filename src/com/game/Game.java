package com.game;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.Serial;


public class Game extends Canvas implements Runnable{

    @Serial
    private static final long serialVersionUID = 8738526053709115505L;

    public static final int WIDTH = 800;               // initialize window size constants
    public static final int HEIGHT = WIDTH / 16 * 9;    //

    private Thread thread;                              // initialize Game thread
    private boolean running = false;                    //

    private Handler handler;                            // initialize object handler

    private HUD hud;                                    // initialize heads up display

    public Game() {                                     // game constructor

        handler = new Handler();                        // instantiate object handler class object

        this.addKeyListener(new KeyInput(handler));     // asks KeyInput class to listen for key input and assigns
                                                        // object handler inside this class

        new Window(WIDTH, HEIGHT, "Let's Build a Game!", this);

        hud = new HUD();                                // instantiate HUD class object

        handler.addObject(new Player(WIDTH/2-32,HEIGHT/2-32,GameObjectID.Player, handler));
        handler.addObject(new Enemy(WIDTH/2+32,HEIGHT/2+32,GameObjectID.Enemy, handler));

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
        this.requestFocus();                            // sets focus to game window when launched

        // this loop sets the pace of the game environment
        long lastTime = System.nanoTime();              // returns:the current value of the running JVM time in nanosecs
        double amountOfTicks = 60.0;                    // seconds in a minute
        double ns = 1000000000 / amountOfTicks;         // nanoseconds in a second / seconds in minute
        double delta = 0;                               // variable used to calculate loop timer intervals
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

    private void tick(){                                // this method synchronizes all game objects and HUD
        handler.tick();                                 // with the game loop
        hud.tick();
    }

    private void render(){                              // rendering method called by game loop in run()
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();              // creates a graphics context for the drawing buffer

        g.setColor(Color.BLACK);                        // set background color
        g.fillRect(0,0,WIDTH,HEIGHT);             //

        handler.render(g);                              // render all game objects
        hud.render(g);                                  // render HUD

        g.dispose();                                    // releases graphics to be rendered
        bs.show();                                      //
    }

    public static int clamp(int var, int min, int max){  // this method prevents objects from traveling out of frame
        if (var >= max)
            return max;
        else if (var <= min)
            return min;
        else
            return var;
    }

    public static void main(String[] args) {

        new Game();

    }
}
