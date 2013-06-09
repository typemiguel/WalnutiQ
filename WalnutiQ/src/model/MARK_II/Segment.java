package model.MARK_II;

import java.util.Set;
import java.util.HashSet;

/**
 * Provides the implementation for a distal or a proximal Segment.
 *
 * Input to Segment: activity of active Synapses connected to this Segment.
 *
 * Output from Segment: whether or not this Segment is active or not ( return
 * type of getActiveState()).
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @author Michael Cogswell (cogswell@vt.edu)
 * @version MARK II | June 8, 2013
 */
public class Segment<CellType extends Cell> {
    private Set<Synapse<CellType>> synapses;

    /**
     * Minimal percent of active Synapses out of total Synapses needed for a
     * Segment to become active.
     */
    public static final double PERCENT_ACTIVE_SYNAPSES_THRESHOLD = 0.2;

    /**
     * Provides three enums to be used as parameters in the method
     * updateSynapsePermanences(enumParameter). These 3 enums describe the three
     * different ways that Synapse permanences on a Segment can be updated.
     *
     * @author Quinn Liu (quinnliu@vt.edu)
     * @author Michael Cogswell (cogswell@vt.edu)
     * @version MARK II | April 4, 2013
     */
    public enum SynapseUpdateState {
	/**
	 * Increase permanence of Synapses with an active Cell on one Segment.
	 */
	INCREASE_ACTIVE,

	/**
	 * Increase permanence of all Synapses on one Segment.
	 */
	INCREASE_ALL,

	/**
	 * Decrease permanence of all Synapses on one Segment.
	 */
	DECREASE_ALL
    }

    public Segment() {
	this.synapses = new HashSet<Synapse<CellType>>();
    }

    /**
     * @return true if this Segment has more active Synapses than the minimal
     *         number of active Synapses needed to activate this Segment based
     *         on PERCENT_ACTIVE_SYNAPSES_THRESHOLD.
     */
    public boolean getActiveState() {
	int numberOfActiveSynapses = 0;
	for (Synapse<CellType> synapse : this.synapses) {
	    CellType abstractCell = synapse.getCell();
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
     *            This enum parameter determines how permanence of all Synapses
     *            on a Segment will be updated.
     *
     */
    public void updateSynapsePermanences(SynapseUpdateState updateState) {
	if (updateState == null) {
	    throw new IllegalArgumentException(
		    "updateState in Segment method updateSynapsePermanences cannot be null");
	}
	for (Synapse<CellType> synapse : this.synapses) {
	    switch (updateState) {
	    case INCREASE_ACTIVE:
		if (synapse.isConnected() && synapse.getCell().getActiveState()) {
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

    // TODO: Question: A proximalSegment should never connect 2 types of
    // SensorCells?
    public void addSynapse(Synapse<Cell> synapse) {
	if (synapse == null) {
	    throw new IllegalArgumentException(
		    "Synapse in Segment class method addSynapse cannot be null");
	}
	this.synapses.add((Synapse<CellType>) synapse);
    }

    public Set<Synapse<CellType>> getSynapses() {
	return this.synapses;
    }

    // TODO: make sure this is only called once
    public int getNumberOfActiveSynapses() {
	int numberOfActiveSynapses = 0;
	for (Synapse synapse : synapses) {
	    Cell cell = synapse.getCell();
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
	Segment other = (Segment) obj;
	if (synapses == null) {
	    if (other.synapses != null)
		return false;
	} else if (!synapses.equals(other.synapses))
	    return false;
	return true;
    }

    // package visible methods for test classes in the tests folder
    boolean removeSynapse(Synapse synapseToRemove) {
	for (Synapse synapse : this.synapses) {
	    if (synapseToRemove.getCell().getClass().equals(synapse.getCell().getClass())
		    && synapseToRemove.getPermanenceValue() == synapse
			    .getPermanenceValue()
		    && synapseToRemove.getCellXPosition() == synapse
			    .getCellXPosition()
		    && synapseToRemove.getCellYPosition() == synapse
			    .getCellYPosition()) {
		this.synapses.remove(synapse);
		return true;
	    }
	}
	return false;
    }

    Synapse getSynapse(int cellXPosition, int cellYPosition) {
	for (Synapse synapse : this.synapses) {
	    if (synapse.getCellXPosition() == cellXPosition && synapse.getCellYPosition() == cellYPosition) {
		return synapse;
	    }
	}
	return null;
    }
}
