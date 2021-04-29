package com.game;

import java.awt.*;
import java.awt.image.BufferedImage;

// this class defines the characteristics of the Players object
public class Player extends GameObject{

    // object sprites
    private BufferedImage livingSprite, deadSprite;
    // animation ticker
    private int ticker = 0;

    // constructor
    public Player(float x, float y,
                  float width, float height,
                  float vel, GameObjectID id,
                  Handler handler, Game game) {

        super(x, y, width, height, vel, id, handler, game);

        SpriteSheet ss = new SpriteSheet(game.getSpriteSheet());

        livingSprite = ss.getImage(9,5,16,16);
        deadSprite = ss.getImage(4,2,16,16);
    }

    // method to synchronize object with game loop
    @Override
    public void tick() {
        // stops player movement if death occurs
        if (game.gameState == Game.STATE.GameOver)
            vel = 0;
        // update position
        x += velX;
        y += velY;
        // prevents player from leaving the stage
        x = Game.clamp(x, 5 , Game.WIDTH - 48);
        y = Game.clamp(y, 128 ,  Game.HEIGHT - 70);

        // prevents damage from occur for a short period after being hit
        ticker++;
        if (ticker == 10){
            collision = true;
            ticker = 0;
        }
        // collision check
        collision();
    }

    // object rendering method
    @Override
    public void render(Graphics g) {
        if (game.gameState == Game.STATE.GameOver) {
            g.drawImage(deadSprite, (int) x, (int) y, (int) width, (int) height, null);
        }else{
            g.drawImage(livingSprite, (int) x, (int) y, (int) width, (int) height, null);
        }
    }

    // method that sets bounds for object collision
    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x,(int)y,(int)width,(int)height);
    }

    // handles collision characteristics for this object
    private void collision() {
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
