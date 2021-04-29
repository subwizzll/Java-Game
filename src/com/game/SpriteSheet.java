package com.game;

import java.awt.image.BufferedImage;

// this class loads sprite sheet and provides a method for returning sprites via index
public class SpriteSheet {

    private BufferedImage image;
    private int cellSize = 16;

    // constructor
    public SpriteSheet(BufferedImage image){
        this.image = image;
    }

    // gets image by index
    public BufferedImage getImage(int row, int col, int width, int height) {
        BufferedImage image = this.image.getSubimage(
                (col * cellSize) - cellSize,(row * cellSize) - cellSize, width, height);
        return image;
    }

    public int getCellSize() {
        return cellSize;
    }
}
