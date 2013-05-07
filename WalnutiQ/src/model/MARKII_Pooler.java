package model;

/**
 * Abstract class extended by SpatialPooler and TemporalPooler class.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK II | April 28, 2013
 */
public abstract class MARKII_Pooler {
    protected MARKII_Region region;

    // TODO: how to make this code parallel

    public boolean changeRegion(MARKII_Region newRegion)
    {
	if (newRegion != null) {
	    this.region = newRegion;
	    return true;
	} else {
	    return false;
	}
    }
}
