package model.MARK_II;

public class Stub_VisionCell extends Stub_SensorCell {

    public Stub_VisionCell() {
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
