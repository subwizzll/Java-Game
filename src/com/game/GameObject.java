package com.game;

import java.awt.*;
import java.awt.geom.Line2D;

// parent class for all game objects
public abstract class GameObject {

    protected float x, y, width, height;  // initialize objects environmental position variable
    protected GameObjectID id;            // initialize object ID enum
    protected Handler handler;            // initialize object handler variable
    protected Game game;                  // initialize game variable
    protected float velX, velY, vel;           // initialize object speed variables
    protected Line2D top, right, bottom, left; // initialize object bounding box sides
    protected boolean collision = true;        // default collision setting
    protected int ticker = 0;                  // initialize ticker for event triggers

    // root game object constructor
    public GameObject(float x, float y,
                      float width, float height,
                      float vel,
                      GameObjectID id, Handler handler, Game game) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.velX = vel;
        this.velY = vel;
        this.vel = vel;
        this.id = id;
        this.handler = handler;
        this.game = game;

    }

    public abstract void tick();                // method to synchronize object with game loop
    public abstract void render(Graphics g);    // object rendering method
    public abstract Rectangle getBounds();      // method that sets bounds for object collision

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public float getCenterX() {
        return this.x+ this.width/2;
    }

    public float getCenterY() {
        return this.y+ this.height/2;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Line2D getTop() {
        top = new Line2D.Float(this.x, this.y, this.x+this.width, this.y);
        return top;
    }

    public Line2D getRight() {
        right = new Line2D.Float(this.x+this.width, this.y, this.x+this.width, this.y+this.height);
        return right;
    }

    public Line2D getBottom() {
        bottom = new Line2D.Float(this.x, this.y+this.height, this.x+this.width, this.y+this.height);
        return bottom;
    }

    public Line2D getLeft() {
        left = new Line2D.Float(this.x, this.y, this.x, this.y+this.height);
        return left;
    }

    public GameObjectID getId() {
        return id;
    }
}
