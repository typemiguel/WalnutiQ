package model.MARK_II;

public class Stub_VisionCellLayer extends Stub_SensorCellLayer {

    public Stub_VisionCellLayer(int numberOfVisionCellsAlongXAxis,
	    int numberOfVisionCellsAlongYAxis) {
	sensorCellLayer = new Stub_VisionCell[numberOfVisionCellsAlongXAxis][numberOfVisionCellsAlongYAxis];
    }
}
