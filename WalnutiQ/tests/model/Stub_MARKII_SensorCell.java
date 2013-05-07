package model;

import java.io.Serializable;

public abstract class Stub_MARKII_SensorCell extends MARKII_AbstractCell implements Serializable {
    public Stub_MARKII_SensorCell() {
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
