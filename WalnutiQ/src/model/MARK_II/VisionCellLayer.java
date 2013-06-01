package model.MARK_II;

/**
 * A data structure that represents the layer of cells within the retina.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK II | April 4, 2013
 */
public class VisionCellLayer extends SensorCellLayer {

    public VisionCellLayer(int numberOfVisionCellsAlongXAxis,
	    int numberOfVisionCellsAlongYAxis) {
	super.sensorCellLayer = new VisionCell[numberOfVisionCellsAlongXAxis][numberOfVisionCellsAlongYAxis];
    }
}
