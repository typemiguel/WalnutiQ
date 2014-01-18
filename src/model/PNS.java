package model;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version June 5, 2013
 */
public class PNS {
    private SNS SNS;

    /*
     * Works below the level of consciousness controlling
     * heart rate, digestion, respiratory rate, ...
     * Support from actual experiments: TODO
     */
    // private AutonomicNervousSystem ANS;

    public PNS(Retine retine) {
	this.SNS = new SNS(retine);
	// this.ANS = null;
    }

    public SNS getSNS() {
	return this.SNS;
    }
}
