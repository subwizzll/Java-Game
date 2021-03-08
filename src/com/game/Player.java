package com.game;

import java.awt.*;

public class Player extends GameObject{

    public Player(int x, int y, GameObjectID id) {
        super(x, y, id);
    }

    @Override
    public void tick() {                    // method to synchronize object with game loop
        x += velX;
        y += velY;

        x = Game.clamp(x, 5 , Game.WIDTH - 48);
        y = Game.clamp(y, 2 ,  Game.HEIGHT - 70);

    }

    @Override
    public void render(Graphics g) {        // object rendering method
        g.setColor(Color.WHITE);
        g.fillRect(x,y,32,32);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,32,32);    }
}
