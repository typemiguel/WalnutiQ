package model;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version June 5, 2013
 */
public class PeripheralNervousSystem {
    private SomaticNervousSystem SNS;

    /*
     * Works below the level of consciousness controlling
     * heart rate, digestion, respiratory rate, ...
     * Support from actual experiments: TODO
     */
    // private AutonomicNervousSystem ANS;

    public PeripheralNervousSystem(Retine retine) {
	this.SNS = new SomaticNervousSystem(retine);
	// this.ANS = null;
    }

    public SomaticNervousSystem getSNS() {
	return this.SNS;
    }
}
