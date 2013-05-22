package model.MARK_II;

/**
 * A data structure that represents the layer of cells within the cochlea.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK II | April 4, 2013
 */
public class AudioCellLayer extends SensorCellLayer {

    public AudioCellLayer(int numberOfAudioCellsAlongXAxis,
	    int numberOfAudioCellsAlongYAxis) {
	super.sensorCellLayer = new AudioCell[numberOfAudioCellsAlongXAxis][numberOfAudioCellsAlongYAxis];
    }
}
