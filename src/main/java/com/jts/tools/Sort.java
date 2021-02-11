package com.jts.tools;

import java.util.Arrays;
import java.util.Random;

import lombok.extern.slf4j.Slf4j;
import processing.core.PGraphics;

@Slf4j
public class Sort {

    public static void sortHorizontally(Direction direction, PGraphics graphics) {
        sortNPixelsHorizontally(direction, graphics.width, graphics, 0);
    }

    public static void sortVertically(Direction direction, PGraphics graphics) {
        sortNPixelsVertically(direction, graphics.height, graphics, 0);
    }

    public static void sortNPixelsHorizontally(Direction direction, int N, PGraphics graphics, float noise) {
        log.debug("Sorting Horizontally");
        graphics.loadPixels();
        int length = 0;
        while (length < graphics.pixels.length) {
            int noised = N;
            if ((int) noise > 0) {
                noised = N + new Random().nextInt((int) Math.abs(noise));
            }
            sortCycle(graphics.pixels, length, length + noised, direction);
            length += noise;
        }
        graphics.updatePixels();
    }

    public static void sortNPixelsVertically(Direction direction, int N, PGraphics graphics, float noise) {
        log.debug("Sorting Vertically");
        graphics.loadPixels();

        int[] transposed = new int[graphics.pixels.length];

        for (int i = 0; i < graphics.pixels.length; i++) {
            int index = ((i * graphics.width) % graphics.pixels.length) + ((i * graphics.width) / graphics.pixels.length);
            transposed[i] = graphics.pixels[index];
        }

        int length = 0;
        while (length < transposed.length) {
            int noised = N;
            if ((int) noise > 0) {
                noised = N + new Random().nextInt((int) Math.abs(noise));
            }
            sortCycle(transposed, length, length + noised, direction);
            length += noised;
        }

        for (int i = 0; i < graphics.pixels.length; i++) {
            int index = ((i * graphics.width) % graphics.pixels.length) + ((i * graphics.width) / graphics.pixels.length);
            graphics.pixels[index] = transposed[i];
        }

        graphics.updatePixels();
    }

    private static void sortCycle(int[] array, int from, int to, Direction direction) {
        if (array.length < to) {
            to = array.length;
        }
        Arrays.sort(array, from, to);
        if (direction == Direction.DESCENDING) {
            reverse(array, from, to);
        }
    }

    private static void reverse(int[] array, int from, int to) {
        for (int i = 0; i < Math.ceil((to - from) / 2.0); i++) {
            int tmp = array[from + i];
            array[from + i] = array[to - (i + 1)];
            array[to - (i + 1)] = tmp;
        }
    }
}
