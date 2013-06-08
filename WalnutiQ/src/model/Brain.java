package model;

import model.MARK_II.Neocortex;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version June 5, 2013
 */
public class Brain {
    private Cerebrum cerebrum;
    private Thalamus thalamus;
    // private Hindbrain hindbrain;
    // private Midbrain midbrain;
    // private ReticularFormation reticularFormation;
    // private Hypothalamus hypothalamus;

    public Brain(Neocortex neocortex, LateralGeniculateNucleus LGN) {
	this.cerebrum = new Cerebrum(neocortex);
	this.thalamus = new Thalamus(LGN);
	// this.hindbrain = null;
	// this.midbrain = null;
	// this.reticularFormation = null;
	// this.hypothalamus = null;
    }

    public Cerebrum getCerebrum() {
	return this.cerebrum;
    }

    public Thalamus getThalamus() {
	return this.thalamus;
    }
}
