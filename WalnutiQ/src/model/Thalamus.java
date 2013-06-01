package model;

import model.MARK_II.Region;

public class Thalamus {
    private LateralGeniculateNucleus LGN;

    public Thalamus(Region region) {
	this.LGN = new LateralGeniculateNucleus(region);
    }

    public LateralGeniculateNucleus getLGN() {
	return this.LGN;
    }
}
