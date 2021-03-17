package com.game;

import java.awt.image.BufferedImage;

public class SpriteSheet {                  // this class loads sprite sheet and provides a method for returning
                                            // sprites via index

    private BufferedImage image;
    private int cellSize = 16;

    public SpriteSheet(BufferedImage image){
        this.image = image;
    }

    public BufferedImage getImage(int row, int col, int width, int height) {
        BufferedImage image = this.image.getSubimage(
                (col * cellSize) - cellSize,(row * cellSize) - cellSize, width, height);
        return image;
    }

    public BufferedImage getImage(double col, double row, int width, int height) {
        BufferedImage image = this.image.getSubimage(
                (int)((col * cellSize) - cellSize),(int)((row * cellSize) - cellSize), width, height);
        return image;
    }

    public int getCellSize() {
        return cellSize;
    }
}
