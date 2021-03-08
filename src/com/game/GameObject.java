package com.game;

import java.awt.*;

// parent class for all game objects
public abstract class GameObject {

    protected int x, y;                 // initialize objects environmental position variable

    protected GameObjectID id;          // initialize object ID enum

    protected int velX, velY;           // initialize object speed variable

    public GameObject(int x, int y, GameObjectID id) { // game object constructor
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public abstract void tick();                // method to synchronize object with game loop
    public abstract void render(Graphics g);    // object rendering method
    public abstract Rectangle getBounds();      // object collision method

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

    public void setId(GameObjectID id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getVelX() {
        return velX;
    }

    public int getVelY() {
        return velY;
    }

    public GameObjectID getId() {
        return id;
    }

}
