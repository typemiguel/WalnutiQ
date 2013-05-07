package model;

public class Stub_MARKII_AudioCellLayer extends MARKII_SensorCellLayer {

    public Stub_MARKII_AudioCellLayer(int numberOfAudioCellsAlongXAxis,
	    int numberOfAudioCellsAlongYAxis) {
	sensorCellLayer = new MARKII_AudioCell[numberOfAudioCellsAlongXAxis][numberOfAudioCellsAlongYAxis];
    }
}
