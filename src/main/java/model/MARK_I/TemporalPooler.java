package main.java.model.MARK_I;

import java.util.Set;

/**
 * Idea behind temporal pooling: SDRs that occur adjacent in time probably have
 * a common underlying cause. Several times a second your eyes fixate on a
 * different part of the image causing a complete change in input. Despite this
 * changing input your perception is stable. Somewhere in higher regions there
 * must be neurons that remain active.
 *
 * Input into TemporalPooler: activeColumns of a Region at time t computed by
 * SpatialPooler
 *
 * Output from TemporalPooler: boolean OR of the actual active??? and predictive
 * state for each neuron in the set of activeColumns of a Region??? how are
 * synapses permances decremented/incremented??? which neurons & which segments
 * are affected???
 *
 * Sequence memory: learning sequences of SDRs??? Uses the columns to represent
 * inputs uniquely in different contexts???
 *
 * Temporal Pooler: forms a stable representation over sequences???
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version April 22, 2014
 */
public class TemporalPooler extends Pooler {
    private SpatialPooler spatialPooler;

    public TemporalPooler(SpatialPooler spatialPooler) {
	this.spatialPooler = spatialPooler;
	super.region = spatialPooler.getRegion();
    }

    public void performTemporalPoolingOnRegion() {
	Set<Column> activeColumns = this.spatialPooler.getActiveColumns();
	this.phaseOne(activeColumns);

	// phase 2
	this.phaseTwo(activeColumns);

	// phase 3
	this.phaseThree(activeColumns);
    }

    /**
     * Compute the activeState for each Neuron in activeColumns. Then in each
     * active Column a learning Neuron is chosen.
     */
    public void phaseOne(Set<Column> activeColumns) {
	for (Column column : activeColumns) {

	    boolean bottomUpPredicted = false;
	    boolean learningCellChosen = false;

	    Neuron[] neurons = column.getNeurons();
	    for (int i = 0; i < neurons.length; i++) {

		if (neurons[i].getPreviousActiveState() == true) {
		    DistalSegment bestSegment = neurons[i]
			    .getBestPreviousActiveSegment();

		    if (bestSegment != null
			    && bestSegment
				    .getSequenceStatePredictsFeedFowardInputOnNextStep()) {
			bottomUpPredicted = true;
			neurons[i].setActiveState(true);

			if (bestSegment.getPreviousActiveStateLearnState()) {
			    learningCellChosen = true;
			    column.setLearningNeuronPosition(i);
			}
		    }
		}
	    }

	    if (bottomUpPredicted == false) {
		for (Neuron neuron : column.getNeurons()) {
		    neuron.setActiveState(true);
		}
	    }

	    if (learningCellChosen == false) {
		int bestNeuronIndex = this.getBestMatchingNeuronIndex(column);
		column.setLearningNeuronPosition(bestNeuronIndex);

		// sUpdate = getSegmentActiveSynapses(c, i, s, t-1, true);
		// sUpdate.sequenceSegment = true;
		// segmentUpdateList.add(sUpdate);
	    }
	}
    }

    /**
     * Calculated the predictive state for each Neuron. A Neuron's
     * predictiveState will be true if 1 or more distal segments becomes active.
     */
    public void phaseTwo(Set<Column> activeColumns) {
	for (Column column : activeColumns) {
	    for (Neuron neuron : column.getNeurons()) {
		for (Segment segment : neuron.getDistalSegments()) {
		    if (segment.getActiveState()) {
			neuron.setPredictingState(true);

			// activeUpdate = getSegmentActiveSynapses(c, i, s, t,
			// false);

			// segmentUpdateList.add(activeUpdate);

			// predSegment = getBestMatchingSegment(c, i, t-1);
			Segment predictingSegment = neuron
				.getBestPreviousActiveSegment();

			// predUpdate = getSegmentActiveSynapses(c, i,
			// predSegment, t-1, true);

			// segmentUpdateList.add(predUpdate);
		    }
		}
	    }

	}
    }

    /**
     * Carries out learning. Segment updates that have been queued up are
     * actually implemented once we get feed-forward input and a Neuron is
     * chosen as a learning Neuron. Otherwise, if the Neuron ever stops
     * predicting for any reason, we negatively reinforce the Segments.
     */
    public void phaseThree(Set<Column> activeColumns) {
	for (Column column : activeColumns) {
	    Neuron learningNeuron = column.getLearningNeuron();
	    for (Neuron neuron : column.getNeurons()) {
		if (neuron.equals(learningNeuron)) {
		    // adaptSegments(segmentUpdateList(c, i), true);
		    // segmentUpdateList(c, i).delete(); ???
		} else if (neuron.getPredictingState() == false
			&& neuron.getPreviousPredictingState() == true) {
		    // adaptSegments(segmentUpdateList(c, i), false);
		    // segmentUpdateList(c, i).delete();
		}
	    }
	}
    }

    public void adaptSegments(SegmentUpdateList segmentUpdateList,
	    boolean rename) {

    }

    public int getBestMatchingNeuronIndex(Column column) {
	return -1;
    }
}
