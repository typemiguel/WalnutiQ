package model.MARK_II.ConnectTypes;

import model.MARK_II.SensorCell;
import model.MARK_II.Region;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK II | June 7, 2013
 */
public interface SensorCellsToRegionConnect {
    public abstract void connect(SensorCell[][] sensorCells, Region region,
	    int numberOfColumnsToOverlapAlongXAxisOfSensorCells,
	    int numberOfColumnsToOverlapAlongYAxisOfSensorCells);

}
