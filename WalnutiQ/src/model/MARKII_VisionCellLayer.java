package model;

/**
 * A data structure that represents the layer of cells within the retina.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK II | April 4, 2013
 */
public class MARKII_VisionCellLayer extends MARKII_SensorCellLayer {

    public MARKII_VisionCellLayer(int numberOfVisionCellsAlongXAxis,
	    int numberOfVisionCellsAlongYAxis) {
	sensorCellLayer = new MARKII_VisionCell[numberOfVisionCellsAlongXAxis][numberOfVisionCellsAlongYAxis];
    }
}
