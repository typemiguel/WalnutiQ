package model.MARK_II;

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
 * MARKII Synapse data structure models the following neuroscience ideas:
 * 1) synaptic plasticity(Hebbian plasticity)
 * 2) longterm potentiation/depression
 *
 * NOTE: When getting the activeState or previousActiveState of a Synapse make
 * sure to check that the Synapse is connected to it's abstractCell by calling
 * synapse.getConnectedState()
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @author Michael Cogswell (cogswell@vt.edu)
 * @version MARK II | May 14, 2013
 * @param <GenericType>
 *            A Synapse can be connected to either a SensorCell or a Neuron.
 */
public class Synapse<CellType extends Cell> implements
	Serializable {
    private CellType abstractCell;
    private double permanenceValue;
    private int abstractCellXPosition;
    private int abstractCellYPosition;

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
    public Synapse(CellType abstractCell, double initialPermanence, int abstractCellXPosition, int abstractCellYPosition) {
	if (abstractCell == null) {
	    throw new IllegalArgumentException(
		    "abstractCell in Synapse constructor cannot be null");
	} else if (initialPermanence < 0.0 || initialPermanence > 1.0) {
	    throw new IllegalArgumentException(
		    "initialPermanence in Synapse constructor must be between 0 and 1");
	}
	this.abstractCell = abstractCell;
	this.permanenceValue = initialPermanence;
	this.abstractCellXPosition = abstractCellXPosition;
	this.abstractCellYPosition = abstractCellYPosition;
    }

    /**
     * Create a new Synapse object with an AbstractCell object and
     * INITIAL_PERMANENCE value.
     *
     * @param abstractCell
     *            The AbstractCell object that will be providing data for a
     *            Synapse object.
     */
    public Synapse(CellType abstractCell, int abstractCellXPosition, int abstractCellYPosition) {
	if (abstractCell == null) {
	    throw new IllegalArgumentException(
		    "abstractCell in Synapse constructor cannot be null");
	} else {
	    this.abstractCell = abstractCell;
	    this.permanenceValue = INITIAL_PERMANENCE;
	}
	this.abstractCellXPosition = abstractCellXPosition;
	this.abstractCellYPosition = abstractCellYPosition;
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

    public int getAbstractCellXPosition() {
	return this.abstractCellXPosition;
    }

    public int getAbstractCellYPosition() {
	return this.abstractCellYPosition;
    }

    @Override
    public String toString() {
	StringBuilder stringBuilder = new StringBuilder();
	Cell cell = this.getAbstractCell();
	stringBuilder.append("\n===========================");
	stringBuilder.append("\n----Synapse Information----");
	stringBuilder.append("\n Connected to a: ");
	stringBuilder.append(this.abstractCell.toString());
	stringBuilder.append("\n Located at: ");
	stringBuilder.append("(" + this.abstractCellXPosition + ", ");
	stringBuilder.append(this.abstractCellYPosition + ")");
	stringBuilder.append("\npermanenceValue: ");
	stringBuilder.append(this.permanenceValue);
	stringBuilder.append("\n    isConnected: ");
	if (this.getAbstractCell() == null) {
	    stringBuilder.append("false");
	} else {
	    stringBuilder.append("true");
	};
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
	Synapse other = (Synapse) obj;
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
