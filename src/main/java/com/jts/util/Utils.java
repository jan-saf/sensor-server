package com.jts.util;

import javax.imageio.ImageIO;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import processing.core.PGraphics;

public class Utils {

    public static BufferedImage getBufferedImage(String name) {
        InputStream stream = Utils.class.getClassLoader().getResourceAsStream(name);
        try {
            return ImageIO.read(stream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't read image " + name + ", terminating.");
        }
    }

    public static void drawToGraphics(PGraphics graphics, BufferedImage image, int width, int height) {
        graphics.beginDraw();
        Graphics2D g2d = (Graphics2D) graphics.getNative();
        g2d.drawImage(image, 0, 0, width, height, null);
        graphics.endDraw();
    }
}
