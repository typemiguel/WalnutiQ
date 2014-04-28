package model.MARK_II.movement;

import java.awt.Point;

public class ViewPosition {
    private Point retinaPosition;
    private int distanceBetweenImageAndRetina;

    public ViewPosition(Point retinaPosition, int distanceBetweenImageAndRetina) {
	this.retinaPosition = retinaPosition;
	this.distanceBetweenImageAndRetina = distanceBetweenImageAndRetina;
    }

    public Point getRetinaPosition() {
        return retinaPosition;
    }

    public void setRetinaPosition(Point retinaPosition) {
        this.retinaPosition = retinaPosition;
    }

    public int getDistanceBetweenImageAndRetina() {
        return distanceBetweenImageAndRetina;
    }

    public void setDistanceBetweenImageAndRetina(int distanceBetweenImageAndRetina) {
        this.distanceBetweenImageAndRetina = distanceBetweenImageAndRetina;
    }
}
