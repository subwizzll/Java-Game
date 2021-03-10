package com.game;

import java.awt.*;
import java.util.Random;

public class EnemyBossJR extends GameObject{      // this class defines the characteristics of a basic enemy object

    private int timer = 0;
    private Random r;

    public EnemyBossJR(float x, float y, GameObjectID id, Handler handler) {
        super(x, y, id,handler);
        width = 64;
        height = 64;
        velX = 1;
        velY = 1;
    }

    @Override
    public void tick() {                    // method to synchronize object with game loop
        if(y < 50 && timer == 0) {          // timer starts when boss reaches panning home
            y += velY;
        }
        else {
            timer++;
            x += velX;
        }
        if (timer > 0 && timer < 1000) {
            if (x <= 0 || x >= Game.WIDTH - 16) velX *= -1;  // prevents object from leaving the window
        if (timer % 50 == 0 ){                               // fires smart enemies every 0.5 seconds
                handler.addObject(new SmartEnemy((int)x+32,(int)y+32, GameObjectID.SmartEnemy, handler));
            }
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
        return new Rectangle((int)x,(int)y,(int)width,(int)height);
    }

    private void collision(){                // handles collision characteristics for this object
    }
}
