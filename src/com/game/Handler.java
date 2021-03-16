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
        LinkedList<GameObject> nonTrails = new LinkedList<GameObject>();
        GameObject tempObject;
        for(int i = 0; i < object.size(); i++) {        // this for loop filters out all objects that are not Trails
            tempObject = object.get(i);
            if(tempObject.id != GameObjectID.Trail){
                nonTrails.add(object.get(i));
            }
            tempObject.render(g);                       // they are rendered here
        }
        for(int i = 0; i < nonTrails.size(); i++){
            tempObject = object.get(i);
            tempObject.render(g);                       // all remaining objects are rendered here on top of the Trails
        }
        nonTrails.clear();                              // empties list of non Trail objects
    }

    public void addObject(GameObject object){           // adds objects to object list
        this.object.add(object);
    }

    public void removeObject(GameObject object){        // removes objects from object list
        this.object.remove(object);
    }

    public void clearEnemies(){ //TODO clear all enemies without explicitly specifying them
        for (int i = 0; i < object.size(); i++) {
            if (object.get(i).getId() == GameObjectID.Enemy ||
                    object.get(i).getId() == GameObjectID.SmartEnemy) {
                object.remove(i);
                i = -1;
            }
        }
    }

    public void removePlayer(){
        for (int i = 0; i < object.size(); i++) {
            if (object.get(i).getId() == GameObjectID.Player) {
                object.remove(i);
            }
        }
    }
}
