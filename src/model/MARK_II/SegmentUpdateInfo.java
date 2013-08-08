package model.MARK_II;

import java.util.HashSet;

import java.util.Set;

public class SegmentUpdateInfo {
    // segment index (-1 if its a new segment)

    private Set<Synapse<Cell>> existingActiveSynapses;

    private boolean sequenceState;

    public SegmentUpdateInfo() {
	this.existingActiveSynapses = new HashSet<Synapse<Cell>>();
	this.sequenceState = false;
    }

    public Set<Synapse<Cell>> getExistingActiveSynapses() {
	return this.existingActiveSynapses;
    }

    public boolean getSequenceState() {
	return this.sequenceState;
    }

    public void setSequenceState(boolean sequenceState) {
	this.sequenceState = sequenceState;
    }
}
