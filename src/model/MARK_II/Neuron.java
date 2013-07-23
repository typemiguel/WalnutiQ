package model.MARK_II;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

/**
 * A Neuron is active or non-active, predicting or not predicting at each time
 * step. This Neuron only keeps track of these 2 states during the current time
 * step and the previous time step. A Neuron also has a set of DistalSegments.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @author Michael Cogswell (cogswell@vt.edu)
 * @version MARK II | July 22, 2013
 */
public class Neuron extends Cell {
    private boolean isPredicting;
    private boolean wasPredicting;
    private Set<DistalSegment> distalSegments;

    public Neuron() {
	super(); // Initializes isActive state
	this.isPredicting = false;
	this.wasPredicting = false;
	this.distalSegments = new HashSet<DistalSegment>();
    }

    /**
     * Return a Segment that was active in the previous time step. If more than
     * one Segment was was active, sequence segments are considered, and finally
     * if more than one sequence segment that was active in the previous time
     * step, the segment with the most activity is returned.
     *
     * @return A Segment that was active in the previous time step; otherwise null.
     */
    public DistalSegment getBestPreviousActiveSegment() {
	List<DistalSegment> previousActiveSegments = new ArrayList<DistalSegment>();
	List<DistalSegment> previousActiveSequenceSegments = new ArrayList<DistalSegment>();

	for (DistalSegment distalSegment : this.distalSegments) {
	    if (distalSegment.getPreviousActiveState()
		    && distalSegment.getSequenceState()) {
		previousActiveSegments.add(distalSegment);
		previousActiveSequenceSegments.add(distalSegment);
	    } else if (distalSegment.getPreviousActiveState()) {
		previousActiveSegments.add(distalSegment);
	    }
	}

	// the most active sequence Segment at t-1
	if (previousActiveSequenceSegments.size() > 1) {
	    DistalSegment mostActiveSequenceSegment = previousActiveSequenceSegments.get(0);
	    int maximumPreviousActiveSynapses = 0;
	    for (DistalSegment sequenceSegment : previousActiveSequenceSegments) {
		int previousActiveSynapses = sequenceSegment
			.getNumberOfPreviousActiveSynapses();
		if (maximumPreviousActiveSynapses < previousActiveSynapses) {
		    maximumPreviousActiveSynapses = previousActiveSynapses;
		    mostActiveSequenceSegment = sequenceSegment;
		}
	    }
	    return mostActiveSequenceSegment;
	} else if (previousActiveSequenceSegments.size() == 1) {
	    return previousActiveSequenceSegments.get(0);
	} else {
	    // get the most active nonsequence Segment at t-1
	    if (previousActiveSegments.size() > 1) {
		DistalSegment mostActiveSegment = previousActiveSegments.get(0);
		int maximumPreviousActiveSynapses = 0;
		for (DistalSegment segment : previousActiveSegments) {
		    int previousActiveSynapses = segment
			    .getNumberOfPreviousActiveSynapses();
		    if (maximumPreviousActiveSynapses < previousActiveSynapses) {
			maximumPreviousActiveSynapses = previousActiveSynapses;
			mostActiveSegment = segment;
		    }
		}
		return mostActiveSegment;
	    } else if (previousActiveSegments.size() == 1) {
		return previousActiveSegments.get(0);
	    } else {
		return null;
	    }
	}
    }

    /**
     * Sets current active and predicting state to previous active and
     * predicting state. Then resets current active and predicting state to
     * false.
     */
    public void nextTimeStep() {
	this.wasActive = this.isActive;
	this.wasPredicting = this.isPredicting;
	this.isActive = false;
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

    public Set<DistalSegment> getDistalSegments() {
	return this.distalSegments;
    }

    public void addDistalSegment(DistalSegment distalSegment) {
	if (distalSegment == null) {
	    throw new IllegalArgumentException(
		    "distalSegment in neuron class method addDistalSegment cannot be null");
	}
	this.distalSegments.add(distalSegment);
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
	stringBuilder.append(this.isPredicting);
	stringBuilder.append("\n      wasPredicting: ");
	stringBuilder.append(this.wasPredicting);
	stringBuilder.append("\n# of DistalSegments: ");
	stringBuilder.append(this.distalSegments.size());
	stringBuilder.append("\n===========================");
	String NeuronInformation = stringBuilder.toString();
	return NeuronInformation;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result
		+ ((distalSegments == null) ? 0 : distalSegments.hashCode());
	result = prime * result + (isPredicting ? 1231 : 1237);
	result = prime * result + (wasPredicting ? 1231 : 1237);
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (!super.equals(obj))
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Neuron other = (Neuron) obj;
	if (distalSegments == null) {
	    if (other.distalSegments != null)
		return false;
	} else if (!distalSegments.equals(other.distalSegments))
	    return false;
	if (isPredicting != other.isPredicting)
	    return false;
	if (wasPredicting != other.wasPredicting)
	    return false;
	return true;
    }
}
