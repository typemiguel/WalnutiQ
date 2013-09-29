package model.MARK_II;

import java.util.List;

import java.util.HashSet;
import java.util.Set;

/**
 * Provides implementation for running the temporal pooling algorithm on a
 * Region with and without learning. The TemporalPooler computes the active and
 * predictive state for each Neuron at the current time step t.
 *
 * Input into TemporalPooler: activeColumns at time t computed by the
 * SpatialPooler.
 *
 * Output from TemporalPooler: boolean OR of the active and predictive states
 * for each neuron forms the output of the TemporalPooler for the next Region in
 * the hierarchy.
 *
 * ----------------------------Notes------------------------------------ in the
 * temporal pooler, we randomly add active synapses to each segment during
 * learning
 *
 * newSynapseCount = the maximum number of synapses added to a segment during
 * learning
 *
 * When an input is unexpected, all the cells in a particular column become
 * active in the same time step. Only one of these cells(the cell that best
 * matches the input) has its learnState turned on. We only add synapses from
 * cells that have learnState set to one(this avoids over representing a fully
 * active column in dendritic segments).
 * -----------------------------------------------------------------------
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK II | August 24, 2013
 */
public class TemporalPooler extends Pooler {

    // list of Synapses and Segments to update in this.region
    private SegmentUpdateList segmentUpdateList;

    public TemporalPooler(Region newRegion) {
	this.region = newRegion;
	this.segmentUpdateList = new SegmentUpdateList();
    }

    public void performInferenceOnRegion(Set<Column> activeColumns) {
	this.computeActiveStateOfAllNeurons(activeColumns); // Phase 1
	this.computePredictiveStateOfAllNeurons(activeColumns); // Phase 2
    }

    void computeActiveStateOfAllNeurons(Set<Column> activeColumns) {
	for (Column column : activeColumns) {
	    boolean bottomUpPredicted = false;
	    Neuron[] neurons = column.getNeurons();
	    for (int i = 0; i < neurons.length; i++) {
		if (neurons[i].getPreviousPredictingState() == true) {
		    DistalSegment bestSegment = neurons[i]
			    .getBestPreviousActiveSegment();
		    if (bestSegment != null && bestSegment.getSequenceState()) {
			bottomUpPredicted = true;
			neurons[i].setActiveState(true);
		    }
		}
	    }
	    if (bottomUpPredicted == false) {
		for (Neuron neuron : column.getNeurons()) {
		    neuron.setActiveState(true);
		}
	    }
	}
    }

    void computePredictiveStateOfAllNeurons(Set<Column> activeColumns) {
	for (Column column : activeColumns) {
	    for (Neuron neuron : column.getNeurons()) {
		for (Segment segment : neuron.getDistalSegments()) {
		    if (segment.getActiveState()) {
			neuron.setPredictingState(true);
		    }
		}
	    }
	}
    }

    /**
     * The algorithm in split into 3 distinct phases that occur in sequence:
     *
     * Phase 3 is only required for learning. However, unlike spatial pooling,
     * Phases 1 and 2 contain some learning-specific operations when learning is
     * turned on. TemporalPooler is significantly more complicated than
     * SpatialPooler.
     */
    public Region performTemporalPoolingOnRegion(Set<Column> activeColumns) {
	this.phaseOne(activeColumns);
	this.phaseTwo(activeColumns);

	// TODO: call phaseThree correctly
	return this.region;
    }

