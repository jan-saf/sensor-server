package com.jts.tools;

import java.util.Random;

import processing.core.PGraphics;

public class Shuffle {

    /**
     * Naive method which takes semirandom parts of picture and moves it to semirandom position
     *
     * x-axis defines the x position of duplicated frame relative to the original position
     * y-axis defines the y position of duplicated frame relative to the original position
     * z-axis defines the width and height of the duplicated frame (always a rectangle)
     */
    public static void shuffle(PGraphics graphics, float x, float y, float z) {
        graphics.loadPixels();

        int x1 = new Random().nextInt(graphics.width);
        int y1 = new Random().nextInt(graphics.height);
        int x2 = (int) (x1 + x*10);
        int y2 = (int) (y1 + y*10);

        int w = (int) (z*20);
        int h = (int) (z*20);

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                int origin = (x1 + y1 * graphics.width) + j * graphics.width + i;
                int destination = (x2 + y2 * graphics.width) + j * graphics.width + i;
                if (origin < graphics.pixels.length && destination < graphics.pixels.length) {
                    graphics.pixels[destination] = graphics.pixels[origin];
                }
            }
        }

        graphics.updatePixels();
    }
}
