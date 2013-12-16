package model;

import model.MARK_I.Neocortex;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version June 5, 2013
 */
public class CerebralCortex {
    private Neocortex neocortex;
    // private Paleocortex paleocortex;
    // private Archicortex archicortex;

    public CerebralCortex(Neocortex neocortex) {
	this.neocortex = neocortex;
	// this.paleocortex = null;
	// this.archicortex = null;
    }

    public Neocortex getNeocortex() {
	return this.neocortex;
    }
}
