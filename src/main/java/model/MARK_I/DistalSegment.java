package model.MARK_I;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version April 25, 2014
 */
public class DistalSegment extends Segment {
    /** same as sequenceSegment */
    private boolean predictsFeedForwardInputOnNextTimeStep;

    private boolean wasActive;

    public DistalSegment() {
	super();
	this.predictsFeedForwardInputOnNextTimeStep = false;
	this.wasActive = false;
    }

    public void nextTimeStep() {
	this.wasActive = super.isActive;
	super.isActive = false;

	this.wasActive = this.isActive;
	this.isActive = false;
    }

    public boolean getSequenceStatePredictsFeedFowardInputOnNextStep() {
	return this.predictsFeedForwardInputOnNextTimeStep;
    }

    public void setPreviousActiveState(boolean wasActive) {
	this.wasActive = wasActive;
    }

    public boolean getPreviousActiveStateLearnState() {
	return this.wasActive;
    }

    public int getNumberOfPreviousActiveSynapses() {
	int numberOfPreviousActiveSynapses = 0;
	for (Synapse synapse : this.synapses) {
	    if (synapse.isConnected()
		    && synapse.getConnectedCell().getPreviousActiveState()) {
		numberOfPreviousActiveSynapses++;
	    }
	}
	return numberOfPreviousActiveSynapses;
    }
}
