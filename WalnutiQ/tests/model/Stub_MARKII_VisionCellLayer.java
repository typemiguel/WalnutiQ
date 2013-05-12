package model;

public class Stub_MARKII_VisionCellLayer extends Stub_MARKII_SensorCellLayer {

    public Stub_MARKII_VisionCellLayer(int numberOfVisionCellsAlongXAxis,
	    int numberOfVisionCellsAlongYAxis) {
	sensorCellLayer = new Stub_MARKII_VisionCell[numberOfVisionCellsAlongXAxis][numberOfVisionCellsAlongYAxis];
    }
}
