package model.MARK_I.connectTypes;

import model.MARK_I.Region;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version June 7, 2013
 */
public abstract class AbstractRegionToRegionConnect {
    /**
     * Place a description of your method here.
     *
     * @param childRegion
     * @param parentRegion
     * @param numberOfColumnsToOverlapAlongXAxisOfRegion
     * @param numberOfColumnsToOverlapAlongYAxisOfRegion
     */
    public abstract void connect(Region childRegion, Region parentRegion,
	    int numberOfColumnsToOverlapAlongXAxisOfRegion,
	    int numberOfColumnsToOverlapAlongYAxisOfRegion);

    void checkParameters(Region childRegion, Region parentRegion,
	    int numberOfColumnsToOverlapAlongXAxisOfRegion,
	    int numberOfColumnsToOverlapAlongYAxisOfRegion) {
	if (parentRegion == null) {
	    throw new IllegalArgumentException(
		    "parentRegion in connect method cannot be null");
	} else if (childRegion == null) {
	    throw new IllegalArgumentException(
		    "childRegion in connect method cannot be null");
	} else if (childRegion.getXAxisLength() <= parentRegion
		.getXAxisLength()
		|| childRegion.getYAxisLength() <= parentRegion
			.getYAxisLength()) {
	    throw new IllegalArgumentException(
		    "childRegion in connect method cannot be smaller in X or Y "
			    + "dimentions than the parentRegion");
	} else if (numberOfColumnsToOverlapAlongXAxisOfRegion < 0) {
	    throw new IllegalArgumentException(
		    "numberOfColumnsToOverlapAlongXAxisOfRegion in connect method cannot be < 0");
	} else if (numberOfColumnsToOverlapAlongYAxisOfRegion < 0) {
	    throw new IllegalArgumentException(
		    "numberOfColumnsToOverlapAlongYAxisOfRegion in connect method cannot be < 0");
	}
    }
}
