package model;

/**
 * "Relay station" for all sensory information(except smell) which
 * is conveyed to the cortex.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version June 5, 2013
 */
public class Thalamus {
    private LateralGeniculateNucleus LGN;

    public Thalamus(LateralGeniculateNucleus LGN) {
	this.LGN = LGN;
    }

    public LateralGeniculateNucleus getLGN() {
	return this.LGN;
    }
}
