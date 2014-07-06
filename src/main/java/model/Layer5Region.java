package model;

import model.MARK_II.Neuron;
import model.MARK_II.Region;
import model.util.BoundingBox;
import model.util.Point3D;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version July 5, 2014
 */
public class Layer5Region extends Region {

    public Layer5Region(String biologicalName, int numberOfColumnsAlongRowsDimension,
                        int numberOfColumnsAlongColumnsDimension, int cellsPerColumn,
                        double percentMinimumOverlapScore, int desiredLocalActivity) {
        super(biologicalName, numberOfColumnsAlongRowsDimension, numberOfColumnsAlongColumnsDimension, cellsPerColumn,
                percentMinimumOverlapScore, desiredLocalActivity);
    }

    public Point3D getMotorOutput(BoundingBox boundingBox) {
        // NOTE: x = width = super.columns[0].length AND y = height = super.columns.length
        // split the Region x & y axis halfway along the x axis & only consider x less than this midpoint when
        // calculating averageRetinaX and averageRetinaY
        double regionWidth = super.columns[0].length / 2;
        double regionHeight = super.columns.length;

        double averageRegionXMultipliedByThisNumberEqualsNewBoundingBoxXPosition = boundingBox.getWidth() / regionWidth;
        double averageRegionYMultipliedByThisNumberEqualsNewBoundingBoxYPosition = boundingBox.getHeight() / regionHeight;
        double averageRegionZMultipliedByThisNumberEqualsNewBoundingBoxZPosition = boundingBox.getHeight() / regionHeight;

        double averageRegionX = 0;
        double averageRegionY = 0;
        int numberOfActiveAndPredictiveNeuronsForXY = 0;

        double averageRegionZ = 0;
        int numberOfActiveAndPredictiveNeuronsForZ = 0;


        for (int row = 0; row < super.columns.length; row++) {
            for (int column = 0; column < super.columns[0].length; column++) {
                for (Neuron neuron : super.columns[row][column].getNeurons()) {
                    if (neuron.getActiveState() == true || neuron.getPredictingState() == true) {
                        if (column < regionWidth) {
                            averageRegionX += column;
                            averageRegionY += row;
                            numberOfActiveAndPredictiveNeuronsForXY++;
                        } else {
                            // column >= regionWidth so use row variable to calculate averageRetinaZ
                            averageRegionZ += row;
                            numberOfActiveAndPredictiveNeuronsForZ++;
                        }
                    }
                }
            }
        }

        averageRegionX = averageRegionX / numberOfActiveAndPredictiveNeuronsForXY;
        averageRegionY = averageRegionY / numberOfActiveAndPredictiveNeuronsForXY;
        averageRegionZ = averageRegionZ / numberOfActiveAndPredictiveNeuronsForZ;

        double newBoundingBoxXPosition = averageRegionX * averageRegionXMultipliedByThisNumberEqualsNewBoundingBoxXPosition;
        double newBoundingBoxYPosition = averageRegionY * averageRegionYMultipliedByThisNumberEqualsNewBoundingBoxYPosition;
        double newBoundingBoxZPosition = averageRegionZ * averageRegionZMultipliedByThisNumberEqualsNewBoundingBoxZPosition;

        return new Point3D(newBoundingBoxXPosition, newBoundingBoxYPosition, newBoundingBoxZPosition);
    }
}