    /**
     * Compute the active state for each Neuron in activeColumns. Then in each
     * active Column a learning Cell is chosen.
     */
    void phaseOne(Set<Column> activeColumns) {
	// for c in activeColumns(t)
	for (Column column : activeColumns) {
	    // buPredicted = false
	    boolean bottomUpPredicted = false;

	    // lcChosen = false
	    boolean learningCellChosen = false;

	    // for i = 0 to cellsPerColumn - 1
	    Neuron[] neurons = column.getNeurons();
	    for (int i = 0; i < neurons.length; i++) {
		// if predictiveState(c, i, t-1) == true then
		if (neurons[i].getPreviousPredictingState() == true) {
		    // s = getActiveSegment(c, i, t-1, activeState)
		    DistalSegment bestSegment = neurons[i]
			    .getBestPreviousActiveSegment();

		    // if s.sequenceSegment == true then
		    if (bestSegment != null && bestSegment.getSequenceState()) {
			// buPredicted = true
			bottomUpPredicted = true;

			// activeState(c, i, t) = 1
			neurons[i].setActiveState(true);

			// if segmentActive(s, t-1, learnState) then
			if (bestSegment.getPreviousActiveState()) {
			    // lcChosen = true
			    learningCellChosen = true;

			    // learnState(c, i, t) = 1
			    column.setLearningNeuronPosition(i);
			}
		    }
		}
	    }
	    // if buPredicted == false then
	    if (bottomUpPredicted == false) {
		// for i = 0 to cellsPerColumn - 1
		for (Neuron neuron : column.getNeurons()) {
		    // activeState(c, i, t) = 1
		    neuron.setActiveState(true);
		}
	    }
	    // if lcChosen == false then
	    if (learningCellChosen == false) {
		// l,s = getBestMatchingCell(c, t-1)
		int bestNeuronIndex = this.getBestMatchingNeuronIndex(column);

		// learnState(c, i, t) = 1
		column.setLearningNeuronPosition(bestNeuronIndex);

		// sUpdate = getSegmentActiveSynapses(c, i, s, t-1, true)
		Set<Synapse<Cell>> synapsesWithPreviousActiveCells = this
			.getSynapsesWithPreviousCellsToUpdate(column
				.getLearningNeuron()
				.getBestPreviousActiveSegment(), true);
		Set<Synapse<Cell>> synapsesWithPreviousDeactiveCells = this
			.getSynapsesWithPreviousCellsToUpdate(column
				.getLearningNeuron()
				.getBestPreviousActiveSegment(), false);

		// sUpdate.sequenceSegment = true
		// segmentUpdateList.add(sUpdate)
		this.segmentUpdateList.getSegmentUpdates().add(
			new SegmentUpdate(synapsesWithPreviousActiveCells,
				synapsesWithPreviousDeactiveCells, true));
	    }
	}
    }

    Segment getBestActiveSegment(Neuron neuron) {
	if (neuron == null) {
	    throw new IllegalArgumentException(
		    "neuron in TemporalPooler class getBestActiveSegment method"
			    + " cannot be null");
	}
	Segment bestSegment = null;

	// covers the case where there is a distalSegment with 0 active Synapses
	int greatestNumberOfActiveSynapses = -1;
	for (Segment distalSegment : neuron.getDistalSegments()) {
	    if (distalSegment.getNumberOfActiveSynapses() > greatestNumberOfActiveSynapses) {
		bestSegment = distalSegment;
		greatestNumberOfActiveSynapses = distalSegment
			.getNumberOfActiveSynapses();
	    }
	}
	return bestSegment;
    }

    /**
     * @return The index of the Neuron with the best matching Segment. Where
     *         best matching Segment is the segment with the largest number of
     *         active Synapses.
     */
    int getBestMatchingNeuronIndex(Column column) {
	if (column == null) {
	    throw new IllegalArgumentException(
		    "column in TemporalPooler class getBestMatchingNeuronIndex method"
			    + " cannot be null");
	}
	Neuron[] neurons = column.getNeurons();
	Cell bestCell = neurons[0];
	int greatestNumberOfActiveSynapses = 0;
	int bestMatchingNeuronIndex = 0;
	for (int i = 0; i < neurons.length; i++) {
	    Segment bestSegment = this.getBestActiveSegment(neurons[i]);
	    if (bestSegment.getNumberOfActiveSynapses() > greatestNumberOfActiveSynapses) {
		greatestNumberOfActiveSynapses = bestSegment
			.getNumberOfActiveSynapses();
		bestMatchingNeuronIndex = i;
	    }
	}
	return bestMatchingNeuronIndex;
    }

    /**
     * @param previousActiveCells
     *            Whether to return Cells that previous active or deactive.
     */
    Set<Synapse<Cell>> getSynapsesWithPreviousCellsToUpdate(Segment segment,
	    boolean previousActiveCells) {
	Set<Synapse<Cell>> synapses = segment.getSynapses();
	Set<Synapse<Cell>> synapseWithPreviousActiveOrDeactiveCells = new HashSet<Synapse<Cell>>();
	for (Synapse synapse : synapses) {
	    if (previousActiveCells) {
		if (synapse.getCell().getPreviousActiveState() == true) {
		    synapseWithPreviousActiveOrDeactiveCells.add(synapse);
		}
	    } else { // add Cells that were previously not active
		if (synapse.getCell().getPreviousActiveState() == false) {
		    synapseWithPreviousActiveOrDeactiveCells.add(synapse);
		}
	    }

	}
	return synapseWithPreviousActiveOrDeactiveCells;
	// TODO: if newSynapses is true, then newSynapseCount -
	// count(activeSynapses)
	// synapses are added to activeSynapses. These synapses are randomly
	// chosen from the set of cells that have learnState output = 1
    }

