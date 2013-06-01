package model.MARK_II.ConnectTypes;

import model.MARK_II.Region;

public interface RegionToRegionConnect {
    public abstract void connect(Region childRegion, Region parentRegion,
	    int numberOfColumnsToOverlapAlongXAxisOfRegion,
	    int numberOfColumnsToOverlapAlongYAxisOfRegion);
}
