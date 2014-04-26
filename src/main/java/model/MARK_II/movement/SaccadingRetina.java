package model.MARK_II.movement;

import model.MARK_II.VisionCell;

/**
 *  Put all logic for updating SaccadingRetina elsewhere. In reality
 *  a retina does not update itself.
 */
public class SaccadingRetina {
    private VisionCell[][] visionCells;

    public SaccadingRetina(int numberOfVisionCellsAlongYAxis,
	    int numberOfVisionCellsAlongXAxis) {
	this.visionCells = new VisionCell[numberOfVisionCellsAlongYAxis][numberOfVisionCellsAlongXAxis];

	for (int currentRow = 0; currentRow < numberOfVisionCellsAlongYAxis; currentRow++) {
	    for (int currentColumn = 0; currentColumn < numberOfVisionCellsAlongXAxis; currentColumn++) {
		this.visionCells[currentRow][currentColumn] = new VisionCell();
	    }
	}
    }

    public VisionCell[][] getVisionCells() {
	return this.visionCells;
    }
}
