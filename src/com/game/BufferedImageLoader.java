package com.game;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BufferedImageLoader {              // this class provides the method needed to load our sprite sheet

    private BufferedImage image;

    public BufferedImage loadImage(String path) throws IOException {

        image = ImageIO.read(getClass().getResource(path));
        return image;
    }

}
