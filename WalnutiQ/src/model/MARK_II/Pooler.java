package model.MARK_II;

/**
 * Abstract class extended by SpatialPooler.java and TemporalPooler.java class.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK II | April 28, 2013
 */
public abstract class Pooler {
    protected Region region;

    // TODO: how to make this code parallel

    public void changeRegion(Region newRegion)
    {
	if (newRegion == null) {
	    throw new IllegalArgumentException(
		    "Region in Pooler method changeRegion cannot be null");
	}
	this.region = newRegion;
    }
}
