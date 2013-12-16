package model;

import model.MARK_I.Region;

/**
 * Input into LGN: activity of Cells within Retina.
 *
 * Output of LGN: activity of Neurons from this Region.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK I | June 5, 2013
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
