package com.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends GameObject{     // this class defines the characteristics of the Players object

    private BufferedImage livingSprite, deadSprite;

    private int ticker = 0;

    public Player(float x, float y,
                  float width, float height,
                  float vel, GameObjectID id,
                  Handler handler, Game game) {
        super(x, y, width, height, vel, id, handler, game);

        SpriteSheet ss = new SpriteSheet(game.getSpriteSheet());

        livingSprite = ss.getImage(9,5,16,16);
        deadSprite = ss.getImage(4,2,16,16);
    }

    @Override
    public void tick() {                    // method to synchronize object with game loop
        if (game.gameState == Game.STATE.GameOver)
            vel = 0;
        x += velX;
        y += velY;
        x = Game.clamp(x, 5 , Game.WIDTH - 48);
        y = Game.clamp(y, 128 ,  Game.HEIGHT - 70);
        ticker++;
        if (ticker == 10){
            collision = true;
            ticker = 0;
        }
        collision();
    }

    @Override
    public void render(Graphics g) {        // object rendering method
        if (game.gameState == Game.STATE.GameOver) {
            g.drawImage(deadSprite, (int) x, (int) y, (int) width, (int) height, null);
        }else{
            g.drawImage(livingSprite, (int) x, (int) y, (int) width, (int) height, null);
        }
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
                        HUD.health -= 1;
                        collision = false;
                    }
                }
            }
        }
    }
}
