package com.game;

import java.awt.*;
import java.awt.image.BufferedImage;

// this class defines the characteristics of a basic enemy object
public class Enemy extends GameObject {

    // object sprites
    private BufferedImage bulletLg, bulletSm;

    // animation variable
    private boolean isSmall = false;

    // constructor
    public Enemy(float x, float y,
                 float width, float height,
                 float vel, GameObjectID id,
                 Handler handler, Game game) {

        super(x, y, width, height, vel, id, handler, game);

        SpriteSheet ss = new SpriteSheet(game.getSpriteSheet());

        bulletLg = ss.getImage(13,5,16,16);
        bulletSm = ss.getImage(14,5,16,16);

    }

    // method to synchronize object with game loop
    @Override
    public void tick() {
        // trail effect
        handler.addObject(new Trail(
                getCenterX(),getCenterY(),
                width*.5f,height*.5f,
                0,GameObjectID.Trail,handler,
                game,0.1f,Color.green));

        // update position
        x += velX;
        y += velY;

        // prevents object from leaving the window
        if(x <= 0 || x >= Game.WIDTH - width) velX *= -1;
        if(y <= 128 || y >= Game.HEIGHT - (height+height/2)) velY *= -1; //

        // animation ticker
        ticker++;
        if (ticker == 20){
            if(isSmall){
                isSmall = false;
            }else{
                isSmall = true;
            }
            collision = true;
            ticker = 0;
        }
        // collision check
        try{
            collision();
        }catch (Exception ex){
            throw ex;
        }

    }

    // object rendering method and animation logic
    @Override
    public void render(Graphics g) {
        if (isSmall) {
            g.drawImage(bulletSm, (int) x, (int) y, (int) width, (int) height, null);
        } else {
            g.drawImage(bulletLg, (int) x, (int) y, (int) width, (int) height, null);
        }
    }

    // method that sets bounds for object collision
    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)(x+width/2-1),(int)(y+height/2-1),1,1);
    }

    // handles collision characteristics for this object - this object has a bounce effect
    private void collision(){
        if (collision){
            for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempObject = handler.object.get(i);
                if(tempObject.getId() == GameObjectID.Player || tempObject.getId() == GameObjectID.Enemy){
                    if (getBounds().intersectsLine(tempObject.getTop())
                        ||
                        getBounds().intersectsLine(tempObject.getBottom()))
                    {
                        collision = false;
                        velY *= -1;
                    }
                    if (getBounds().intersectsLine(tempObject.getLeft())
                        ||
                        getBounds().intersectsLine(tempObject.getRight()))
                    {
                        collision = false;
                        velX *= -1;
                    }
                }
            }
        }
    }
}
