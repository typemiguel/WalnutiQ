package model.MARK_II;

import java.io.Serializable;

public abstract class Stub_SensorCellLayer implements Serializable {
    public Stub_SensorCell[][] sensorCellLayer;

    public Stub_SensorCell[][] getSensorCellLayer() {
	return this.sensorCellLayer;
    }
}
