package com.game;

import java.awt.*;

public class Player extends GameObject{     // this class defines the characteristics of the Players object

    public Player(int x, int y, GameObjectID id, Handler handler) {
        super(x, y, id,handler);

        width = 32;
        height = 32;
    }

    @Override
    public void tick() {                    // method to synchronize object with game loop
        x += velX;
        y += velY;

        x = Game.clamp(x, 5 , Game.WIDTH - 48);
        y = Game.clamp(y, 2 ,  Game.HEIGHT - 70);

        collision();

    }

    @Override
    public void render(Graphics g) {        // object rendering method
        g.setColor(Color.WHITE);
        g.fillRect(x,y,width,height);
    }

    @Override
    public Rectangle getBounds() {          // method that sets bounds for object collision
        return new Rectangle(x,y,32,32);
    }

    private void collision(){               // handles collision characteristics for this object

        for (int i = 0; i < handler.object.size(); i++) {

            GameObject tempObject = handler.object.get(i);

            if(tempObject.getId() == GameObjectID.Enemy){           // events if player collides with Enemy objects
                if(getBounds().intersects(tempObject.getBounds())){ //
                    HUD.health -= 2;                                //
                }
            }

        }
    }
}
