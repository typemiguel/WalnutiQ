package model.MARK_II;

/**
 * Input to DistalSegment: activity of active Synapses connected to this Segment.
 *
 * Output from DistalSegment: whether or not this Segment is currently active,
 * previously active, or a sequence Segment.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK II | July 22, 2013
 */
public class DistalSegment extends Segment {
    private boolean wasActive;
    private boolean sequenceState;

    public DistalSegment() {
	super();
	this.wasActive = false;
	this.sequenceState = false;
    }

    public boolean getPreviousActiveState() {
	return this.wasActive;
    }

    public void setPreviousActiveState(boolean previousActiveState) {
	this.wasActive = previousActiveState;
    }

    public boolean getSequenceState()
    {
        return this.sequenceState;
    }

    public void setSequenceState(boolean sequenceState)
    {
        this.sequenceState = sequenceState;
    }

    public int getNumberOfPreviousActiveSynapses() {
	int numberOfPreviousActiveSynapses = 0;
	for (Synapse synapse : synapses) {
	    if (synapse.isConnected() && synapse.getConnectedCell().getPreviousActiveState()) {
		numberOfPreviousActiveSynapses++;
	    }
	}
	return numberOfPreviousActiveSynapses;
    }

    @Override
    public String toString() {
	StringBuilder stringBuilder = new StringBuilder();
	stringBuilder.append("\n====================================");
	stringBuilder.append("\n---------DistalSegment Info---------");
	stringBuilder.append("\n                active state: ");
	stringBuilder.append(this.getActiveState());
	stringBuilder.append("\n       previous active state: ");
	stringBuilder.append(this.wasActive);
	stringBuilder.append("\n              sequence state: ");
	stringBuilder.append(this.sequenceState);
	stringBuilder.append("\n  # previous active Synapses: ");
	stringBuilder.append(this.getNumberOfPreviousActiveSynapses());
	stringBuilder.append("\n    number of total Synapses: ");
	stringBuilder.append(this.synapses.size());
	stringBuilder.append("\nminimum activation threshold: ");
	stringBuilder
		.append((int) (this.synapses.size() * PERCENT_ACTIVE_SYNAPSES_THRESHOLD));
	stringBuilder.append("\n   number of active Synapses: ");
	stringBuilder.append(this.getNumberOfActiveSynapses());
	stringBuilder.append("\n=====================================");
	String columnInformation = stringBuilder.toString();
	return columnInformation;
    }
}
