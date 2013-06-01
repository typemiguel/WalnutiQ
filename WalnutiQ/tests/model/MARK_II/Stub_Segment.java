package model.MARK_II;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Stub_Segment<CellType extends Stub_Cell>
	implements Serializable {
    private Set<Stub_Synapse<CellType>> synapses;

    /**
     * Minimal percent of active synapses out of total synapses needed for a
     * Segment to become active.
     */
    public static final double PERCENT_ACTIVE_SYNAPSES_THRESHOLD = 0.2;

    public enum Stub_SynapseUpdateState {
	/**
	 * Increase permanence of synapses with an active abstractCell on one
	 * segment.
	 */
	INCREASE_ACTIVE,

	/**
	 * Increase permanence of all synapses on one segment.
	 */
	INCREASE_ALL,

	/**
	 * Decrease permanence of all synapses on one segment.
	 */
	DECREASE_ALL
    }

    public Stub_Segment() {
	this.synapses = new HashSet<Stub_Synapse<CellType>>();
    }

    /**
     * @return true if this segment has more active synapses than the minimal
     *         number of active synapses needed to activate this segment based
     *         on PERCENT_ACTIVE_SYNAPSES_THRESHOLD.
     */
    public boolean getActiveState() {
	int numberOfActiveSynapses = 0;
	for (Stub_Synapse<CellType> synapse : this.synapses) {
	    CellType abstractCell = synapse.getAbstractCell();
	    if (synapse.isConnected() && abstractCell.getActiveState()) {
		numberOfActiveSynapses++;
	    }
	}
	int minimalNumberOfActiveSynapses = (int) (this.synapses.size() * PERCENT_ACTIVE_SYNAPSES_THRESHOLD);
	if (numberOfActiveSynapses > minimalNumberOfActiveSynapses) {
	    return true;
	} else {
	    return false;
	}
    }

    /**
     * @param updateState
     *            This enum parameter determines how permanence of all synapses
     *            on a segment will be updated.
     *
     */
    public void updateSynapsePermanences(Stub_SynapseUpdateState updateState) {
	if (updateState == null) {
	    throw new IllegalArgumentException(
		    "updateState in Segment method updateSynapsePermanences cannot be null");
	}
	for (Stub_Synapse<CellType> synapse : this.synapses) {
	    switch (updateState) {
	    case INCREASE_ACTIVE:
		if (synapse.isConnected()
			&& synapse.getAbstractCell().getActiveState()) {
		    synapse.increasePermanence();
		}
		break;
	    case INCREASE_ALL:
		synapse.increasePermanence();
		break;
	    case DECREASE_ALL:
		synapse.decreasePermanence();
		break;
	    }
	}
    }

    // TODO: A proximalSegment should never connect 2 types of SensorCells
    public void addSynapse(Stub_Synapse<Stub_Cell> synapse) {
	if (synapse == null) {
	    throw new IllegalArgumentException(
		    "synapse in Segment method addSynapse cannot be null");
	}
	this.synapses.add((Stub_Synapse<CellType>) synapse);
    }

    public Set<Stub_Synapse<CellType>> getSynapses() {
	return this.synapses;
    }

    public void setSynapses(Set<Stub_Synapse<CellType>> synapses) {
	if (synapses == null) {
	    throw new IllegalArgumentException(
		    "synapses in Segment method setSynapses cannot be null");
	}
	this.synapses = synapses;
    }

    // TODO: make sure this is only called once
    public int getNumberOfActiveSynapses() {
	int numberOfActiveSynapses = 0;
	for (Stub_Synapse synapse : synapses) {
	    Stub_Cell cell = synapse.getAbstractCell();
	    if (cell != null && cell.getActiveState()) {
		numberOfActiveSynapses++;
	    }
	}
	return numberOfActiveSynapses;
    }

    @Override
    public String toString() {
	StringBuilder stringBuilder = new StringBuilder();
	stringBuilder.append("\n====================================");
	stringBuilder.append("\n------------Segment Info------------");
	stringBuilder.append("\n                active state: ");
	stringBuilder.append(this.getActiveState());
	stringBuilder.append("\n          number of synapses: ");
	stringBuilder.append(this.synapses.size());
	stringBuilder.append("\nminimum activation threshold: ");
	stringBuilder
		.append((int) (this.synapses.size() * PERCENT_ACTIVE_SYNAPSES_THRESHOLD));
	stringBuilder.append("\n   number of active synapses: ");
	stringBuilder.append(this.getNumberOfActiveSynapses());
	stringBuilder.append("\n=====================================");
	String columnInformation = stringBuilder.toString();
	return columnInformation;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result
		+ ((synapses == null) ? 0 : synapses.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Stub_Segment other = (Stub_Segment) obj;
	if (synapses == null) {
	    if (other.synapses != null)
		return false;
	} else if (!synapses.equals(other.synapses))
	    return false;
	return true;
    }
}
