package model.MARK_II.ConnectTypes;

import model.MARK_II.Stub_Cell;
import model.MARK_II.Stub_Column;
import model.MARK_II.Stub_Region;
import model.MARK_II.Stub_SensorCell;
import model.MARK_II.Stub_SensorCellLayer;
import model.MARK_II.Stub_Synapse;

public class Stub_SensorCellLayerToRegionRectangleConnect implements Stub_SensoryCellLayerToRegionConnect {
    @Override
    public void connect(Stub_SensorCellLayer sensorCellLayer, Stub_Region region,
	    int numberOfColumnsToOverlapAlongXAxisOfSensorCellLayer,
	    int numberOfColumnsToOverlapAlongYAxisOfSensorCellLayer) {
	if (region == null) {
	    throw new IllegalArgumentException(
		    "region in connect method cannot be null");
	} else if (sensorCellLayer == null) {
	    throw new IllegalArgumentException(
		    "sensorCellLayer in connect method cannot be null");
	}
	Stub_Column[][] regionColumns = region.getColumns();
	int regionXAxisLength = regionColumns.length; // = 8
	int regionYAxisLength = regionColumns[0].length; // = 8

	Stub_SensorCell[][] sensorCells = sensorCellLayer.getSensorCellLayer();
	int sensorCellsXAxisLength = sensorCells.length; // = 66
	int sensorCellsYAxisLength = sensorCells[0].length; // = 66

	// TODO: add missing exceptions for connectingRectangle dimension >= 8

	// TODO: view formula derivation and details at www.walnutiq.com/...
	int connectingRectangleXAxisLength = Math
		.round((sensorCellsXAxisLength
			+ numberOfColumnsToOverlapAlongXAxisOfSensorCellLayer
			* regionXAxisLength - numberOfColumnsToOverlapAlongXAxisOfSensorCellLayer)
			/ regionXAxisLength); // = 10
	int connectingRectangleYAxisLength = Math
		.round((sensorCellsYAxisLength
			+ numberOfColumnsToOverlapAlongYAxisOfSensorCellLayer
			* regionYAxisLength - numberOfColumnsToOverlapAlongYAxisOfSensorCellLayer)
			/ regionYAxisLength); // = 10

	int shiftAmountXAxis = connectingRectangleXAxisLength - numberOfColumnsToOverlapAlongXAxisOfSensorCellLayer; // = 10 - 2
	int shiftAmountYAxis = connectingRectangleYAxisLength - numberOfColumnsToOverlapAlongYAxisOfSensorCellLayer; // = 10 - 2
	for (int columnX = 0; columnX < (regionXAxisLength-shiftAmountXAxis); columnX++) {
	    for (int columnY = 0; columnY < (regionYAxisLength-shiftAmountYAxis); columnY++) {

		// xStart = 0, 8, 16, 24, 32, 40, 48, 56
		// yStart = 0, 8, 16, 24, 32, 40, 48, 56
		int xStart = columnX * shiftAmountXAxis;
		int yStart = columnY * shiftAmountYAxis;

		// xEnd = 10, 18, 26, 34, 42, 50, 58, 66
		// yEnd = 10, 18, 26, 34, 42, 50, 58, 66
		int xEnd = xStart + connectingRectangleXAxisLength;
		int yEnd = yStart + connectingRectangleYAxisLength;

		Stub_Column column = regionColumns[columnX][columnY];

		for (int sensorCellX = xStart; sensorCellX < xEnd; sensorCellX++) {
		    for (int sensorCellY = yStart; sensorCellY < yEnd; sensorCellY++) {
			// # of synapses connected/add to this proximal segment
			// = connectingRectangleXAxisLength *
			// connectingRectangleYAxisLength
			column.getProximalSegment().addSynapse(new Stub_Synapse<Stub_Cell>(sensorCells[sensorCellX][sensorCellY], sensorCellX, sensorCellY));
		    }
		}
	    }
	}
    }
}
