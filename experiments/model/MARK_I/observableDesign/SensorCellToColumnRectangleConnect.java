package model.MARK_I.observableDesign;

import model.MARK_I.Cell;
import model.MARK_I.SensorCell;
import model.MARK_I.Synapse;
import model.MARK_I.Column;

public class SensorCellToColumnRectangleConnect {

    public void connect(SensorCell sensorCell, Column column) {
	column.getProximalSegment().addSynapse(
		new Synapse<Cell>(sensorCell, 0, 0));
    }
}