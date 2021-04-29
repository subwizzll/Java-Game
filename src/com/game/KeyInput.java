package com.game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// this class handles game controls
public class KeyInput extends KeyAdapter {

    // initialize object handler for assigning key inputs
    private Handler handler;
    private Game game;
    private boolean[] keyPressed = new boolean[] {false, false, false, false};

    // method for receiving object handler
    public KeyInput(Handler handler, Game game){
        this.handler = handler;
        this.game = game;
    }

    // handles events for key press
    @Override
    public void keyPressed(KeyEvent e) {
        if (game.gameState == Game.STATE.Game) {
            int key = e.getKeyCode();
            for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempObject = handler.object.get(i);
                // key events for Player()
                if (tempObject.getId() == GameObjectID.Player) {
                    float speed = 6.0f;
                    if (key == KeyEvent.VK_W) {
                        tempObject.setVelY(-speed);
                        keyPressed[0] = true;
                    }
                    if (key == KeyEvent.VK_S) {
                        tempObject.setVelY(speed);
                        keyPressed[1] = true;
                    }
                    if (key == KeyEvent.VK_A) {
                        tempObject.setVelX(-speed);
                        keyPressed[2] = true;
                    }
                    if (key == KeyEvent.VK_D) {
                        tempObject.setVelX(speed);
                        keyPressed[3] = true;
                    }
                }
            }
        }
    }

    // handles events for key releases
    @Override
    public void keyReleased(KeyEvent e) {
        if (game.gameState == Game.STATE.Game) {
            int key = e.getKeyCode();
            for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempObject = handler.object.get(i);
                // key events for Player()
                if (tempObject.getId() == GameObjectID.Player) {
                    if (key == KeyEvent.VK_W) keyPressed[0] = false;
                    if (key == KeyEvent.VK_S) keyPressed[1] = false;
                    if (key == KeyEvent.VK_A) keyPressed[2] = false;
                    if (key == KeyEvent.VK_D) keyPressed[3] = false;
                    if (!keyPressed[0] && !keyPressed[1]) tempObject.setVelY(0);
                    if (!keyPressed[2] && !keyPressed[3]) tempObject.setVelX(0);
                }
            }
            if (key == KeyEvent.VK_ESCAPE) {
                if (!Game.paused) Game.paused = true;
                else Game.paused = false;
            }
        }
    }
}
