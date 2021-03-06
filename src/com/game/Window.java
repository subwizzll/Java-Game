package com.game;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;

public class Window extends Canvas{                             // class to initialize main game window


    @Serial
    private static final long serialVersionUID = 5070093916032438121L;

    public Window(float width, float height, String title, Game game) { // window constructor sets parameters

        JFrame frame = new JFrame(title);

        frame.setPreferredSize(new Dimension((int)width,(int)height));
        frame.setMaximumSize(new Dimension((int)width,(int)height));
        frame.setMinimumSize(new Dimension((int)width,(int)height));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // enables closing of game thread window with X button
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(game);
        frame.setVisible(true);
        game.start();                                           // launches Game class (main)
    }
}
