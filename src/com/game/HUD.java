package com.game;

import java.awt.*;

public class HUD {                              // this class characterises the heads up display

    public static float health = 100;           // initialize health bar variables
    public float greenValue = 255;              //
    public float redValue = 0;                  //
    private int score = 0;                      // score variable
    private int level = 1;                      // level variable

    public void tick(){                         // refresh health value
        score++;
        health = Game.clamp(health,0,100); // prevents health from going to a negative value
        greenValue = health * 255 / 100;             // transitions health from green to red
        redValue = 255 - greenValue;                 //
    }

    public void render(Graphics g){                 // health bar rendering parameters
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
