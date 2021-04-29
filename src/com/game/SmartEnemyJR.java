package com.game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

// this class defines the characteristics of a smart enemy object
public class SmartEnemyJR extends GameObject{

    // object sprites
    private BufferedImage bossBulletLg, bossBulletSm;
    // initialize our player variable
    GameObject player;
    private Random r = new Random();

    private boolean tracking = true;
    private boolean collision = true;
    private boolean isSmall = false;
    private int ticker = 0;

    // constructor
    public SmartEnemyJR(float x, float y,
                        float width, float height,
                        float vel, GameObjectID id,
                        Handler handler, Game game) {
        super(x, y, width, height, vel, id, handler, game);
        SpriteSheet ss = new SpriteSheet(game.getSpriteSheet());

        bossBulletLg = ss.getImage(13,3,16,16);
        bossBulletSm = ss.getImage(14,3,16,16);

        for (int i = 0; i < handler.object.size(); i++)                 // loop through object list to find our player
            if (handler.object.get(i).getId() == GameObjectID.Player)   // to assign our player to the variable
                player = handler.object.get(i);                         // so the enemy can see it

    }

    // method to synchronize object with game loop
    @Override
    public void tick() {
        // stops tracking if player dies
        if(game.gameState == Game.STATE.GameOver ){
            tracking = false;
            x += r.nextInt(1) * velX;
            y += r.nextInt(1) * velY;
        }
        x += velX;
        y += velY;

        // trail effect
        handler.addObject(new Trail(
                getCenterX(),getCenterY()+height/4,
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

                // prevents object from leaving the window
                if (x <= 0 || x >= Game.WIDTH - 16) velX *= -1;
                if (y <= 0 || y >= Game.HEIGHT - 32) velY *= -1;
        // sends enemies in random direction if player is not found
        }else if (!tracking && velX <= 1 && velY <= 1){
            this.velX = r.nextInt();
            this.velY = r.nextInt();
        }
        // allows object to leave the screen after colliding
        else if (x < 0 || x > Game.WIDTH - width || y < 0 || y >  Game.HEIGHT - height) {
            handler.object.remove(this);
        }
        // animation ticker
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

    // object rendering method and animation logic
    @Override
    public void render(Graphics g) {
        if (isSmall) {
            g.drawImage(bossBulletSm, (int) x, (int) y, (int) width, (int) height, null);
        } else {
            g.drawImage(bossBulletLg, (int) x, (int) y, (int) width, (int) height, null);
        }
    }

    // method that sets bounds for object collision
    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)(x+width/2-1),(int)(y+height/2-1),1,1);
    }

    // handles collision characteristics for this object
    private void collision(){
        if (collision){
            for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempObject = handler.object.get(i);
                // deflects and disables tracking of a SmartEnemies if they collide with the player or themselves
                if(tempObject.getId() == GameObjectID.SmartEnemyJR && tempObject != this){
                    if (getBounds().intersectsLine(tempObject.getTop())
                        ||
                        getBounds().intersectsLine(tempObject.getBottom()))
                    {
                        tracking = false;
                        if (velY < 1){
                        velY *= -2;}
                        else velY *= -1;
                    }
                    if (getBounds().intersectsLine(tempObject.getLeft())
                        ||
                        getBounds().intersectsLine(tempObject.getRight()))
                    {
                        tracking = false;
                        if (velX < 1){
                            velX *= -2;}
                        else velX *= -1;
                    }
                }else if (tempObject.getId() == GameObjectID.Player){
                    if(getBounds().intersects(tempObject.getBounds())) {
                        ticker = 11;
                    }
                }
            }
        }
    }
}
