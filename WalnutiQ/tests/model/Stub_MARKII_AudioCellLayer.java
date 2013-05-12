package model;

public class Stub_MARKII_AudioCellLayer extends Stub_MARKII_SensorCellLayer {

    public Stub_MARKII_AudioCellLayer(int numberOfAudioCellsAlongXAxis,
	    int numberOfAudioCellsAlongYAxis) {
	sensorCellLayer = new Stub_MARKII_AudioCell[numberOfAudioCellsAlongXAxis][numberOfAudioCellsAlongYAxis];
    }
}
