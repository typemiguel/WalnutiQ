package model.MARK_II;

/**
 * A functor class that provides the implementation for connecting a lower
 * region to a higher region with overlaping proximal segments.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK II | May 16, 2013
 */
public class OverlapConnectFunctor {

    public void connect(SensorCellLayer inputLayer, Region outputRegion) {
	if (inputLayer == null) {
	    throw new IllegalArgumentException(
		    "parentRegion in connect method cannot be null");
	} else if (outputRegion == null) {
	    throw new IllegalArgumentException(
		    "chilRegion in connect method cannot be null");
	}
    }
}
