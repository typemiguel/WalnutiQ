package model.util;

import java.awt.Point;

public class BoundingBox {
    private double width, height, depth;

    public BoundingBox(double width, double height, double depth) {
	this.width = width;
	this.height = height;
	this.depth = depth;
    }

    public boolean contains(Point positionXY, double positionZ) {
	double x = positionXY.getX();
	double y = positionXY.getY();
	double z = positionZ;
	if (x < 0 || y < 0 || z < 0 || x > this.width || y > this.height
		|| z > this.depth) {
	    return false;
	} else {
	    return true;
	}
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
