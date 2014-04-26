package model.MARK_II;

import java.util.List;
import java.util.ArrayList;

/**
 * Simulates a cortical pyramidal cell.
 *
 * A real Neuron has 3 different states? 1) 3 spikes per second 2) 10+ spikes
 * per second 3) 0 spikes per second Support: https://db.tt/QFqA4Dta
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @author Michael Cogswell (cogswell@vt.edu)
 * @version July 22, 2013
 */
public class Neuron extends Cell {
    private boolean isPredicting;
    private boolean wasPredicting;

    private List<DistalSegment> distalSegments;

    public Neuron() {
	super();
	this.isPredicting = false;
	this.wasPredicting = false;

	this.distalSegments = new ArrayList<DistalSegment>();
    }

    public void nextTimeStep() {
	this.wasActive = super.isActive;
	super.isActive = false;

	this.wasPredicting = this.isPredicting;
	this.isPredicting = false;
    }

    public boolean getPredictingState() {
	return this.isPredicting;
    }

    public void setPredictingState(boolean predictingState) {
	this.isPredicting = predictingState;
    }

    public boolean getPreviousPredictingState() {
	return this.wasPredicting;
    }

    public void setPreviousPredictingState(boolean previousPredictingState) {
	this.wasPredicting = previousPredictingState;
    }

    /**
     * For the given column c cell i, return a segment index such that
     * segmentActive(s,t, state) is true. If multiple segments are active,
     * sequence segments are given preference. Otherwise, segments with most
     * activity are given preference.
     *
     * Return a DistalSegment that was active in the previous time step. If more
     * than one DistalSegment was active, sequence segments are considered. If
     * more than one sequence segment was active, the segment with the most
     * activity is returned.
     */
    public DistalSegment getBestPreviousActiveSegment() {
	List<DistalSegment> previousActiveSegments = new ArrayList<DistalSegment>();
	List<DistalSegment> previousActiveSequenceSegment = new ArrayList<DistalSegment>();

	for (DistalSegment distalSegment : this.distalSegments) {
	    if (distalSegment.getPreviousActiveStateLearnState()
		    && distalSegment
			    .getSequenceStatePredictsFeedFowardInputOnNextStep()) {
		previousActiveSegments.add(distalSegment);
		previousActiveSequenceSegment.add(distalSegment);
	    } else if (distalSegment.getPreviousActiveStateLearnState()) {
		previousActiveSegments.add(distalSegment);
	    }
	}

	if (previousActiveSegments.size() == 0) {
	    return this.getSegmentWithMostActivity(this.distalSegments);

	} else if (previousActiveSegments.size() == 1) {
	    return previousActiveSegments.get(0);

	} else { // previousActiveSegments.size() > 1

	    if (previousActiveSequenceSegment.size() == 0) {
		return this.getSegmentWithMostActivity(this.distalSegments);
	    } else if (previousActiveSequenceSegment.size() == 1) {
		return previousActiveSequenceSegment.get(0);
	    } else { // previousActiveSequenceSegments.size() > 1
		return this
			.getSegmentWithMostActivity(previousActiveSequenceSegment);
	    }
	}
    }

    DistalSegment getSegmentWithMostActivity(
	    List<DistalSegment> whichSegmentsToCheck) {
	DistalSegment mostActiveDistalSegment = this.distalSegments.get(0);

	int maxPreviousActiveSynapses = 0;
	for (DistalSegment distalSegment : whichSegmentsToCheck) {
	    int previousActiveSynapses = distalSegment
		    .getNumberOfPreviousActiveSynapses();
	    if (previousActiveSynapses > maxPreviousActiveSynapses) {
		maxPreviousActiveSynapses = previousActiveSynapses;
		mostActiveDistalSegment = distalSegment;
	    }
	}
	return mostActiveDistalSegment;
    }

    public DistalSegment getBestActiveSegment() {
	DistalSegment mostActiveDistalSegment = this.distalSegments.get(0);

	int maxActiveSynapses = 0;
	for (DistalSegment distalSegment : this.distalSegments) {
	    int previousActiveSynapses = distalSegment
		    .getNumberOfActiveSynapses();
	    if (previousActiveSynapses > maxActiveSynapses) {
		maxActiveSynapses = previousActiveSynapses;
		mostActiveDistalSegment = distalSegment;
	    }
	}
	return mostActiveDistalSegment;
    }

    public List<DistalSegment> getDistalSegments() {
	return this.distalSegments;
    }

    @Override
    public String toString() {
	StringBuilder stringBuilder = new StringBuilder();
	stringBuilder.append("\n===========================");
	stringBuilder.append("\n--------Neuron Info--------");
	stringBuilder.append("\n           isActive: ");
	stringBuilder.append(this.isActive);
	stringBuilder.append("\n          wasActive: ");
	stringBuilder.append(this.wasActive);
	stringBuilder.append("\n       isPredicting: ");
	stringBuilder.append("\n===========================");
	String NeuronInformation = stringBuilder.toString();
	return NeuronInformation;
    }
}
