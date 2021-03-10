package com.game;

import java.util.Random;

public class Spawn {

    private Handler handler;
    private HUD hud;
    private Random r = new Random();
    private int scoreKeep = -1;
    private boolean bossSpawned = false;

    public Spawn(Handler handler, HUD hud) {
        this.handler = handler;
        this.hud = hud;
    }

    public void tick(){
        if(scoreKeep < 0){
            handler.addObject(new Player(Game.WIDTH/2-32,Game.HEIGHT/2-32,GameObjectID.Player, handler));
            handler.addObject(new Enemy(r.nextInt((int)Game.WIDTH), r.nextInt((int)Game.HEIGHT), GameObjectID.Enemy, handler));
        }
        scoreKeep++;

        if(scoreKeep >= 200){
            scoreKeep = 0;
            hud.setLevel(hud.getLevel() + 1);

            if (hud.getLevel() == 2) {
                handler.addObject(new Enemy(r.nextInt((int) Game.WIDTH),
                        r.nextInt((int) Game.HEIGHT), GameObjectID.Enemy, handler));
            }
            else if (hud.getLevel() == 3) {
                handler.addObject(new Enemy(r.nextInt((int) Game.WIDTH),
                        r.nextInt((int) Game.HEIGHT), GameObjectID.Enemy, handler));
                handler.addObject(new Enemy(r.nextInt((int) Game.WIDTH),
                        r.nextInt((int) Game.HEIGHT), GameObjectID.Enemy, handler));
            }
            else if (hud.getLevel() == 4) {
                handler.addObject(new SmartEnemy(r.nextInt((int) Game.WIDTH + 15),
                        r.nextInt((int) Game.HEIGHT + 15), GameObjectID.SmartEnemy, handler));
            }
            else if (hud.getLevel() == 5 && bossSpawned == false) {
                handler.clearEnemies();
                handler.addObject(new EnemyBossJR((int) Game.WIDTH / 2, -64, GameObjectID.EnemyBossJR, handler));
                bossSpawned = true;
            }
        }
    }
}
