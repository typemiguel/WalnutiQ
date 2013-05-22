package model.MARK_II;

/**
 * A data structure that represents a single vision cell within the retina.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK II | April 4, 2013
 */
public class VisionCell extends SensorCell {

    public VisionCell() {
	super();
    }

    // TODO: other Red Green Blue detectors and different grey scale detectors

    @Override
    public String toString() {
	StringBuilder stringBuilder = new StringBuilder();
	stringBuilder.append("\n===================");
	stringBuilder.append("\n--VisionCell Info--");
	stringBuilder.append("\nisActive: ");
	stringBuilder.append(super.getActiveState());
	stringBuilder.append("\n===================");
	String sensorCellInformation = stringBuilder.toString();
	return sensorCellInformation;
    }
}
