package model;

public class PeripheralNervousSystem {
    private SomaticNervousSystem SNS;
    private AutonomicNervousSystem ANS;

    public PeripheralNervousSystem(int numberOfVisionCellsAlongXAxis,
	    int numberOfVisionCellsAlongYAxis) {
	this.SNS = new SomaticNervousSystem(numberOfVisionCellsAlongXAxis, numberOfVisionCellsAlongYAxis);
	this.ANS = null;
    }

    public SomaticNervousSystem getSNS() {
	return this.SNS;
    }
}
