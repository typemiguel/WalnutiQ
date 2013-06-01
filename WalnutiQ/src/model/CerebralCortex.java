package model;

import model.MARK_II.Neocortex;

public class CerebralCortex {
    private Neocortex neocortex;
    private Paleocortex paleocortex;
    private Archicortex archicortex;

    public CerebralCortex(Neocortex neocortex) {
	this.neocortex = neocortex;
	this.paleocortex = null;
	this.archicortex = null;
    }

    public Neocortex getNeocortex() {
	return this.neocortex;
    }
}
