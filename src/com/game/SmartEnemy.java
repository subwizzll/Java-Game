package com.game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class SmartEnemy extends GameObject{     // this class defines the characteristics of a smart enemy object

    private BufferedImage bulletLg, bulletSm;

    private boolean isSmall = false;

    GameObject player;                          // initialize our player variable
    private Random r = new Random();

    private boolean tracking = true;
    private int ticker = 0;

    public SmartEnemy(float x, float y,
                      float width, float height,
                      float vel, GameObjectID id,
                      Handler handler, Game game) {
        super(x, y, width, height, vel, id, handler, game);

        SpriteSheet ss = new SpriteSheet(game.getSpriteSheet());

        bulletLg = ss.getImage(13,6,16,16);
        bulletSm = ss.getImage(14,6,16,16);


        for (int i = 0; i < handler.object.size(); i++)                 // loop through object list to find our player
            if (handler.object.get(i).getId() == GameObjectID.Player)   // to assign our player to the variable
                player = handler.object.get(i);                         // so the enemy can see it
    }

    @Override
    public void tick() {                                // method to synchronize object with game loop
        if(game.gameState == Game.STATE.GameOver ){
            tracking = false;
            x += r.nextInt(1) * velX;
            y += r.nextInt(1) * velY;
            return;
        }
        x += velX;
        y += velY;
        handler.addObject(new Trail(                    // trail effect
                getCenterX(),getCenterY(),
                width*.5f,height*.5f,
                0,GameObjectID.Trail,handler,
                game,0.1f,Color.blue));
        if (tracking && player != null) {
            float diffX = this.getCenterX() - player.getCenterX();  // difference between enemy X and player X
            float diffY = this.getCenterY() - player.getCenterY();  // difference between enemy Y and player Y//
            float distance = (float) Math.sqrt(                     // calculates distance with the square root
                    Math.pow(diffX, 2) + Math.pow(diffY, 2));       // of the sum of diffX^2 + diffY^2                                              ////
            velX = ((-this.vel / distance) * diffX);    // sets direction by converting distance to a negative/positive
            velY = ((-this.vel / distance) * diffY);   // float multiplying it by the distance of each axis

            if (x <= 0 || x >= Game.WIDTH - 16) velX *= -1;  // prevents object from leaving the window
            if (y <= 0 || y >= Game.HEIGHT - 32) velY *= -1; //
        }else if (!tracking && velX <= 1 && velY <= 1){
            this.velX = r.nextInt();
            this.velY = r.nextInt();
        }
        // allows object to leave the screen after colliding
        else if (x < 0 || x > Game.WIDTH - width || y < 0 || y >  Game.HEIGHT - height) {
            handler.object.remove(this);
        }
        ticker++;
        if (ticker == 10){
            if(isSmall){
                isSmall = false;
            }else{
                isSmall = true;
            }
            collision = true;
            ticker = 0;
        }else if (ticker == 12){handler.object.remove(this);}
        collision();
    }

    @Override
    public void render(Graphics g) {        // object rendering method and animation logic
        if (isSmall) {
            g.drawImage(bulletSm, (int) x, (int) y, (int) width, (int) height, null);
        } else {
            g.drawImage(bulletLg, (int) x, (int) y, (int) width, (int) height, null);
        }
    }

    @Override
    public Rectangle getBounds() {           // method that sets bounds for object collision
        return new Rectangle((int)x,(int)y,(int)width,(int)height);
    }

    private void collision(){                // handles collision characteristics for this object

    }
}
