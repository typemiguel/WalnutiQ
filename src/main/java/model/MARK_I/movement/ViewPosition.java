package main.java.model.MARK_I.movement;

import java.awt.Point;

public class ViewPosition {
    private Point retinaPosition;
    private int distanceBetweenImageAndRetina;

    public ViewPosition(Point retinaPosition, int distanceBetweenImageAndRetina) {
	this.retinaPosition = retinaPosition;
	this.distanceBetweenImageAndRetina = distanceBetweenImageAndRetina;
    }
}
