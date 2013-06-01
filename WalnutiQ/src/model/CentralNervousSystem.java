package model;

import model.MARK_II.Neocortex;
import model.MARK_II.Region;

public class CentralNervousSystem {
    private Brain brain;
    private SpinalCord spinalCord;

    public CentralNervousSystem(Neocortex neocortex, Region region) {
	this.brain = new Brain(neocortex, region);
	this.spinalCord = null;
    }

    public Brain getBrain() {
	return this.brain;
    }
}
