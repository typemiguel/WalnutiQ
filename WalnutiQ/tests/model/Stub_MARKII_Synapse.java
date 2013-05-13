package model;

import java.io.Serializable;

public class Stub_MARKII_Synapse<CellType extends Stub_MARKII_AbstractCell> implements
	Serializable {
    private CellType abstractCell;
    private double permanenceValue;

    // class level variables that can be changed for all synapses
    /**
     * Amount permanenceValue is increased during one iteration of learning.
     */
    public static final double PERMANCE_INCREASE = 0.015;

    /**
     * Amount permanenceValue is decreased during one iteration of learning.
     */
    public static final double PERMANCE_DECREASE = 0.005;

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
    public Stub_MARKII_Synapse(CellType abstractCell, double initialPermanence) {
	this.abstractCell = abstractCell;
	this.permanenceValue = initialPermanence;
    }

    /**
     * Create a new Synapse object with an AbstractCell object and
     * INITIAL_PERMANENCE value.
     *
     * @param abstractCell
     *            The AbstractCell object that will be providing data for a
     *            Synapse object.
     */
    public Stub_MARKII_Synapse(CellType abstractCell) {
	this.abstractCell = abstractCell;
	this.permanenceValue = INITIAL_PERMANENCE;
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

    public boolean setAbstractCell(CellType abstractCell) {
	if (abstractCell != null) {
	    this.abstractCell = abstractCell;
	    return true;
	} else {
	    return false;
	}

    }

    public double getPermanenceValue() {
	return this.permanenceValue;
    }

    public boolean setPermanenceValue(double permanenceValue) {
	if (permanenceValue >= 0.0 && permanenceValue <= 1.0) {
	    this.permanenceValue = permanenceValue;
	    return true;
	} else {
	    return false;
	}
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
		+ PERMANCE_INCREASE);
    }

    /**
     * Decrease the permanenceValue of a Synapse object by PERMANENCE_DECREASE
     * while forcing permanenceValue to always be > 0.0.
     */
    public void decreasePermanence() {
	this.permanenceValue = Math.max(0.0, this.permanenceValue
		- PERMANCE_DECREASE);
    }

    @Override
    public String toString() {
	StringBuilder stringBuilder = new StringBuilder();
	Stub_MARKII_AbstractCell cell = this.getAbstractCell();
	stringBuilder.append("\n============================");
	stringBuilder.append("\n----Synapse Information-----");
	stringBuilder.append("\n       Connected to a: ");
	stringBuilder.append("this.abstractCell.toString()");
	stringBuilder.append("\n      permanenceValue: ");
	stringBuilder.append(this.permanenceValue);
	stringBuilder.append("\n          isConnected: ");
	if (this.getAbstractCell() == null) {
	    stringBuilder.append("false");
	} else {
	    stringBuilder.append("true");
	}
	stringBuilder.append("\n============================");
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
	Stub_MARKII_Synapse other = (Stub_MARKII_Synapse) obj;
	if (abstractCell == null) {
	    if (other.getAbstractCell() != null)
		return false;
	} else if (!abstractCell.equals(other.getAbstractCell()))
	    return false;
	if (Double.doubleToLongBits(permanenceValue) != Double
		.doubleToLongBits(other.getPermanenceValue()))
	    return false;
	return true;
    }
}
