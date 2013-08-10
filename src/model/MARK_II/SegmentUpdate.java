package model.MARK_II;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK II | August 10, 2013
 */
public class SegmentUpdate {
    // segment index (-1 if its a new segment)

    private Set<Synapse<Cell>> synapses;

    private boolean sequenceState;

    public SegmentUpdate() {
	this.synapses = new HashSet<Synapse<Cell>>();
	this.sequenceState = false;
    }

    public SegmentUpdate(Set<Synapse<Cell>> synapses, boolean sequenceState) {
	this.synapses = synapses;
	this.sequenceState = sequenceState;
    }

    public Set<Synapse<Cell>> getSynapses() {
	return this.synapses;
    }

    public void setSynapses(
	    Set<Synapse<Cell>> synapses) {
	this.synapses = synapses;
    }

    public boolean getSequenceState() {
	return this.sequenceState;
    }

    public void setSequenceState(boolean sequenceState) {
	this.sequenceState = sequenceState;
    }
}
