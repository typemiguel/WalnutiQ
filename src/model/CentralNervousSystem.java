package model;

import model.MARK_I.Neocortex;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version June 5, 2013
 */
public class CentralNervousSystem {
    private Brain brain;
    // private SpinalCord spinalCord;

    public CentralNervousSystem(Neocortex neocortex, LateralGeniculateNucleus LGN) {
	this.brain = new Brain(neocortex, LGN);
	// this.spinalCord = null;
    }

    public Brain getBrain() {
	return this.brain;
    }
}
