package model.MARK_II;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK II | August 8, 2013
 */
public class SegmentUpdate {
    // segment index (-1 if its a new segment)

    private Set<Synapse<Cell>> existingActiveSynapses;

    private boolean sequenceState;

    public SegmentUpdate() {
	this.existingActiveSynapses = new HashSet<Synapse<Cell>>();
	this.sequenceState = false;
    }

    public Set<Synapse<Cell>> getExistingActiveSynapses() {
	return this.existingActiveSynapses;
    }

    public void setExistingActiveSynapses(
	    Set<Synapse<Cell>> existingActiveSynapses) {
	this.existingActiveSynapses = existingActiveSynapses;
    }

    public boolean getSequenceState() {
	return this.sequenceState;
    }

    public void setSequenceState(boolean sequenceState) {
	this.sequenceState = sequenceState;
    }
}
