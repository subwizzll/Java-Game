package com.game;

import java.awt.*;

public class Enemy extends GameObject{

    public Enemy(int x, int y, GameObjectID id) {
        super(x, y, id);

        velX = 5;
        velY = 5;
    }

    @Override
    public void tick() {                    // method to synchronize object with game loop
        x += velX;
        y += velY;

        if(x <= 0 || x >= Game.WIDTH - 16) velX *= -1;
        if(y <= 0 || y >= Game.HEIGHT - 32) velY *= -1;
    }

    @Override
    public void render(Graphics g) {        // object rendering method
        g.setColor(Color.red);
        g.fillRect(x,y,16,16);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,16,16);

    }
}
