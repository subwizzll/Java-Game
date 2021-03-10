package com.game;

import java.awt.*;

public class Enemy extends GameObject{      // this class defines the characteristics of a basic enemy object

    public Enemy(float x, float y, GameObjectID id, Handler handler) {
        super(x, y, id,handler);
        width = 16;
        height = 16;
        velX = 5;
        velY = 5;
    }

    @Override
    public void tick() {                    // method to synchronize object with game loop
        handler.addObject(new Trail(x,y,GameObjectID.Trail,handler,0.05f)); // trailing effect
        x += velX;
        y += velY;

        if(x <= 0 || x >= Game.WIDTH - 16) velX *= -1;  // prevents object from leaving the window
        if(y <= 0 || y >= Game.HEIGHT - 32) velY *= -1; //

        collision();
    }

    @Override
    public void render(Graphics g) {        // object rendering method
        g.setColor(Color.red);
        g.fillRect((int)x,(int)y,(int)width,(int)height);
    }

    @Override
    public Rectangle getBounds() {           // method that sets bounds for object collision
        return new Rectangle((int)x,(int)y,(int)width,(int)height);
    }

    private void collision(){                // handles collision characteristics for this object

        for (int i = 0; i < handler.object.size(); i++) {

            GameObject tempObject = handler.object.get(i);

            if(tempObject.getId() == GameObjectID.Player){
                if(getBounds().intersects(tempObject.getBounds())){
                    velX *= -1;
                    velY *= -1;
                }
            }
        }
    }
}
