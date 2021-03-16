package com.game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class SmartEnemy extends GameObject{     // this class defines the characteristics of a smart enemy object

    private BufferedImage bossBullet;
    GameObject player;                          // initialize our player variable
    private Random r = new Random();

    private boolean tracking = true;
    private boolean collision = true;
    private int ticker = 0;

    public SmartEnemy(float x, float y,
                      float width, float height,
                      float vel, GameObjectID id,
                      Handler handler, Game game) {
        super(x, y, width, height, vel, id, handler, game);
        if(this.id == GameObjectID.SmartEnemyJR){
            SpriteSheet ss = new SpriteSheet(game.getSpriteSheet());
            bossBullet = ss.getImage(13,3,16,16);
        }

        for (int i = 0; i < handler.object.size(); i++)                 // loop through object list to find our player
            if (handler.object.get(i).getId() == GameObjectID.Player)   // to assign our player to the variable
                player = handler.object.get(i);                         // so the enemy can see it
    }

    @Override
    public void tick() {                                // method to synchronize object with game loop
        x += velX;
        y += velY;
        handler.addObject(new Trail(                    // trail effect
                getCenterX(),getCenterY(),
                width*.75f,height*.75f,
                0,GameObjectID.Trail,handler,
                game,0.1f,Color.orange));
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
            collision = true;
            ticker = 0;
        }else if (ticker == 12){handler.object.remove(this);}
        collision();
    }

    @Override
    public void render(Graphics g) {        // object rendering method
        if(this.id == GameObjectID.SmartEnemyJR){
            g.drawImage(bossBullet,(int)x,(int)y,(int)width,(int)height,null);
        }
        g.setColor(Color.CYAN);
        g.fillRect((int)x,(int)y,(int)width,(int)height);
    }

    @Override
    public Rectangle getBounds() {           // method that sets bounds for object collision
        return new Rectangle((int)x,(int)y,(int)width,(int)height);
    }

    private void collision(){                // handles collision characteristics for this object
        if (collision) {
            for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempObject = handler.object.get(i);
                // deflects and disables tracking of a SmartEnemies if they collide with the player or themselves
                if (tempObject.getId() == GameObjectID.SmartEnemy && tempObject != this) {
                    if (getBounds().intersectsLine(tempObject.getTop())
                        ||
                        getBounds().intersectsLine(tempObject.getBottom())) {
                        tracking = false;
                        collision = false;
                        velY *= -1;
                    }
                    if (getBounds().intersectsLine(tempObject.getLeft())
                        ||
                        getBounds().intersectsLine(tempObject.getRight())) {
                        tracking = false;
                        collision = false;
                        velX *= -1;
                    }
                }
            }
        }
    }
}
