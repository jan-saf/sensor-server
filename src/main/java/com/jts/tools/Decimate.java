package com.jts.tools;

import processing.core.PGraphics;

public class Decimate {

    /**
     * Probably super naive method to pixelate picture provided in PGraphics parameter
     */
    public static void decimate(PGraphics graphics, int N) {
        graphics.loadPixels();
        for (int row = 0; row < graphics.height; row += N) {
            for (int column = 0; column < graphics.width; column += N) {

                int clr = graphics.pixels[row * graphics.width + column];

                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        if ((row + j) * graphics.width + column + i < graphics.pixels.length) {
                            clr = graphics.lerpColor(clr, graphics.pixels[(row + j) * graphics.width + column + i], 1 / ((float) (i * N + j) + 1));
                        }
                    }
                }

                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        if ((row + j) * graphics.width + column + i < graphics.pixels.length) {
                            graphics.pixels[(row + j) * graphics.width + column + i] = clr;
                        }
                    }
                }
            }
        }
        graphics.updatePixels();
    }

    public static void decimate(PGraphics graphics, int X, int Y) {
        graphics.loadPixels();
        for (int row = 0; row < graphics.height; row += X) {
            for (int column = 0; column < graphics.width; column += Y) {

                int clr = graphics.pixels[row * graphics.width + column];

                for (int i = 0; i < X; i++) {
                    for (int j = 0; j < Y; j++) {
                        if ((row + j) * graphics.width + column + i < graphics.pixels.length) {
                            clr = graphics.lerpColor(clr, graphics.pixels[(row + j) * graphics.width + column + i], 1 / ((float) (i * X + j) + 1));
                        }
                    }
                }

                for (int i = 0; i < X; i++) {
                    for (int j = 0; j < Y; j++) {
                        if ((row + j) * graphics.width + column + i < graphics.pixels.length) {
                            graphics.pixels[(row + j) * graphics.width + column + i] = clr;
                        }
                    }
                }
            }
        }
        graphics.updatePixels();
    }

}
