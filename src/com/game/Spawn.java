package com.game;

import java.util.Random;

public class Spawn {

    private Handler handler;
    private HUD hud;
    private Game game;
    private Random r = new Random();
    private int scoreKeep = 0;
    public static boolean bossSpawned = false;

    public Spawn(Handler handler, HUD hud, Game game) {
        this.handler = handler;
        this.hud = hud;
        this.game = game;
    }

    public void tick(){
        if(hud.getLevel() == 0){
            handler.object.clear();
            bossSpawned = false;
            handler.addObject(
                    new Player(Game.WIDTH/2-32,Game.HEIGHT/2-32,
                                32,32,
                                0, GameObjectID.Player,
                                handler,game));
            handler.addObject(
                    new Enemy(r.nextInt((int)Game.WIDTH), r.nextInt((int)Game.HEIGHT),
                            16,16,
                            5, GameObjectID.Enemy,
                            handler,game));
            hud.setLevel(1);
        }
        if (!bossSpawned) {scoreKeep++;}

        if(scoreKeep >= 200){
            scoreKeep = 0;
            hud.setLevel(hud.getLevel() + 1);

            if (hud.getLevel() == 2){
                handler.addObject(
                        new Enemy(r.nextInt((int)Game.WIDTH), r.nextInt((int)Game.HEIGHT),
                                16,16,
                                5, GameObjectID.Enemy,
                                handler,game));
            }
            else if (hud.getLevel() == 3) {
                for (int i = 0; i < 2; i++) {
                    handler.addObject(
                        new Enemy(r.nextInt((int) Game.WIDTH), r.nextInt((int) Game.HEIGHT),
                                16, 16,
                                5, GameObjectID.Enemy,
                                handler, game));
                }
            }
            else if (hud.getLevel() == 4) {
            handler.addObject(
                    new SmartEnemy(r.nextInt((int)Game.WIDTH), r.nextInt((int)Game.HEIGHT),
                            16,16,
                            5, GameObjectID.SmartEnemy,
                            handler,game));
            }
            else if (hud.getLevel() == 5 && !bossSpawned) {
                handler.clearEnemies();
                handler.addObject(
                    new EnemyBossJR((int)Game.WIDTH/2, -64,
                            128,128,
                            1, GameObjectID.EnemyBossJR,
                            handler,game));
            }
        }
    }
}
