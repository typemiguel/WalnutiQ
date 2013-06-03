package model;

import model.MARK_II.Region;

public class Thalamus {
    private LateralGeniculateNucleus LGN;

    public Thalamus(LateralGeniculateNucleus LGN) {
	this.LGN = LGN;
    }

    public LateralGeniculateNucleus getLGN() {
	return this.LGN;
    }
}
