package com.game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

// this class defines the characteristics of the enemy boss object
public class EnemyBossJR extends GameObject{

    // boss timer variable
    private int timer = 0;
    private Random r = new Random();
    // object sprite
    private BufferedImage boss;


    // constructor
    public EnemyBossJR(float x, float y,
                       float width, float height,
                       float vel, GameObjectID id,
                       Handler handler, Game game) {

        super(x, y, width, height, vel, id, handler, game);

        // allows certain events if boss is present
        Spawn.bossSpawned = true;

        SpriteSheet ss = new SpriteSheet(game.getSpriteSheet());

        boss = ss.getImage(12,11, ss.getCellSize()*2, ss.getCellSize()*2);

    }

    // method to synchronize object with game loop
    @Override
    public void tick() {
        if(y < 50 && timer == 0) {
            y += velY;
        }else {
            // timer starts when boss reaches panning home
            timer++;
            if(velX < 3) {
                x += velX * (float) (timer / 100);
            }
            else {
                x += velX;
            }
        }
        if (timer > 0 && timer < 3000) {
            // prevents object from leaving the window
            if (x <= 0 || x >= Game.WIDTH - width) velX *= -1;
        if (timer % 25 == 0 ){
                // fires smart enemies every 0.5 seconds
                handler.addObject(new SmartEnemyJR(
                        (int)x+width/2,(int)y+height/2,
                        32,32,
                        4.5f,GameObjectID.SmartEnemyJR,
                        handler, game));
        }
        if (timer >= 1500 && timer % 150 == 0){
            // fires scattering projectiles
            for (int i = -2; i <= 2; i++) {
                handler.addObject(new EnemyBossProjectileJR(
                        (int)x+width/2,(int)y+height/2,
                        32,32,
                        (float) i,GameObjectID.EnemyBossProjectileJR,
                        handler, game));
            }
        }
        }else if(timer > 3000){
            // boss leaves the stage
            velX = 0;
            velY = -5;
            y += velY;
        }
        if(timer > 3000 && y < -64){
            // boss object removed after leaving stage
            Spawn.bossSpawned = false;

            handler.removeObject(this);

            if (game.gameState == Game.STATE.Game) game.gameState = Game.STATE.GameWon;
        }
        // collision check - not defined
        collision();
    }

    // object rendering method
    @Override
    public void render(Graphics g) {
        g.drawImage(boss,(int)x,(int)y,(int)width,(int)height,null);
    }

    // method that sets bounds for object collision
    @Override
    public Rectangle getBounds() {           // method that sets bounds for object collision
        return new Rectangle((int)x,(int)y,(int)width,(int)height);
    }

    // handles collision characteristics for this object
    private void collision(){
    }
}
