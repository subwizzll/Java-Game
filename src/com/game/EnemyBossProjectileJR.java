package com.game;

import java.awt.*;

public class EnemyBossProjectileJR extends GameObject {      // this class defines the characteristics of a basic enemy object

    public EnemyBossProjectileJR(float x, float y,
                                 float width, float height,
                                 float vel, GameObjectID id,
                                 Handler handler, Game game) {

        super(x, y, width, height, vel, id, handler, game);
        velY = 3;

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
        if (ticker == 10){
            collision = true;
            ticker = 0;
        }
        collision();
    }

    @Override
    public void render(Graphics g) {        // object rendering method
        g.setColor(Color.red);
        g.fillRect((int)x,(int)y,(int)width,(int)height);
    }

    @Override
    public Rectangle getBounds() {           // method that sets bounds for object collision
        return new Rectangle((int)(x+width/2-5),(int)(y+height/2-5),5,5);
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
