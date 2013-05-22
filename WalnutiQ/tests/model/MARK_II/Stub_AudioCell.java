package model.MARK_II;

public class Stub_AudioCell extends Stub_SensorCell {

    public Stub_AudioCell() {
	super();
    }

    // TODO: other frequency, pitch, thresholds

    @Override
    public String toString() {
	StringBuilder stringBuilder = new StringBuilder();
	stringBuilder.append("\n==================");
	stringBuilder.append("\n--AudioCell Info--");
	stringBuilder.append("\nisActive: ");
	stringBuilder.append(getActiveState());
	stringBuilder.append("\n==================");
	String sensorCellInformation = stringBuilder.toString();
	return sensorCellInformation;
    }
}
