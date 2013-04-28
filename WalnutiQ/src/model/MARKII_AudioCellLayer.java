package model;

public class MARKII_AudioCellLayer extends MARKII_SensorCellLayer {

    public MARKII_AudioCellLayer(int numberOfAudioCellsAlongXAxis,
	    int numberOfAudioCellsAlongYAxis) {
	sensorCellLayer = new MARKII_AudioCell[numberOfAudioCellsAlongXAxis][numberOfAudioCellsAlongYAxis];
    }
}
