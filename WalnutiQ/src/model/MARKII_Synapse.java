package model;

import java.io.Serializable;

/**
 * A data structure that partly represents the connection strength from a
 * Segment to an AbstractCell. Each Synapse has an AbstractCell and has a
 * permanenceValue. A Synapse with permanenceValue >= 0.2 means that it is
 * connected to an AbstractCell. This creates a binary connection from a Synapse
 * to an AbstractCell. For example a permanenceValue of 0.19 means a Synapse is
 * not connected to an AbstractCell whereas a permanenceValue of 0.205 does.
 * Furthermore, A permanceValue of 0.9 will only be considered by the algorithm
 * to be connected. The idea is that the connection is now very "permanent" and
 * not easy to forget. This permanenceValue implementation strategy was done to
 * make the learning algorithms involving Synapses more efficient and minimally
 * reduces the learning algorithms performance.
 *
 * NOTE: When getting the activeState or previousActiveState of a Synapse make
 * sure to check that the Synapse is connected to it's abstractCell by calling
 * synapse.getConnectedState()
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @author Michael Cogswell (cogswell@vt.edu)
 * @version MARK II | April 4, 2013
 * @param <GenericType>
 *            A Synapse can be connected to either a SensorCell or a Neuron.
 */
public class MARKII_Synapse<CellType extends MARKII_AbstractCell> implements
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
     * Create a new Synapse object with an AbstractCell object and
     * given permanence value.
     *
     * @param abstractCell
     *            The AbstractCell object that will be providing data for a
     *            Synapse object.
     */
    public MARKII_Synapse(CellType abstractCell, double initialPermanence) {
	this.abstractCell = abstractCell;
	this.setPermanenceValue(initialPermanence);
    }

    /**
     * Create a new Synapse object with an AbstractCell object and
     * INITIAL_PERMANENCE value.
     *
     * @param abstractCell
     *            The AbstractCell object that will be providing data for a
     *            Synapse object.
     */
    public MARKII_Synapse(CellType abstractCell) {
	this.abstractCell = abstractCell;
	this.setPermanenceValue(INITIAL_PERMANENCE);
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

    /**
     * @return true if this synapses' permanenceValue is greater than the
     * MINIMAL_CONNECTED_PERMANCE represented the synapse and abstract cell are
     * connected.
     */
    public boolean isConnected()
    {
	return (this.getPermanenceValue() >= MINIMAL_CONNECTED_PERMANCE);
    }

    /**
     * Increase the permanenceValue of a Synapse object by PERMANENCE_INCREASE
     * while forcing permanenceValue to always be < 1.0.
     */
    public void increasePermance() {
	this.setPermanenceValue(Math.min(1.0, this.getPermanenceValue()
		+ PERMANCE_INCREASE));
    }

    /**
     * Decrease the permanenceValue of a Synapse object by PERMANENCE_DECREASE
     * while forcing permanenceValue to always be > 0.0.
     */
    public void decreasePermance() {
	this.setPermanenceValue(Math.max(0.0, this.getPermanenceValue()
		- PERMANCE_DECREASE));
    }

    @Override
    public String toString() {
	StringBuilder stringBuilder = new StringBuilder();
	MARKII_AbstractCell cell = this.getAbstractCell();
	stringBuilder.append("\n============================");
	stringBuilder.append("\n----Synapse Information-----");
	stringBuilder.append("\n       Connected to a: ");
	stringBuilder.append("this.abstractCell.toString()");
	stringBuilder.append("\n      permanenceValue: ");
	stringBuilder.append(this.getPermanenceValue());
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
	temp = Double.doubleToLongBits(getPermanenceValue());
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
	MARKII_Synapse other = (MARKII_Synapse) obj;
	if (abstractCell == null) {
	    if (other.abstractCell != null)
		return false;
	} else if (!abstractCell.equals(other.abstractCell))
	    return false;
	if (Double.doubleToLongBits(getPermanenceValue()) != Double
		.doubleToLongBits(other.getPermanenceValue()))
	    return false;
	return true;
    }

    public double getPermanenceValue() {
	return permanenceValue;
    }

    public void setPermanenceValue(double permanenceValue) {
	this.permanenceValue = permanenceValue;
    }
}
