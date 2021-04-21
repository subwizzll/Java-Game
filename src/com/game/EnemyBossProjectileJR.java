package com.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EnemyBossProjectileJR extends GameObject {      // this class defines the characteristics of a basic enemy object

    private BufferedImage bossBulletLg, bossBulletSm;

    private boolean isSmall = false;

    public EnemyBossProjectileJR(float x, float y,
                                 float width, float height,
                                 float vel, GameObjectID id,
                                 Handler handler, Game game) {

        super(x, y, width, height, vel, id, handler, game);

        velY = 3;

        SpriteSheet ss = new SpriteSheet(game.getSpriteSheet());

        bossBulletLg = ss.getImage(13,4,16,16);
        bossBulletSm = ss.getImage(14,4,16,16);
    }

    @Override
    public void tick() {                    // method to synchronize object with game loop
        handler.addObject(new Trail(                    // trail effect
                getCenterX(),getCenterY(),
                width*.75f,height*.75f,
                0,GameObjectID.Trail,handler,
                game,0.1f,Color.pink));
        x += velX;
        y += velY;

        if(x <= 0 || x >= Game.WIDTH - width) velX *= -1;  // prevents object from leaving the window
        if(y >= Game.HEIGHT - height) velY *= -1;           //
        if(x == 0) handler.removeObject(this);

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
        collision();
    }

    @Override
    public void render(Graphics g) {        // object rendering method and animation logic
        if (isSmall) {
            g.drawImage(bossBulletSm, (int) x, (int) y, (int) width, (int) height, null);
        } else {
            g.drawImage(bossBulletLg, (int) x, (int) y, (int) width, (int) height, null);
        }
    }

    @Override
    public Rectangle getBounds() {           // method that sets bounds for object collision
        return new Rectangle((int)(x+width/2-1),(int)(y+height/2-1),1,1);
    }

    private void collision(){                // handles collision characteristics for this object
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
