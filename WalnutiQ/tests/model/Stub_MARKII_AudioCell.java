package model;

public class Stub_MARKII_AudioCell extends Stub_MARKII_SensorCell {

    public Stub_MARKII_AudioCell()
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
