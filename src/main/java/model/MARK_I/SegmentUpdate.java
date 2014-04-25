package model.MARK_I;

import java.awt.Point;
import java.util.Set;

class SegmentUpdate {
    private int segmentIndex;
    private Set<Synapse<Cell>> synapsesWithActiveCells;
    private Set<Synapse<Cell>> synapsesWithDeactiveCells;

    private int neuronIndex;
    private Point neuronColumnPosition;

    /** same as sequenceSegment */
    private boolean predictsFeedForwardInputOnNextTimeStep;

    public SegmentUpdate(Set<Synapse<Cell>> synapsesWithActiveCells,
	    Set<Synapse<Cell>> synapsesWithDeactiveCells, int neuronIndex,
	    Point neuronColumnPosition) {
	this.segmentIndex = -1;
	this.synapsesWithActiveCells = synapsesWithActiveCells;
	this.synapsesWithDeactiveCells = synapsesWithDeactiveCells;
	this.predictsFeedForwardInputOnNextTimeStep = false;

	this.neuronIndex = neuronIndex;
	this.neuronColumnPosition = neuronColumnPosition;
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
