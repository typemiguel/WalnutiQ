package model;

import java.io.Serializable;

/**
 * A data structure that represents a single input bit from an external sensor
 * source.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @author Michael Cogswell (cogswell@vt.edu)
 * @version MARK II | April 4, 2013
 */
public abstract class MARKII_SensorCell extends MARKII_AbstractCell implements Serializable {
    public MARKII_SensorCell() {
	super();
    }

    @Override
    public String toString() {
	StringBuilder stringBuilder = new StringBuilder();
	stringBuilder.append("\n===========================");
	stringBuilder.append("\n------SensorCell Info------");
	stringBuilder.append("\n        isActive: ");
	stringBuilder.append(getActiveState());
	stringBuilder.append("\n===========================");
	String sensorCellInformation = stringBuilder.toString();
	return sensorCellInformation;
    }
}
