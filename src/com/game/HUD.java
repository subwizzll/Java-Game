package com.game;

import java.awt.*;

public class HUD {

    public static int health = 100;             // initialize health bar variable

    public void tick(){                         // refresh health value

        health = Game.clamp(health,0,100);// prevents health from going to a negative value

    }

    public void render(Graphics g){                 // health bar rendering parameters
        g.setColor(Color.gray);
        g.fillRect(15,15,200,32);
        g.setColor(Color.green);
        g.fillRect(15,15,health * 2,32);
        g.setColor(Color.white);
        g.drawRect(15,15,health * 2,32);

    }

}
