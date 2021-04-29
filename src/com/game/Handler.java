package com.game;

import java.awt.Graphics;
import java.util.LinkedList;

// maintains, updates, and renders game objects
public class Handler {

    // initialize list of ALL GameObjects
    LinkedList<GameObject> object = new LinkedList<GameObject>();

    // synchronize all GameObjects in object list with game loop
    public void tick(){
       for(int i = 0; i < object.size(); i++){
           GameObject tempObject = object.get(i);
           tempObject.tick();
       }
    }

    // renders all GameObjects in object list
    public void render(Graphics g){
        LinkedList<GameObject> nonTrails = new LinkedList<GameObject>();
        GameObject tempObject;

        // this for loop filters out all objects that are not Trails
        for(int i = 0; i < object.size(); i++) {
            tempObject = object.get(i);
            if(tempObject.id != GameObjectID.Trail){
                nonTrails.add(object.get(i));
            }
            // they are rendered here
            tempObject.render(g);
        }
        for(int i = 0; i < nonTrails.size(); i++){
                tempObject = object.get(i);
            // all remaining objects are rendered here on top of the Trails
            tempObject.render(g);
        }
        // empties list of non Trail objects
        nonTrails.clear();
    }

    // adds objects to object list
    public void addObject(GameObject object){
        this.object.add(object);
    }

    // removes objects from object list
    public void removeObject(GameObject object){
        this.object.remove(object);
    }

    //clears enemies from the stage
    public void clearEnemies(){
        for (int i = 0; i < object.size(); i++) {
            if (object.get(i).getId() == GameObjectID.Enemy ||
                    object.get(i).getId() == GameObjectID.SmartEnemy) {
                object.remove(i);
                i = -1;
            }
        }
    }

}
