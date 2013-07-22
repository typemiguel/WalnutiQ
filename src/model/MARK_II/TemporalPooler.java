package model.MARK_II;

import java.util.Set;

/**
 * Provides implementation for running the temporal pooling algorithm on a
 * Region. The input to the TemporalPooler is the activeColumns at time t
 * computed by the SpatialPooler. The TemporalPooler computes the active and
 * predictive state for each Neuron at the current timestep t. The boolean OR of
 * the active and predictive states for each neuron forms the output of the
 * TemporalPooler for the next Region in the hierarchy.
 *
 * The algorithm in split into 3 distinct phases that occur in sequence:
 * Phase 1) compute the active state for each Neuron
 * Phase 2) compute the predictive state for each Neuron
 * Phase 3) update Synapse permanences
 *
 * Phase 3 is only required for learning. However, unlike spatial pooling,
 * Phases 1 and 2 contain some learning-specific operations when learning is
 * turned on. TemporalPooler is significantly more complicated than
 * SpatialPooler.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK II | July 21, 2013
 */
public class TemporalPooler extends Pooler {

    public TemporalPooler(Region newRegion) {
	this.region = newRegion;
    }

    // return Set<NeuronPositions>
    public void performTemporalPoolingOnRegion(Set<Column> activeColumns) {
	// inference alone
	this.computeActiveStateOfAllNeurons(activeColumns); // Phase 1
	this.computePredictiveStateOfAllNeurons(activeColumns); // Phase 2
    }

    void computeActiveStateOfAllNeurons(Set<Column> activeColumns) {
	for (Column column : activeColumns) {
	    boolean bottomUpPredicted = false;
	    for (Neuron neuron : column.getNeurons()) {
		if (neuron.getPreviousPredictingState() == true) {
		    Segment bestSegment = neuron.getBestPreviousActiveSegment();
		    if (bestSegment != null && bestSegment.getSequenceState()) {
			bottomUpPredicted = true;
			neuron.setActiveState(true);
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
}
