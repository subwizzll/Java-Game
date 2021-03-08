package com.game;

import java.awt.Graphics;
import java.util.LinkedList;

// maintains, updates, and renders game objects
public class Handler {

    LinkedList<GameObject> object = new LinkedList<GameObject>(); // initialize list of ALL GameObjects

    public void tick(){                                 // syncronize all GameObjects in object list with game loop
       for(int i = 0; i < object.size(); i++){
           GameObject tempObject = object.get(i);

           tempObject.tick();
       }
    }

    public void render(Graphics g){                     // renders all GameObjects in object list
        for(int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);

            tempObject.render(g);
        }
    }

    public void addObject(GameObject object){           // adds objects to object list
        this.object.add(object);
    }

    public void removeObject(GameObject object){        // removes objects from object list
        this.object.remove(object);
    }
}
