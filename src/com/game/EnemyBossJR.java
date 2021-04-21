package com.game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class EnemyBossJR extends GameObject{      // this class defines the characteristics of a basic enemy object

    private int timer = 0;
    private Random r = new Random();
    private BufferedImage boss;



    public EnemyBossJR(float x, float y,
                       float width, float height,
                       float vel, GameObjectID id,
                       Handler handler, Game game) {

        super(x, y, width, height, vel, id, handler, game);

        Spawn.bossSpawned = true;

        SpriteSheet ss = new SpriteSheet(game.getSpriteSheet());

        boss = ss.getImage(12,11, ss.getCellSize()*2, ss.getCellSize()*2);

    }

    @Override
    public void tick() {                    // method to synchronize object with game loop
        if(y < 50 && timer == 0) {          // timer starts when boss reaches panning home
            y += velY;
        }
        else {
            timer++;
            if(velX < 3) {
                x += velX * (float) (timer / 100);
            }
            else {
                x += velX;
            }
        }
        if (timer > 0 && timer < 3000) {
            if (x <= 0 || x >= Game.WIDTH - width) velX *= -1;  // prevents object from leaving the window
        if (timer % 25 == 0 ){                               // fires smart enemies every 0.5 seconds
                handler.addObject(new SmartEnemyJR(
                        (int)x+width/2,(int)y+height/2,
                        32,32,
                        4.5f,GameObjectID.SmartEnemyJR,
                        handler, game));
        }
        if (timer >= 1500 && timer % 150 == 0){
            for (int i = -2; i <= 2; i++) {
                handler.addObject(new EnemyBossProjectileJR(
                        (int)x+width/2,(int)y+height/2,
                        32,32,
                        (float) i,GameObjectID.EnemyBossProjectileJR,
                        handler, game));
            }
        }
        }else if(timer > 3000){
            velX = 0;
            velY = -5;
            y += velY;
        }
        if(timer > 3000 && y < -64){
            Spawn.bossSpawned = false;

            handler.removeObject(this);

            if (game.gameState == Game.STATE.Game) game.gameState = Game.STATE.GameWon;
        }
        collision();
    }

    @Override
    public void render(Graphics g) {        // object rendering method

        g.drawImage(boss,(int)x,(int)y,(int)width,(int)height,null);
    }

    @Override
    public Rectangle getBounds() {           // method that sets bounds for object collision
        return new Rectangle((int)x,(int)y,(int)width,(int)height);
    }

    private void collision(){                // handles collision characteristics for this object
    }
}
