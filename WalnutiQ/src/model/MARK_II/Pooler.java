package model.MARK_II;

/**
 * Abstract class extended by SpatialPooler and TemporalPooler class.
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
		    "region in Pooler method changeRegion cannot be null");
	}
	this.region = newRegion;
    }
}
