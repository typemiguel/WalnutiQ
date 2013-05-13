package model;

public class Stub_MARKII_VisionCell extends Stub_MARKII_SensorCell {

    public Stub_MARKII_VisionCell()
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
