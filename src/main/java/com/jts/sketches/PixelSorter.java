package com.jts.sketches;

import com.jts.data.Operation;
import com.jts.data.SensorData;
import com.jts.data.Sensors;
import com.jts.tools.Decimate;
import com.jts.tools.Direction;
import com.jts.tools.Shuffle;
import com.jts.tools.Sort;
import com.jts.util.Utils;

import java.awt.image.BufferedImage;

import lombok.extern.slf4j.Slf4j;
import processing.core.PApplet;
import processing.core.PGraphics;

@Slf4j
public class PixelSorter extends PApplet {

    BufferedImage image;
    PGraphics graphics;
    int wdth;
    int hght;

    @Override
    public void settings() {
        image = Utils.getBufferedImage(args[0]);
        wdth = image.getWidth();
        hght = image.getHeight();
        while (hght > 1000) {
            wdth *= 0.9;
            hght *= 0.9;
        }
        size(wdth, hght);
        log.info("Width: {}, Height: {}", image.getWidth(), image.getHeight());
        log.info("Press 's' to save currently displayed picture");
    }

    @Override
    public void setup() {
        graphics = createGraphics(wdth, hght);
        redrawImage();
    }

    @Override
    public void draw() {
        image(graphics, 0, 0);
        for (SensorData data : Sensors.getSensors().values()) {
            float x = data.getX();
            float y = data.getY();
            float z = data.getZ();
            Operation op = data.getOperation();

            switch (op) {
                case SORT:
                    log.debug("Sorting");
                    sort(x, y, z);
                    break;
                case DECIMATE:
                    log.debug("Decimating");
                    decimate(x, y);
                    break;
                case SHUFFLE:
                    log.debug("Shuffling");
                    shuffle(x, y, z);
                    break;
                case RESTORE:
                    log.debug("Restoring");
                    redrawImage();
                    break;
                case NONE:
                default:
                    break;
            }
        }
    }

    private void shuffle(float x, float y, float z) {
        int X = x < 0 ? (int) -x : (int) x;
        int Y = y < 0 ? (int) -y : (int) y;
        int Z = z < 0 ? (int) -z : (int) z;
        Shuffle.shuffle(graphics, X, Y, Z);
    }

    private void decimate(float x, float y) {
        int N = x < 0 ? (int) -x : (int) x;
        int D = y < 0 ? (int) -y : (int) y;
        if (N > 1 && D > 1) {
            Decimate.decimate(graphics, N, D);
        }
    }

    private void sort(float x, float y, float z) {
        int X = x < 0 ? (int) -x : (int) x;
        int Y = y < 0 ? (int) -y : (int) y;
        int Z = z < 0 ? (int) -z : (int) z;
        X *= 10;
        Y *= 10;
        Z *= 10;
        if (x > 10 || x < -10 || y > 10 || y < -10) {
            if (x > y) {
                Direction direction = (x > 0) ? Direction.ASCENDING : Direction.DESCENDING;
                Sort.sortNPixelsHorizontally(direction, X, graphics, Z);
            } else {
                Direction direction = (y > 0) ? Direction.ASCENDING : Direction.DESCENDING;
                Sort.sortNPixelsVertically(direction, Y, graphics, Z);
            }
        }
    }

    private void printSensorData(float x, float y, float z, Operation op) {
        textSize(20);
        text("x: " + (x) + "\ny: " + (y) + "\nz: " + z + "\noperation: " + op.toString(), 30, 50);
    }

    @Override
    public void keyPressed() {
        if (key == 's') {
            StringBuilder builder = new StringBuilder();
            builder.append(month());
            builder.append(".");
            builder.append(day());
            builder.append(".");
            builder.append(hour());
            builder.append(".");
            builder.append(minute());
            builder.append(".");
            builder.append(second());
            builder.append(".");
            builder.append("png");
            graphics.save(builder.toString());
        }
    }

    private void redrawImage() {
        Utils.drawToGraphics(graphics, image, wdth, hght);
        image(graphics, 0, 0);
    }
}
