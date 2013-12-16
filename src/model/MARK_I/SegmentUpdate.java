package model.MARK_I;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK I | August 11, 2013
 */
public class SegmentUpdate {
    // segment index (-1 if its a new segment)

    private Set<Synapse<Cell>> synapsesWithActiveCells;
    private Set<Synapse<Cell>> synapsesWithDeactiveCells;

    private boolean sequenceState;

    public SegmentUpdate() {
	this.synapsesWithActiveCells = new HashSet<Synapse<Cell>>();
	this.synapsesWithDeactiveCells = new HashSet<Synapse<Cell>>();
	this.sequenceState = false;
    }

    public SegmentUpdate(Set<Synapse<Cell>> synapsesWithActiveCells, Set<Synapse<Cell>> synapsesWithDeactiveCells, boolean sequenceState) {
	this.synapsesWithActiveCells = synapsesWithActiveCells;
	this.synapsesWithDeactiveCells = synapsesWithDeactiveCells;
	this.sequenceState = sequenceState;
    }

    public Set<Synapse<Cell>> getSynapsesWithActiveCells() {
	return this.synapsesWithActiveCells;
    }

    public Set<Synapse<Cell>> getSynapsesWithDeactiveCells() {
	return this.synapsesWithDeactiveCells;
    }

    public void setSynapsesWithActiveCells(
	    Set<Synapse<Cell>> synapsesWithActiveCells) {
	this.synapsesWithActiveCells = synapsesWithActiveCells;
    }

    public void setSynapsesWithDeactiveCells(Set<Synapse<Cell>> synapsesWithDeactiveCells) {
	this.synapsesWithDeactiveCells = synapsesWithDeactiveCells;
    }

    public boolean getSequenceState() {
	return this.sequenceState;
    }

    public void setSequenceState(boolean sequenceState) {
	this.sequenceState = sequenceState;
    }
}
