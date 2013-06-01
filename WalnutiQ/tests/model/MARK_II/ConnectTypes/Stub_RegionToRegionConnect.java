package model.MARK_II.ConnectTypes;

import model.MARK_II.Stub_Region;

public interface Stub_RegionToRegionConnect {
    public abstract void connect(Stub_Region childRegion, Stub_Region parentRegion,
	    int numberOfColumnsToOverlapAlongXAxisOfRegion,
	    int numberOfColumnsToOverlapAlongYAxisOfRegion);
}
