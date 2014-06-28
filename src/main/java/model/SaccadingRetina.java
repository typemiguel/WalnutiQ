package model;

import java.awt.geom.Point2D;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Apr 28, 2014
 */
public class SaccadingRetina extends Retina {
    private Point2D position;
    private double distanceBetweenImageAndRetina;

    public SaccadingRetina(int numberOfVisionCellsAlongYAxis,
                           int numberOfVisionCellsAlongXAxis, Point2D retinaPosition,
                           double distanceBetweenImageAndRetina) {
        super(numberOfVisionCellsAlongYAxis, numberOfVisionCellsAlongXAxis);

        this.position = retinaPosition;
        this.distanceBetweenImageAndRetina = distanceBetweenImageAndRetina;
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D retinaPosition) {
        this.position = retinaPosition;
    }

    public double getDistanceBetweenImageAndRetina() {
        return distanceBetweenImageAndRetina;
    }

    public void setDistanceBetweenImageAndRetina(
            double boxZ) {
        this.distanceBetweenImageAndRetina = boxZ;
    }

    public int[][] getDoubleIntArrayRepresentationOfVisionCells() {
        int numberOfRows = super.visionCells.length;
        int numberOfColumns = super.visionCells[0].length;
        int[][] retina = new int[numberOfRows][numberOfColumns];

        for (int row = 0; row < numberOfColumns; row++) {
            for (int column = 0; column < numberOfRows; column++) {
                if (super.visionCells[row][column].getActiveState()) {
                    retina[row][column] = 1;
                } else {
                    retina[row][column] = 0;
                }
            }
        }

        return retina;
    }

}
