package model.MARK_II;

public class Stub_AudioCellLayer extends Stub_SensorCellLayer {

    public Stub_AudioCellLayer(int numberOfAudioCellsAlongXAxis,
	    int numberOfAudioCellsAlongYAxis) {
	sensorCellLayer = new Stub_AudioCell[numberOfAudioCellsAlongXAxis][numberOfAudioCellsAlongYAxis];
    }
}
