package model;

import model.MARK_II.VisionCellLayer;

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

    public LateralGeniculateNucleus(int numberOfVisionCellsAlongXAxis,
	    int numberOfVisionCellsAlongYAxis) {
	// how should desiredLocalActivity be calculated
	this.region = new Region("LGN", numberOfVisionCellsAlongXAxis * 5, numberOfVisionCellsAlongYAxis * 5, 1, 2, 3);
    }

    public Region getRegion() {
	return this.region;
    }
}
