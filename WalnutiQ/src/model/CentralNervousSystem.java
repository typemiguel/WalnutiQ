package model;

import model.MARK_II.ConnectionType;
import model.MARK_II.Region;

public class CentralNervousSystem {
    private Brain brain;
    private SpinalCord spinalCord;

    public CentralNervousSystem(Region rootRegion,
	    ConnectionType neocortexRegionToNeocortexRegion,
	    int numberOfVisionCellsAlongXAxis, int numberOfVisionCellsAlongYAxis) {
	this.brain = new Brain(rootRegion, neocortexRegionToNeocortexRegion,
		numberOfVisionCellsAlongXAxis, numberOfVisionCellsAlongYAxis);
	this.spinalCord = null;
    }

    public Brain getBrain() {
	return this.brain;
    }
}
