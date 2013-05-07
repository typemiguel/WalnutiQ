package model;

public class Stub_MARKII_VisionCellLayer extends MARKII_SensorCellLayer {

    public Stub_MARKII_VisionCellLayer(int numberOfVisionCellsAlongXAxis,
	    int numberOfVisionCellsAlongYAxis) {
	sensorCellLayer = new MARKII_VisionCell[numberOfVisionCellsAlongXAxis][numberOfVisionCellsAlongYAxis];
    }
}
