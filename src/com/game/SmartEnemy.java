package com.game;

import java.awt.*;

public class SmartEnemy extends GameObject{     // this class defines the characteristics of a smart enemy object

    GameObject player;                          // initialize our player variable

    private boolean tracking = true;

    public SmartEnemy(float x, float y, GameObjectID id, Handler handler) {
        super(x, y, id,handler);

        width = 16;
        height = 16;

        for (int i = 0; i < handler.object.size(); i++)                 // loop through object list to find our player
            if (handler.object.get(i).getId() == GameObjectID.Player)   // to assign our player to the variable
                player = handler.object.get(i);                         // so the enemy can see it

    }

    @Override
    public void tick() {                        // method to synchronize object with game loop

        handler.addObject(new Trail(x,y,GameObjectID.Trail,handler,0.05f)); // trail effect

        x += velX;
        y += velY;
        if (tracking) {
            float diffX = x - player.getX();                 // difference between enemy X and player X
            float diffY = y - player.getY();                 // difference between enemy Y and player Y//
            float distance = (float) Math.sqrt(              // calculates distance with the square root
                    Math.pow(diffX, 2) + Math.pow(diffY, 2));// of the sum of diffX^2 + diffY^2                                              ////
            velX = ((-5 / distance) * (diffX - 8));          // sets direction by converting distance to a negative/positive
            velY = ((-5 / distance) * (diffY - 8));          // float multiplying it by the distance of each axis

            if (x <= 0 || x >= Game.WIDTH - 16) velX *= -1;  // prevents object from leaving the window
            if (y <= 0 || y >= Game.HEIGHT - 32) velY *= -1; //
        }
        // allows object to leave the screen after colliding
        else if (x < 0 || x > Game.WIDTH - 16 || y < 0 || y >  Game.HEIGHT - 32) {
            handler.object.remove(this);
        }
        collision();
    }

    @Override
    public void render(Graphics g) {        // object rendering method
        g.setColor(Color.CYAN);
        g.fillRect((int)x,(int)y,(int)width,(int)height);
    }

    @Override
    public Rectangle getBounds() {           // method that sets bounds for object collision
        return new Rectangle((int)x,(int)y,(int)width,(int)height);
    }

    private void collision(){                // handles collision characteristics for this object
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            // defects and disables tracking of a SmartEnemies if they collide with the player or themselves
            if(tempObject.getId() == GameObjectID.SmartEnemy && tempObject != this){
                if(getBounds().intersects(tempObject.getBounds())){
                    tracking = false;
                    velX *= -1;
                    velY *= -1;
                }
            }
        }
    }
}
