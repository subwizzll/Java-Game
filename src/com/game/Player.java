package com.game;

import java.awt.*;

public class Player extends GameObject{     // this class defines the characteristics of the Players object

    public Player(float x, float y,
                  float width, float height,
                  float vel, GameObjectID id,
                  Handler handler, Game game) {
        super(x, y, width, height, vel, id, handler, game);
    }

    @Override
    public void tick() {                    // method to synchronize object with game loop
        x += velX;
        y += velY;
        x = Game.clamp(x, 5 , Game.WIDTH - 48);
        y = Game.clamp(y, 2 ,  Game.HEIGHT - 70);
        ticker++;
        if (ticker == 10){
            collision = true;
            ticker = 0;
        }
        collision();
    }

    @Override
    public void render(Graphics g) {        // object rendering method
        g.setColor(Color.WHITE);
        g.fillRect((int)x,(int)y,(int)width,(int)height);
    }

    @Override
    public Rectangle getBounds() {          // method that sets bounds for object collision
        return new Rectangle((int)x,(int)y,(int)width,(int)height);
    }
    private void collision() {               // handles collision characteristics for this object
        if (collision) {
            for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempObject = handler.object.get(i);
                // events if player collides with Enemy objects
                if (tempObject.getId() != GameObjectID.Trail
                        && tempObject.getId() != GameObjectID.Player) {
                    if (getBounds().intersects(tempObject.getBounds())) {
                        HUD.health -= 2;
                        collision = false;
                    }
                }
            }
        }
    }
}
