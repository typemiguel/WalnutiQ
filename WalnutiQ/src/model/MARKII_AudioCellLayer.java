package model;

/**
 * A data structure that represents the layer of cells within the cochlea.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK II | April 4, 2013
 */
public class MARKII_AudioCellLayer extends MARKII_SensorCellLayer {

    public MARKII_AudioCellLayer(int numberOfAudioCellsAlongXAxis,
	    int numberOfAudioCellsAlongYAxis) {
	sensorCellLayer = new MARKII_AudioCell[numberOfAudioCellsAlongXAxis][numberOfAudioCellsAlongYAxis];
    }
}
