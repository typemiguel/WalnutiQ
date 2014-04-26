package model.MARK_II;

import java.util.ArrayList;

import java.util.List;

import java.util.HashSet;

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
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version April 25, 2014
 */
public class TemporalPooler extends Pooler {
    private SpatialPooler spatialPooler;
    private SegmentUpdateList segmentUpdateList;

    private final int newSynapseCount;

    private List<Neuron> listOfCurrentLearningNeurons;

    public TemporalPooler(SpatialPooler spatialPooler, int newSynapseCount) {
	this.spatialPooler = spatialPooler;
	super.region = spatialPooler.getRegion();
	this.segmentUpdateList = new SegmentUpdateList();

	this.newSynapseCount = newSynapseCount;

	this.listOfCurrentLearningNeurons = new ArrayList<Neuron>();
    }

    public void performTemporalPoolingOnRegion() {
	Set<Column> activeColumns = this.spatialPooler.getActiveColumns();
	this.phaseOne(activeColumns);

	this.phaseTwo(activeColumns);

	this.phaseThree(activeColumns);

	this.nextTimeStep();

	this.segmentUpdateList.clear();
    }

    void nextTimeStep() {
	Column[][] columns = super.region.getColumns();
	for (int x = 0; x < super.region.getXAxisLength(); x++) {
	    for (int y = 0; y < super.region.getYAxisLength(); y++) {
		for (Neuron neuron : columns[x][y].getNeurons()) {
		    neuron.nextTimeStep();

		    for (DistalSegment distalSegment : neuron
			    .getDistalSegments()) {
			distalSegment.nextTimeStep();
		    }
		}
	    }
	}
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
			    this.listOfCurrentLearningNeurons.add(neurons[i]);
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

		SegmentUpdate segmentUpdate = this
			.getSegmentActiveSynapses(column.getCurrentPosition(),
				bestNeuronIndex, neurons[bestNeuronIndex]
					.getBestPreviousActiveSegment(), true,
				true);

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
	Set<Synapse<Cell>> activeSynapses = new HashSet<Synapse<Cell>>();
	Set<Synapse<Cell>> deactiveSynapses = new HashSet<Synapse<Cell>>();

	for (Synapse<Cell> synapse : segment.getSynapses()) {

	    if (previousTimeStep) {
		if (synapse.getCell().getPreviousActiveState()) {
		    activeSynapses.add(synapse);
		} else {
		    deactiveSynapses.add(synapse);
		}
	    } else {
		if (synapse.getCell().getActiveState()) {
		    activeSynapses.add(synapse);
		} else {
		    deactiveSynapses.add(synapse);
		}
	    }
	}

	if (newSynapses) {
	    activeSynapses = this
		    .addRandomlyChosenSynapsesFromCurrentLearningNeurons(activeSynapses);
	}

	return new SegmentUpdate(activeSynapses, deactiveSynapses,
		columnPosition, neuronIndex);
    }

    Set<Synapse<Cell>> addRandomlyChosenSynapsesFromCurrentLearningNeurons(
	    Set<Synapse<Cell>> activeSynapses) {
	int numberOfSynapsesToAddToActiveSynapses = this.newSynapseCount
		- activeSynapses.size();

	List<Synapse<Cell>> synapses = new ArrayList<Synapse<Cell>>();
	for (Neuron neuron : this.listOfCurrentLearningNeurons) {

	    for (DistalSegment distalSegment : neuron.getDistalSegments()) {
		if (synapses.size() > numberOfSynapsesToAddToActiveSynapses) {
		    break;
		} else {
		    synapses.addAll(distalSegment.getSynapses());
		}
	    }
	    // it's possible synapses.size() is still <
	    // numberOfSynapsesToAddToActiveSynapses
	    if (synapses.size() > numberOfSynapsesToAddToActiveSynapses) {
		break;
	    }

	}

	// it's possible synapses.size() is still <
	// numberOfSynapsesToAddToActiveSynapses must doesn't really matter
	if (numberOfSynapsesToAddToActiveSynapses > synapses.size()) {
	    // synapses.size() should always be > 0
	    numberOfSynapsesToAddToActiveSynapses = synapses.size();
	}
	for (int i = 0; i < numberOfSynapsesToAddToActiveSynapses; i++) {
	    activeSynapses.add(synapses.get(i));
	}

	return activeSynapses;
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
	    ColumnPosition c = column.getCurrentPosition();
	    Neuron[] neurons = column.getNeurons();
	    for (int i = 0; i < neurons.length; i++) {
		if (i == column.getLearningNeuronPosition()) {
		    this.adaptSegments(
			    this.segmentUpdateList.getSegmentUpdate(c, i), true);

		    this.segmentUpdateList.deleteSegmentUpdate(c, i);
		} else if (neurons[i].getPredictingState() == false
			&& neurons[i].getPreviousPredictingState() == true) {

		    this.adaptSegments(
			    this.segmentUpdateList.getSegmentUpdate(c, i),
			    false);

		    this.segmentUpdateList.deleteSegmentUpdate(c, i);
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

    /**
     * @return the index of the Neuron with the Segment with the greatest number
     *         of active Synapses.
     */
    int getBestMatchingNeuronIndex(Column column) {
	int greatestNumberOfActiveSynapses = 0;
	int bestMatchingNeuronIndex = 0;

	Neuron[] neurons = column.getNeurons();
	for (int i = 0; i < neurons.length; i++) {
	    Segment bestSegment = neurons[i].getBestActiveSegment();
	    int numberOfActiveSynapses = bestSegment
		    .getNumberOfActiveSynapses();
	    if (numberOfActiveSynapses > greatestNumberOfActiveSynapses) {
		greatestNumberOfActiveSynapses = numberOfActiveSynapses;
		bestMatchingNeuronIndex = i;
	    }
	}

	return bestMatchingNeuronIndex;
    }
}
