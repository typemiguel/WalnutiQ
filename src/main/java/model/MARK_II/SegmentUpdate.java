package model.MARK_II;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version April 25, 2014
 */
class SegmentUpdate {
    private Set<Synapse<Cell>> synapsesWithActiveCells;
    private Set<Synapse<Cell>> synapsesWithDeactiveCells;

    /** same as sequenceSegment */
    private boolean predictsFeedForwardInputOnNextTimeStep;

    private ColumnPosition neuronColumnPosition;
    private int neuronIndex;

    private Set<Synapse<Cell>> synapsesNonexistentInModel;

    public SegmentUpdate(Set<Synapse<Cell>> synapsesWithActiveCells,
	    Set<Synapse<Cell>> synapsesWithDeactiveCells,
	    ColumnPosition neuronColumnPosition, int neuronIndex) {
	this.synapsesWithActiveCells = synapsesWithActiveCells;
	this.synapsesWithDeactiveCells = synapsesWithDeactiveCells;
	this.predictsFeedForwardInputOnNextTimeStep = false;

	this.neuronColumnPosition = neuronColumnPosition;
	this.neuronIndex = neuronIndex;

	this.synapsesNonexistentInModel = new HashSet<Synapse<Cell>>();
    }

    public ColumnPosition getNeuronColumnPosition() {
	return this.neuronColumnPosition;
    }

    public int getNeuronIndex() {
	return this.neuronIndex;
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
