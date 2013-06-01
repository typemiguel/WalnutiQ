package model.MARK_II.ConnectTypes;

import model.MARK_II.Stub_Region;
import model.MARK_II.Stub_SensorCellLayer;

public interface Stub_SensoryCellLayerToRegionConnect {
    public abstract void connect(Stub_SensorCellLayer sensorCellLayer, Stub_Region region,
	    int numberOfColumnsToOverlapAlongXAxisOfSensorCellLayer,
	    int numberOfColumnsToOverlapAlongYAxisOfSensorCellLayer);
}
