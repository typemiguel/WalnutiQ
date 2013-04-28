package model;

public class MARKII_VisionCellLayer extends MARKII_SensorCellLayer {

    public MARKII_VisionCellLayer(int numberOfVisionCellsAlongXAxis,
	    int numberOfVisionCellsAlongYAxis) {
	sensorCellLayer = new MARKII_VisionCell[numberOfVisionCellsAlongXAxis][numberOfVisionCellsAlongYAxis];
    }
}
