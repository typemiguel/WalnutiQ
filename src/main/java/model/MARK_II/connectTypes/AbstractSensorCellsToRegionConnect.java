package model.MARK_II.connectTypes;

import model.MARK_II.Region;
import model.MARK_II.SensorCell;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version June 7, 2013
 */
public abstract class AbstractSensorCellsToRegionConnect {
    public abstract void connect(SensorCell[][] sensorCells, Region region,
                                 int numberOfColumnsToOverlapAlongXAxisOfSensorCells,
                                 int numberOfColumnsToOverlapAlongYAxisOfSensorCells);

    void checkParameters(SensorCell[][] sensorCells, Region region,
                         int numberOfColumnsToOverlapAlongXAxisOfSensorCells,
                         int numberOfColumnsToOverlapAlongYAxisOfSensorCells) {
        if (region == null) {
            throw new IllegalArgumentException(
                    "region in SensorCellsToRegionRectangleConnect class"
                            + "connect method cannot be null");
        } else if (sensorCells == null) {
            throw new IllegalArgumentException(
                    "sensorCells in SensorCellsToRegionRectangleConnect class"
                            + "connect method cannot be null");
        } else if (sensorCells.length <= region.getNumberOfRowsAlongRegionYAxis()
                || sensorCells[0].length <= region.getNumberOfColumnsAlongRegionXAxis()) {
            throw new IllegalArgumentException(
                    "sensorCells in connect method cannot be smaller in X or Y "
                            + "dimentions than the region");
        } else if (numberOfColumnsToOverlapAlongXAxisOfSensorCells < 0) {
            throw new IllegalArgumentException(
                    "numberOfColumnsToOverlapAlongXAxisOfSensorCells in connect method cannot be < 0");
        } else if (numberOfColumnsToOverlapAlongYAxisOfSensorCells < 0) {
            throw new IllegalArgumentException(
                    "numberOfColumnsToOverlapAlongYAxisOfSensorCells in connect method cannot be < 0");
        }
    }
}
