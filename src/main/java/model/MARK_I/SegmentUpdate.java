package model.MARK_I;

import java.util.Set;

class SegmentUpdate {
    private int segmentIndex;
    private Set<Synapse<Cell>> synapsesWithActiveCells;
    private Set<Synapse<Cell>> synapsesWithDeactiveCells;

    private int neuronIndex;
    private ColumnPosition neuronColumnPosition;

    /** same as sequenceSegment */
    private boolean predictsFeedForwardInputOnNextTimeStep;

    public SegmentUpdate(Set<Synapse<Cell>> synapsesWithActiveCells,
	    Set<Synapse<Cell>> synapsesWithDeactiveCells,
	    ColumnPosition neuronColumnPosition, int neuronIndex) {
	this.segmentIndex = -1;
	this.synapsesWithActiveCells = synapsesWithActiveCells;
	this.synapsesWithDeactiveCells = synapsesWithDeactiveCells;
	this.predictsFeedForwardInputOnNextTimeStep = false;

	this.neuronColumnPosition = neuronColumnPosition;
	this.neuronIndex = neuronIndex;
    }

    public Set<Synapse<Cell>> getSynapsesWithActiveCells() {
	return this.synapsesWithActiveCells;
    }

    public Set<Synapse<Cell>> getSynpasesWithDeactiveCells() {
	return this.synapsesWithDeactiveCells;
    }

    public void setSequenceState(boolean predictsFeedForwardInputOnNextTimeStep) {
	this.predictsFeedForwardInputOnNextTimeStep = predictsFeedForwardInputOnNextTimeStep;
    }
}
