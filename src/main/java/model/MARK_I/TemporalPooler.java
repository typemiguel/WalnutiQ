package model.MARK_I;

import java.util.HashSet;

import java.awt.Point;

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
    private SegmentUpdateList segmentUpdateList;

    public TemporalPooler(SpatialPooler spatialPooler) {
	this.spatialPooler = spatialPooler;
	super.region = spatialPooler.getRegion();
	this.segmentUpdateList = new SegmentUpdateList();
    }

    public void performTemporalPoolingOnRegion() {
	Set<Column> activeColumns = this.spatialPooler.getActiveColumns();
	this.phaseOne(activeColumns);

	this.phaseTwo(activeColumns);

	this.phaseThree(activeColumns);

	this.segmentUpdateList.clear();
    }

    /**
     * Compute the activeState for each Neuron in activeColumns. Then in each
     * active Column a learning Neuron is chosen.
     */
    void phaseOne(Set<Column> activeColumns) {
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

		SegmentUpdate segmentUpdate = this.getSegmentActiveSynapses(
			column.getCurrentPosition(), bestNeuronIndex, column
				.getLearningNeuron()
				.getBestPreviousActiveSegment(), true, true);

		segmentUpdate.setSequenceState(true);
		this.segmentUpdateList.add(segmentUpdate);
	    }
	}
    }

    /**
     * @return A segmentUpdate data structure containing a list of proposed
     *         changes to segment. Let activeSynapses be the list of active
     *         synapses where the originating cells have their activeState
     *         output = 1 at time step t. (This list is empty if s = -1 since
     *         the segment doesn't exist.) newSynapses is an optional argument
     *         that defaults to false. If newSynapses is true, then
     *         newSynapseCount - count(activeSynapses) synapses are added to
     *         activeSynapses. These synapses are randomly chosen from the set
     *         of cells that have learnState output = 1 at time step t.
     */
    SegmentUpdate getSegmentActiveSynapses(ColumnPosition columnPosition,
	    int neuronIndex, Segment segment, boolean previousTimeStep,
	    boolean newSynapses) {

	if (previousTimeStep) {

	    return null;
	} else { // current time step

	    return null;
	}
    }

    /**
     * Calculated the predictive state for each Neuron. A Neuron's
     * predictiveState will be true if 1 or more distal segments becomes active.
     */
    void phaseTwo(Set<Column> activeColumns) {
	for (Column column : activeColumns) {
	    Neuron[] neurons = column.getNeurons();
	    for (int i = 0; i < neurons.length; i++) {
		for (Segment segment : neurons[i].getDistalSegments()) {
		    if (segment.getActiveState()) {
			neurons[i].setPredictingState(true);

			SegmentUpdate activeUpdate = this
				.getSegmentActiveSynapses(
					column.getCurrentPosition(), i,
					segment, false, false);

			this.segmentUpdateList.add(activeUpdate);

			Segment predictingSegment = neurons[i]
				.getBestPreviousActiveSegment();

			SegmentUpdate predictionUpdate = this
				.getSegmentActiveSynapses(
					column.getCurrentPosition(), i,
					predictingSegment, true, true);

			this.segmentUpdateList.add(predictionUpdate);
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
    void phaseThree(Set<Column> activeColumns) {
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

    /**
     * Iterates through the Synapses of a SegmentUpdate and reinforces each
     * Synapse. If positiveReinforcement is true then Synapses on the list get
     * their permanenceValues incremented by permanenceIncrease. All other
     * Synapses get their permanenceValue decremented by permanenceDecrease. If
     * positiveReinforcement is false, then Synapses on the list get their
     * permanenceValues decremented by permanenceDecrease. Finally, any Synapses
     * in SegmentUpdate that do not yet exist get added with a permanenceValue
     * of initialPermanence.
     */
    void adaptSegments(SegmentUpdate segmentUpdate,
	    boolean positiveReinforcement) {
	Set<Synapse<Cell>> synapsesWithActiveCells = segmentUpdate
		.getSynapsesWithActiveCells();
	Set<Synapse<Cell>> synapsesWithDeactiveCells = segmentUpdate
		.getSynpasesWithDeactiveCells();

	if (positiveReinforcement) {
	    for (Synapse<Cell> synapse : synapsesWithActiveCells) {
		synapse.increasePermanence();
	    }
	    for (Synapse<Cell> synapse : synapsesWithDeactiveCells) {
		synapse.decreasePermanence();
	    }
	} else {
	    for (Synapse<Cell> synapse : synapsesWithActiveCells) {
		synapse.decreasePermanence();
	    }
	}

	// TODO: add Synapses in SegmentUpdate that do not yet exist???
    }

    int getBestMatchingNeuronIndex(Column column) {
	return -1;
    }
}
