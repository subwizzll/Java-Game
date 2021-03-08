package com.game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    private Handler handler;                    // initialize object handler for assigning key inputs
                                                //
    public KeyInput(Handler handler){           // method for receiving object handler
        this.handler = handler;                 //
    }

    @Override
    public void keyPressed(KeyEvent e) {        // handles events for key press
        int key = e.getKeyCode();

        for(int i = 0; i < handler.object.size(); i++){
            GameObject tempObject = handler.object.get(i);

            if(tempObject.getId() == GameObjectID.Player){            // key events for Player()

                setMovementKeyValues(key, tempObject, 5);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {                             // handles events for key releases
        int key = e.getKeyCode();

        for(int i = 0; i < handler.object.size(); i++){
            GameObject tempObject = handler.object.get(i);

            if(tempObject.getId() == GameObjectID.Player){            // key events for Player()

                setMovementKeyValues(key, tempObject, 0);

            }
        }

        if(key == KeyEvent.VK_ESCAPE) System.exit(1);           // close game window with escape key

    }

    private void setMovementKeyValues(int key, GameObject tempObject, int velXY){ // movement key binding method
        if(key == KeyEvent.VK_W) tempObject.setVelY(-velXY);
        if(key == KeyEvent.VK_S) tempObject.setVelY(velXY);
        if(key == KeyEvent.VK_A) tempObject.setVelX(-velXY);
        if(key == KeyEvent.VK_D) tempObject.setVelX(velXY);
    }

}
