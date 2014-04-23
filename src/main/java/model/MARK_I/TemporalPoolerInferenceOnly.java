package main.java.model.MARK_I;

import java.util.Set;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version April 22, 2014
 */
public class TemporalPoolerInferenceOnly extends Pooler {
    private SpatialPooler spatialPooler;

    public TemporalPoolerInferenceOnly(SpatialPooler spatialPooler) {
	this.spatialPooler = spatialPooler;
	super.region = spatialPooler.getRegion();
    }

    public void performTemporalPoolingInferenceOnlyOnRegion() {
	Set<Column> activeColumns = this.spatialPooler.getActiveColumns();
	// phase 1
	this.computeActiveStateOfAllNeuronsInActiveColumn(activeColumns);

	// phase 2
	this.computePredictiveStateOfAllNeurons(activeColumns);
    }

    public void computeActiveStateOfAllNeuronsInActiveColumn(
	    Set<Column> activeColumns) {
	for (Column column : activeColumns) {

	    boolean bottomUpPredicted = false;

	    for (Neuron neuron : column.getNeurons()) {

		if (neuron.getPreviousActiveState() == true) {
		    DistalSegment bestSegment = neuron
			    .getBestPreviousActiveSegment();

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

    public void computePredictiveStateOfAllNeurons(Set<Column> activeColumns) {
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