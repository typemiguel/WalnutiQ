package model;

/**
 * A data structure that represents a single audio cell within the cochlea.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK II | April 4, 2013
 */
public class MARKII_AudioCell extends MARKII_SensorCell {

    public MARKII_AudioCell()
    {
	super();
    }
    // TODO: other frequency, pitch, thresholds

    @Override
    public String toString() {
	StringBuilder stringBuilder = new StringBuilder();
	stringBuilder.append("\n===========================");
	stringBuilder.append("\n------AudioCell Info------");
	stringBuilder.append("\n          (x, y): ");
	stringBuilder.append("(" + super.getX() + ", " + super.getY() + ")");
	stringBuilder.append("\n        isActive: ");
	stringBuilder.append(getActiveState());
	stringBuilder.append("\n===========================");
	String sensorCellInformation = stringBuilder.toString();
	return sensorCellInformation;
    }
}