    Set<Synapse<Cell>> getSynapseWithCellsToUpdate(Segment segment, boolean
	    activeCells) {
	Set<Synapse<Cell>> synapses = segment.getSynapses();
	Set<Synapse<Cell>> synapseWithActiveOrDeactiveCells = new HashSet<Synapse<Cell>>();
	for (Synapse synapse : synapses) {
	    if (activeCells) {
		if (synapse.getCell().getActiveState() == true) {
		    synapseWithActiveOrDeactiveCells.add(synapse);
		}
	    } else {
		if (synapse.getCell().getActiveState() == false) {
		    synapseWithActiveOrDeactiveCells.add(synapse);
		}
	    }
	}
	return synapseWithActiveOrDeactiveCells;
    }

    /**
     * Calculates the predictive state for each Neuron. A Neuron's
     * predictiveState will be true if one if it's distalSegments becomes
     * active.
     */
    void phaseTwo(Set<Column> activeColumns) {
	// for c, i in cells
	for (Column column : activeColumns) {
	    for (Neuron neuron : column.getNeurons()) {
		// for s in segments(c, i)
		for (Segment segment : neuron.getDistalSegments()) {
		    // if segmentActive(s, t, activeState) then
		    if (segment.getActiveState()) {
			// predictiveState(c, i, t) = 1
			neuron.setPredictingState(true);

			// activeUpdate = getSegmentActiveSynapses(c, i, s, t,
			// false)
			Set<Synapse<Cell>> synapsesWithActiveCells_1 = this
				.getSynapseWithCellsToUpdate(segment, true);
			Set<Synapse<Cell>> synapsesWithDeactiveCells_1 = this.
				getSynapseWithCellsToUpdate(segment, false);

			// segmentUpdateList.add(activeUpdate)
			this.segmentUpdateList.getSegmentUpdates().add(
				new SegmentUpdate(synapsesWithActiveCells_1,
					synapsesWithDeactiveCells_1, false));

			// predSegment = getBestMatchingSegment(c, i, t-1)
			Segment predictingSegment = neuron
				.getBestPreviousActiveSegment();

			// predUpdate = getSegmentActiveSynapses(c, i,
			// predSegment, t-1, true)
			Set<Synapse<Cell>> synapsesWithActiveCells_2 = this
				.getSynapsesWithPreviousCellsToUpdate(predictingSegment, true);
			Set<Synapse<Cell>> synapsesWithDeactiveCells_2 = this
				.getSynapsesWithPreviousCellsToUpdate(predictingSegment, false);

			// segmentUpdateList.add(predUpdate)
			this.segmentUpdateList.getSegmentUpdates().add(
				new SegmentUpdate(synapsesWithActiveCells_2,
					synapsesWithDeactiveCells_2, false));
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
	// for c, i in cells
	for (Column column : activeColumns) {
	    for (Neuron neuron : column.getNeurons()) {
		// TODO: reimplement learnState to be a property of the Neuron
	    }

	    // if learnState(c, i, t) == 1 then
	    Neuron learningNeuron = column.getLearningNeuron();

	    // adaptSegments(segmentUpdateList(c, i), true)
	    List<SegmentUpdate> segmentUpdates = this.segmentUpdateList.getSegmentUpdates();
	    for (SegmentUpdate segmentUpdate : segmentUpdates) {
		this.adaptSegments(segmentUpdate, true);
	    }
	    // segmentUpdateList(c, i).delete() TODO: how to implement?

	    // else if predictiveState(c, i, t) == 0 and predictiveState(c, i,
	    // t-1) == 1 then
	    // adaptSegments(segmentUpdateList(c,i), false)
	    // segmentUpdateList(c,i).delete()
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
		.getSynapsesWithDeactiveCells();
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
	// TODO: add Synapses in SegmentUpdate that do not yet exist
    }
}
