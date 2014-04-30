package model.MARK_II.connectTypes;

import model.MARK_II.Cell;
import model.MARK_II.Column;
import model.MARK_II.Region;
import model.MARK_II.SensorCell;
import model.MARK_II.Synapse;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version June 13, 2013
 */
public class SensorCellsToRegionRectangleConnect extends
	AbstractSensorCellsToRegionConnect {
    @Override
    public void connect(SensorCell[][] sensorCells, Region region,
	    int numberOfColumnsToOverlapAlongXAxisOfSensorCells,
	    int numberOfColumnsToOverlapAlongYAxisOfSensorCells) {

	super.checkParameters(sensorCells, region,
		numberOfColumnsToOverlapAlongXAxisOfSensorCells,
		numberOfColumnsToOverlapAlongYAxisOfSensorCells);

	Column[][] regionColumns = region.getColumns();
	int regionXAxisLength = regionColumns[0].length;
	int regionYAxisLength = regionColumns.length;

	int sensorCellsXAxisLength = sensorCells[0].length;
	int sensorCellsYAxisLength = sensorCells.length;

	int connectingRectangleXAxisLength = Math
		.round((sensorCellsXAxisLength
			+ numberOfColumnsToOverlapAlongXAxisOfSensorCells
			* regionXAxisLength - numberOfColumnsToOverlapAlongXAxisOfSensorCells)
			/ regionXAxisLength);
	int connectingRectangleYAxisLength = Math
		.round((sensorCellsYAxisLength
			+ numberOfColumnsToOverlapAlongYAxisOfSensorCells
			* regionYAxisLength - numberOfColumnsToOverlapAlongYAxisOfSensorCells)
			/ regionYAxisLength);

	int shiftAmountXAxis = connectingRectangleXAxisLength
		- numberOfColumnsToOverlapAlongXAxisOfSensorCells;
	int shiftAmountYAxis = connectingRectangleYAxisLength
		- numberOfColumnsToOverlapAlongYAxisOfSensorCells;
	for (int columnX = 0; columnX < regionXAxisLength; columnX++) {
	    for (int columnY = 0; columnY < regionYAxisLength; columnY++) {

		int xStart = columnX * shiftAmountXAxis;
		int yStart = columnY * shiftAmountYAxis;

		int xEnd = xStart + connectingRectangleXAxisLength;
		int yEnd = yStart + connectingRectangleYAxisLength;

		Column column = regionColumns[columnX][columnY];

		for (int sensorCellX = xStart; sensorCellX < xEnd; sensorCellX++) {
		    for (int sensorCellY = yStart; sensorCellY < yEnd; sensorCellY++) {
			// # of Synapses connected/add to this proximal Segment
			// = connectingRectangleXAxisLength *
			// connectingRectangleYAxisLength
			column.getProximalSegment().addSynapse(
				new Synapse<Cell>(
					sensorCells[sensorCellX][sensorCellY],
					sensorCellX, sensorCellY));
		    }
		}
	    }
	}
    }
}
