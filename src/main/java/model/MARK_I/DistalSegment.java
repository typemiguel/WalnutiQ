package main.java.model.MARK_I;

public class DistalSegment extends Segment {
    /** same as sequenceSegment */
    private boolean predictsFeedForwardInputOnNextTimeStep;

    public DistalSegment() {
	super();
	this.predictsFeedForwardInputOnNextTimeStep = false;
    }

    public boolean getSequenceStatePredictsFeedFowardInputOnNextStep() {
	return this.predictsFeedForwardInputOnNextTimeStep;
    }

    public boolean getPreviousActiveStateLearnState() {
	return false;
    }
}
