package model;

import model.MARK_II.Region;

/**
 *  Input into LGN = activity of cells within retina.
 *  Output into V1(leaf region of neocortex tree) = activity of neurons within
 *  this region due to it's unique connection to the retina.
 *
 *  @author Quinn Liu (quinnliu@vt.edu)
 *  @version MARK II | May 18, 2013
 */
public class LateralGeniculateNucleus {
    private Region region;

    public LateralGeniculateNucleus(Region region) {
	// how should desiredLocalActivity be calculated
	this.region = region;
    }

    public Region getRegion() {
	return this.region;
    }
}
