package model;

import model.MARK_II.Neocortex;

public class Cerebrum {
    private CerebralCortex cerebralCortex;
    // private BasalGanglia basalGanglia;
    // private Hippocampus hippocampus;
    // private Amygdala amygdala;

    public Cerebrum(Neocortex neocortex) {
	this.cerebralCortex = new CerebralCortex(neocortex);
	// this.basalGanglia = null;
	// this.hippocampus = null;
	// this.amygdala = null;
    }

    public CerebralCortex getCerebralCortex() {
	return this.cerebralCortex;
    }
}
