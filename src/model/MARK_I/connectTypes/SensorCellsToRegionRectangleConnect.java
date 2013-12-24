package model.MARK_I.connectTypes;

import model.MARK_I.Cell;
import model.MARK_I.Column;
import model.MARK_I.Region;
import model.MARK_I.SensorCell;
import model.MARK_I.Synapse;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version June 13, 2013
 */
public class SensorCellsToRegionRectangleConnect implements SensorCellsToRegionConnect {
    @Override
    public void connect(SensorCell[][] sensorCells, Region region,
	    int numberOfColumnsToOverlapAlongXAxisOfSensorCells,
	    int numberOfColumnsToOverlapAlongYAxisOfSensorCells) {
	if (region == null) {
	    throw new IllegalArgumentException(
		    "region in SensorCellsToRegionRectangleConnect class" +
		    "connect method cannot be null");
	} else if (sensorCells == null) {
	    throw new IllegalArgumentException(
		    "sensorCells in SensorCellsToRegionRectangleConnect class" +
		    "connect method cannot be null");
	} else if (sensorCells.length <= region
		.getXAxisLength()
		|| sensorCells[0].length <= region
			.getYAxisLength()) {
	    throw new IllegalArgumentException(
		    "sensorCells in connect method cannot be smaller in X or Y " +
		    "dimentions than the region");
	} else if (numberOfColumnsToOverlapAlongXAxisOfSensorCells < 0) {
	    throw new IllegalArgumentException(
		    "numberOfColumnsToOverlapAlongXAxisOfSensorCells in connect method cannot be < 0");
	} else if (numberOfColumnsToOverlapAlongYAxisOfSensorCells < 0) {
	    throw new IllegalArgumentException(
		    "numberOfColumnsToOverlapAlongYAxisOfSensorCells in connect method cannot be < 0");
	}
	Column[][] regionColumns = region.getColumns();
	int regionXAxisLength = regionColumns.length; // = 8
	int regionYAxisLength = regionColumns[0].length; // = 8

	int sensorCellsXAxisLength = sensorCells.length; // = 66
	int sensorCellsYAxisLength = sensorCells[0].length; // = 66

	// TODO: add missing exceptions for connectingRectangle dimension >= 8

	// TODO: view formula derivation and details at www.walnutiq.com/...
	int connectingRectangleXAxisLength = Math
		.round((sensorCellsXAxisLength
			+ numberOfColumnsToOverlapAlongXAxisOfSensorCells
			* regionXAxisLength - numberOfColumnsToOverlapAlongXAxisOfSensorCells)
			/ regionXAxisLength); // = 10
	int connectingRectangleYAxisLength = Math
		.round((sensorCellsYAxisLength
			+ numberOfColumnsToOverlapAlongYAxisOfSensorCells
			* regionYAxisLength - numberOfColumnsToOverlapAlongYAxisOfSensorCells)
			/ regionYAxisLength); // = 10

	int shiftAmountXAxis = connectingRectangleXAxisLength - numberOfColumnsToOverlapAlongXAxisOfSensorCells; // = 10 - 2
	int shiftAmountYAxis = connectingRectangleYAxisLength - numberOfColumnsToOverlapAlongYAxisOfSensorCells; // = 10 - 2
	for (int columnX = 0; columnX < regionXAxisLength; columnX++) {
	    for (int columnY = 0; columnY < regionYAxisLength; columnY++) {

		// xStart = 0, 8, 16, 24, 32, 40, 48, 56
		// yStart = 0, 8, 16, 24, 32, 40, 48, 56
		int xStart = columnX * shiftAmountXAxis;
		int yStart = columnY * shiftAmountYAxis;

		// xEnd = 10, 18, 26, 34, 42, 50, 58, 66
		// yEnd = 10, 18, 26, 34, 42, 50, 58, 66
		int xEnd = xStart + connectingRectangleXAxisLength;
		int yEnd = yStart + connectingRectangleYAxisLength;

		Column column = regionColumns[columnX][columnY];

		for (int sensorCellX = xStart; sensorCellX < xEnd; sensorCellX++) {
		    for (int sensorCellY = yStart; sensorCellY < yEnd; sensorCellY++) {
			// # of Synapses connected/add to this proximal Segment
			// = connectingRectangleXAxisLength *
			// connectingRectangleYAxisLength
			column.getProximalSegment().addSynapse(new Synapse<Cell>(sensorCells[sensorCellX][sensorCellY], sensorCellX, sensorCellY));
		    }
		}
	    }
	}
    }
}
