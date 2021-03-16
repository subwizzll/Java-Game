package com.game;

import java.awt.*;

public class Trail extends GameObject{      // this class defines the characteristics of trailing effects

    private float alpha = 1;                // this variable defines trailing objects transparency value
    private float life;                     // this variable is used to decrement transparency within the tick() method
    private Color color;

    public Trail(float x, float y,
                 float width, float height,
                 float vel, GameObjectID id,
                 Handler handler, Game game, float life, Color color) {
        super(x, y, width, height, vel, id, handler, game);
        this.life = life;
        this.color = color;
    }

    @Override
    public void tick() {                                // fades trailing objects with every tick
        if (alpha > life)                               //
            alpha -= life;                              //
        else handler.removeObject(this);                // object is removed once alpha reaches zero
    }

    @Override
    public void render(Graphics g) {                    // object rendering method
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(makeTransparent(alpha));

        g.setColor(color);
        g.fillOval((int)x-(int)width/2,(int)y-(int)height/2,(int)width,(int)height);

        g2d.setComposite(makeTransparent(1));
    }

    private AlphaComposite makeTransparent(float alpha){    // this method creates our fading transparency effect
        int type = AlphaComposite.SRC_OVER;                 //
        return AlphaComposite.getInstance(type, alpha);     //
    }

    @Override
    public Rectangle getBounds() {                          // this object has no bounds
        return null;
    }
}
