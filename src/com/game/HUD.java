package com.game;

import java.awt.*;

// this class characterises the heads up display
public class HUD {

    // initialize health bar variables
    public static float health = 100;
    public float greenValue = 255;
    public float redValue = 0;
    // score variable
    private int score = 0;
    // level variable
    private int level = 0;

    // refresh health value
    public void tick(){
        score++;
        // prevents health from going to a negative value
        health = Game.clamp(health,0,100);
        // transitions health from green to red
        greenValue = health * 255 / 100;
        redValue = 255 - greenValue;
    }

    // health bar rendering parameters
    public void render(Graphics g){
        g.setColor(Color.gray);
        g.fillRect(15,15,200,32);
        g.setColor(new Color((int)redValue,(int)greenValue,0));
        g.fillRect(15,15,(int)health * 2,32);
        g.setColor(Color.white);
        g.drawRect(15,15,200,32);
        g.drawString("Score: " + score, 220, 24);
        g.drawString("Level: " + level, 220, 44);
    }

    // possibly redundant getters and setters
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
}
