package model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import model.MARKII_Segment.SynapseUpdateState;

public class Stub_MARKII_Segment<CellType extends Stub_MARKII_AbstractCell> implements
	Serializable {
    private Set<Stub_MARKII_Synapse<CellType>> synapses;

    /**
     * Minimal percent of active synapses out of total synapses needed for a
     * Segment to become active.
     */
    public static final double PERCENT_ACTIVE_SYNAPSES_THRESHOLD = 0.2;

    /**
     * Provides three enums to be used as parameters in the method
     * updateSynapsePermanences(enumParameter). These 3 enums describe the three
     * different ways that synapse permanences on a segment can be updated.
     *
     * @author Quinn Liu (quinnliu@vt.edu)
     * @author Michael Cogswell (cogswell@vt.edu)
     * @version MARK II | April 4, 2013
     */
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

    public Stub_MARKII_Segment() {
	this.synapses = new HashSet<Stub_MARKII_Synapse<CellType>>();
    }

    /**
     * @return true if this segment has more active synapses than the minimal
     *         number of active synapses needed to activate this segment based
     *         on PERCENT_ACTIVE_SYNAPSES_THRESHOLD.
     */
    public boolean getActiveState() {
	int numberOfActiveSynapses = 0;
	for (Stub_MARKII_Synapse<CellType> synapse : this.synapses) {
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
	for (Stub_MARKII_Synapse<CellType> synapse : this.synapses) {
	    switch (updateState) {
	    case INCREASE_ACTIVE:
		if (synapse.isConnected()
			&& synapse.getAbstractCell().getActiveState()) {
		    synapse.increasePermance();
		}
		break;
	    case INCREASE_ALL:
		synapse.increasePermance();
		break;
	    case DECREASE_ALL:
		synapse.decreasePermance();
		break;
	    }
	}
    }

    // TODO: A proximalSegment should never connect 2 types of SensorCells
    public boolean addSynapse(Stub_MARKII_Synapse<Stub_MARKII_AbstractCell> synapse) {
	if (synapse != null) {
	    this.synapses.add((Stub_MARKII_Synapse<CellType>) synapse);
	    return true;
	} else {
	    return false;
	}
    }

    public Set<Stub_MARKII_Synapse<CellType>> getSynapses() {
	return this.synapses;
    }

    public boolean setSynapses(Set<Stub_MARKII_Synapse<CellType>> synapses) {
	if (synapses != null) {
	    this.synapses = synapses;
	    return true;
	} else {
	    return false;
	}
    }

    // TODO: make sure this is only called once
    public int getNumberOfActiveSynapses() {
	int numberOfActiveSynapses = 0;
	for (Stub_MARKII_Synapse synapse : synapses) {
	    Stub_MARKII_AbstractCell cell = synapse.getAbstractCell();
	    if (cell != null && cell.getActiveState()) {
		numberOfActiveSynapses++;
	    }
	}
	return numberOfActiveSynapses;
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
	MARKII_Segment other = (MARKII_Segment) obj;
	if (synapses == null) {
	    if (other.getSynapses() != null)
		return false;
	} else if (!synapses.equals(other.getSynapses()))
	    return false;
	return true;
    }
}
