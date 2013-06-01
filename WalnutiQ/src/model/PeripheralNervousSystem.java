package model;

import model.MARK_II.VisionCellLayer;

public class PeripheralNervousSystem {
    private SomaticNervousSystem SNS;
    private AutonomicNervousSystem ANS;

    public PeripheralNervousSystem(VisionCellLayer retina) {
	this.SNS = new SomaticNervousSystem(retina);
	this.ANS = null;
    }

    public SomaticNervousSystem getSNS() {
	return this.SNS;
    }
}
