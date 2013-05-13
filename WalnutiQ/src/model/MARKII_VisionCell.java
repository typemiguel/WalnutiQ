package model;

/**
 * A data structure that represents a single vision cell within the retina.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK II | April 4, 2013
 */
public class MARKII_VisionCell extends MARKII_SensorCell {

    public MARKII_VisionCell()
    {
	super();
    }
    // TODO: other Red Green Blue detectors and different grey scale detectors

    @Override
    public String toString() {
	StringBuilder stringBuilder = new StringBuilder();
	stringBuilder.append("\n===========================");
	stringBuilder.append("\n------VisionCell Info------");
	stringBuilder.append("\n          (x, y): ");
	stringBuilder.append("(" + super.getX() + ", " + super.getY() + ")");
	stringBuilder.append("\n        isActive: ");
	stringBuilder.append(getActiveState());
	stringBuilder.append("\n===========================");
	String sensorCellInformation = stringBuilder.toString();
	return sensorCellInformation;
    }
}
