package model;

import model.MARK_II.ConnectionType;
import model.MARK_II.Region;

import model.MARK_II.Neocortex;

public class CerebralCortex {
    private Neocortex neocortex;
    private Paleocortex paleocortex;
    private Archicortex archicortex;

    public CerebralCortex(Region rootRegion, ConnectionType neocortexRegionToNeocortexRegion) {
	this.neocortex = new Neocortex(rootRegion, neocortexRegionToNeocortexRegion);
	this.paleocortex = null;
	this.archicortex = null;
    }

    public Neocortex getNeocortex() {
	return this.neocortex;
    }
}
