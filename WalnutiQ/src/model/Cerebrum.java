package model;

import model.MARK_II.ConnectionType;
import model.MARK_II.Region;

public class Cerebrum {
    private CerebralCortex cerebralCortex;
    private BasalGanglia basalGanglia;
    private Hippocampus hippocampus;
    private Amygdala amygdala;

    public Cerebrum(Region rootRegion,
	    ConnectionType neocortexRegionToNeocortexRegion) {
	this.cerebralCortex = new CerebralCortex(rootRegion,
		neocortexRegionToNeocortexRegion);
	this.basalGanglia = null;
	this.hippocampus = null;
	this.amygdala = null;
    }

    public CerebralCortex getCerebralCortex() {
	return this.cerebralCortex;
    }
}
