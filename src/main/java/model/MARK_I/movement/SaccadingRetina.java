package main.java.model.MARK_I.movement;

import main.java.model.MARK_I.VisionCell;

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
}
