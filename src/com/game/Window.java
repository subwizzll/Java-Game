package com.game;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;

// class to initialize main game window
public class Window extends Canvas{


    @Serial
    private static final long serialVersionUID = 5070093916032438121L;

    // window constructor sets parameters
    public Window(float width, float height, String title, Game game) {

        JFrame frame = new JFrame(title);

        frame.setPreferredSize(new Dimension((int)width,(int)height));
        frame.setMaximumSize(new Dimension((int)width,(int)height));
        frame.setMinimumSize(new Dimension((int)width,(int)height));

        // enables closing of game thread window with X button
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(game);
        frame.setVisible(true);
        // launches Game
        game.start();
    }
}
