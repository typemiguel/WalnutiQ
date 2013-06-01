package model.MARK_II;

import java.io.Serializable;

public class Stub_Synapse<CellType extends Stub_Cell>
	implements Serializable {
    private CellType abstractCell;
    private double permanenceValue;
    private int abstractCellX;
    private int abstractCellY;

    // class level variables that can be changed for all synapses
    /**
     * Amount permanenceValue is increased during one iteration of learning.
     */
    public static final double PERMANENCE_INCREASE = 0.015;

    /**
     * Amount permanenceValue is decreased during one iteration of learning.
     */
    public static final double PERMANENCE_DECREASE = 0.005;

    /**
     * Minimal permanenceValue needed for a Synapse to be connected.
     */
    public static final double MINIMAL_CONNECTED_PERMANCE = 0.2;

    public static final double INITIAL_PERMANENCE = 0.3;

    /**
     * Create a new Synapse object with an AbstractCell object and given
     * permanence value.
     *
     * @param abstractCell
     *            The AbstractCell object that will be providing data for a
     *            Synapse object.
     */
    public Stub_Synapse(CellType abstractCell, double initialPermanence) {
	if (abstractCell == null) {
	    throw new IllegalArgumentException(
		    "abstractCell in Synapse constructor cannot be null");
	} else if (initialPermanence < 0.0 || initialPermanence > 1.0) {
	    throw new IllegalArgumentException(
		    "initialPermanence in Synapse constructor must be between 0 and 1");
	} else {
	    this.abstractCell = abstractCell;
	    this.permanenceValue = initialPermanence;
	}
    }

    /**
     * Create a new Synapse object with an AbstractCell object and
     * INITIAL_PERMANENCE value.
     *
     * @param abstractCell
     *            The AbstractCell object that will be providing data for a
     *            Synapse object.
     */
    public Stub_Synapse(CellType abstractCell) {
	if (abstractCell == null) {
	    throw new IllegalArgumentException(
		    "abstractCell in Synapse constructor cannot be null");
	} else {
	    this.abstractCell = abstractCell;
	    this.permanenceValue = INITIAL_PERMANENCE;
	}
    }

    /**
     * @return This synapse' abstract cell if it is connected. Otherwise return
     *         null.
     */
    public CellType getAbstractCell() {
	if (this.isConnected()) {
	    return this.abstractCell;
	} else {
	    return null;
	}
    }

    public double getPermanenceValue() {
	return this.permanenceValue;
    }

    public void setPermanenceValue(double permanenceValue) {
	if (permanenceValue < 0.0 || permanenceValue > 1.0) {
	    throw new IllegalArgumentException(
		    "permanenceValue in Segment method setPermanenceValue must be between 0.0 and 1.0");
	}
	this.permanenceValue = permanenceValue;
    }

    /**
     * @return true if this synapses' permanenceValue is greater than the
     *         MINIMAL_CONNECTED_PERMANCE represented the synapse and abstract
     *         cell are connected.
     */
    public boolean isConnected() {
	return (this.permanenceValue >= MINIMAL_CONNECTED_PERMANCE);
    }

    /**
     * Increase the permanenceValue of a Synapse object by PERMANENCE_INCREASE
     * while forcing permanenceValue to always be < 1.0.
     */
    public void increasePermanence() {
	this.permanenceValue = Math.min(1.0, this.permanenceValue
		+ PERMANENCE_INCREASE);
    }

    /**
     * Decrease the permanenceValue of a Synapse object by PERMANENCE_DECREASE
     * while forcing permanenceValue to always be > 0.0.
     */
    public void decreasePermanence() {
	this.permanenceValue = Math.max(0.0, this.permanenceValue
		- PERMANENCE_DECREASE);
    }

    @Override
    public String toString() {
	StringBuilder stringBuilder = new StringBuilder();
	Stub_Cell cell = this.getAbstractCell();
	stringBuilder.append("\n===========================");
	stringBuilder.append("\n----Synapse Information----");
	stringBuilder.append("\n Connected to a: ");
	stringBuilder.append(this.abstractCell.toString());
	stringBuilder.append("\npermanenceValue: ");
	stringBuilder.append(this.permanenceValue);
	stringBuilder.append("\n    isConnected: ");
	if (this.getAbstractCell() == null) {
	    stringBuilder.append("false");
	} else {
	    stringBuilder.append("true");
	}
	stringBuilder.append("\n===========================");
	String synapseInformation = stringBuilder.toString();
	return synapseInformation;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result
		+ ((abstractCell == null) ? 0 : abstractCell.hashCode());
	long temp;
	temp = Double.doubleToLongBits(permanenceValue);
	result = prime * result + (int) (temp ^ (temp >>> 32));
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
	Stub_Synapse other = (Stub_Synapse) obj;
	if (abstractCell == null) {
	    if (other.abstractCell != null)
		return false;
	} else if (!abstractCell.equals(other.abstractCell))
	    return false;
	if (Double.doubleToLongBits(permanenceValue) != Double
		.doubleToLongBits(other.permanenceValue))
	    return false;
	return true;
    }
}