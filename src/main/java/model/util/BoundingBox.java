package model.util;

import java.awt.*;

public class BoundingBox {
    private double width, height, depth;

    public BoundingBox(double width, double height, double depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }

    public double getDepth() {
        return this.depth;
    }
}
