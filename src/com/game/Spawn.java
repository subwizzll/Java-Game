package com.game;

import java.util.Random;

// this class handles spawning of objects through out the course of the game events
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

    // checks for game level and executes events accordingly
    public void tick(){
        if(hud.getLevel() == 0){
            handler.object.clear();
            bossSpawned = false;
            handler.addObject(
                    new Player(Game.WIDTH/2-32,Game.HEIGHT/2-32,
                                64,64,
                                0, GameObjectID.Player,
                                handler,game));
            for (int i = 0; i < 2; i++) {
                handler.addObject(
                        new Enemy(r.nextInt((int) Game.WIDTH), r.nextInt(((int) Game.HEIGHT-256)+1)+128,
                                64, 64,
                                3, GameObjectID.Enemy,
                                handler, game));
            }
            hud.setLevel(1);
        }
        if (!bossSpawned) {scoreKeep++;}

        if(scoreKeep >= 500){
            scoreKeep = 0;
            hud.setLevel(hud.getLevel() + 1);

            if (hud.getLevel() == 2){
                for (int i = 0; i < 2; i++) {
                    handler.addObject(
                            new Enemy(r.nextInt((int) Game.WIDTH), r.nextInt(((int) Game.HEIGHT-256)+1)+128,
                                    64, 64,
                                    3, GameObjectID.Enemy,
                                    handler, game));
                }
            }
            else if (hud.getLevel() == 3) {

                for (int i = 0; i < 3; i++) {
                    handler.addObject(
                        new Enemy(r.nextInt((int) Game.WIDTH), r.nextInt(((int) Game.HEIGHT-256)+1)+128,
                                64, 64,
                                3, GameObjectID.Enemy,
                                handler, game));
                }
            }
            else if (hud.getLevel() == 4) {

            handler.addObject(
                    new SmartEnemy(r.nextInt((int)Game.WIDTH), r.nextInt(((int) Game.HEIGHT-256)+1)+128,
                            64,64,
                            5, GameObjectID.SmartEnemy,
                            handler,game));
                for (int i = 0; i < 3; i++) {
                    handler.addObject(
                            new Enemy(r.nextInt((int) Game.WIDTH), r.nextInt(((int) Game.HEIGHT-256)+1)+128,
                                    64, 64,
                                    3, GameObjectID.Enemy,
                                    handler, game));
                }
            }
            else if (hud.getLevel() == 5) {

                for (int i = 0; i < 2; i++) {
                    handler.addObject(
                        new SmartEnemy(r.nextInt((int) Game.WIDTH), r.nextInt(((int) Game.HEIGHT - 256) + 1) + 128,
                                64, 64,
                                5, GameObjectID.SmartEnemy,
                                handler, game));
                }
                for (int i = 0; i < 4; i++) {
                    handler.addObject(
                        new Enemy(r.nextInt((int) Game.WIDTH), r.nextInt(((int) Game.HEIGHT-256)+1)+128,
                                64, 64,
                                3, GameObjectID.Enemy,
                                handler, game));
                }
            }
            else if (hud.getLevel() == 6) {

                for (int i = 0; i < 2; i++) {
                    handler.addObject(
                            new SmartEnemy(r.nextInt((int) Game.WIDTH), r.nextInt(((int) Game.HEIGHT - 256) + 1) + 128,
                                    64, 64,
                                    5, GameObjectID.SmartEnemy,
                                    handler, game));
                }
            }
            else if (hud.getLevel() == 7) {

                for (int i = 0; i < 3; i++) {
                    handler.addObject(
                            new SmartEnemy(r.nextInt((int) Game.WIDTH), r.nextInt(((int) Game.HEIGHT - 256) + 1) + 128,
                                    64, 64,
                                    5, GameObjectID.SmartEnemy,
                                    handler, game));
                }
            }
            else if (hud.getLevel() == 8) {

                for (int i = 0; i < 4; i++) {
                    handler.addObject(
                            new SmartEnemy(r.nextInt((int) Game.WIDTH), r.nextInt(((int) Game.HEIGHT - 256) + 1) + 128,
                                    64, 64,
                                    5, GameObjectID.SmartEnemy,
                                    handler, game));
                }
            }
            else if (hud.getLevel() == 9) {

                for (int i = 0; i < 4; i++) {
                    handler.addObject(
                            new SmartEnemy(r.nextInt((int) Game.WIDTH), r.nextInt(((int) Game.HEIGHT - 256) + 1) + 128,
                                    64, 64,
                                    5, GameObjectID.SmartEnemy,
                                    handler, game));
                }
            }
            else if (hud.getLevel() == 10 && !bossSpawned) {
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
