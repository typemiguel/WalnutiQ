package model.MARK_II;

import java.io.Serializable;

/**
 * A data structure that partly represents the connection strength from a
 * Segment to an Cell. Each Synapse has an Cell and has a permanenceValue. A
 * Synapse with permanenceValue >= 0.2 means that it is connected to an Cell.
 * This creates a binary connection from a Synapse to an Cell. For example a
 * permanenceValue of 0.19 means a Synapse is not connected to an Cell whereas a
 * permanenceValue of 0.205 does. Furthermore, A permanceValue of 0.9 will only
 * be considered by the algorithm to be connected. The idea is that the
 * connection is now very "permanent" and not easy to forget. This
 * permanenceValue implementation strategy was done to make the learning
 * algorithms involving Synapses more efficient and minimally reduces the
 * learning algorithms performance.
 *
 * MARKII Synapse data structure models the following neuroscience ideas: 1)
 * synaptic plasticity(Hebbian plasticity) 2) longterm potentiation/depression
 *
 * NOTE: When getting the activeState or previousActiveState of a Synapse make
 * sure to check that the Synapse is connected to it's Cell by calling
 * synapse.getConnectedState()
 *
 * Input of Synapse: activity state of Cell.
 *
 * Output of Synapse: whether this Synapse is connected or not to the Cell.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @author Michael Cogswell (cogswell@vt.edu)
 * @version MARK II | May 14, 2013
 * @param <GenericType>
 *            A synapse can be connected to either a sensorCell or a neuron.
 */
public class Synapse<CellType extends Cell> implements Serializable {
    private CellType cell;
    private double permanenceValue;
    private int cellXPosition;
    private int cellYPosition;

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
     * Create a new synapse object with an Cell object and given permanence
     * value.
     *
     * @param cell
     *            The Cell object that will be providing data for a Synapse
     *            object.
     */
    public Synapse(CellType cell, double initialPermanence,
	    int abstractCellXPosition, int abstractCellYPosition) {
	if (cell == null) {
	    throw new IllegalArgumentException(
		    "cell in Synapse class constructor cannot be null");
	} else if (initialPermanence < 0.0 || initialPermanence > 1.0) {
	    throw new IllegalArgumentException(
		    "initialPermanence in Synapse class constructor must be between 0 and 1");
	}
	this.cell = cell;
	this.permanenceValue = initialPermanence;
	this.cellXPosition = abstractCellXPosition;
	this.cellYPosition = abstractCellYPosition;
    }

    /**
     * Create a new Synapse object with an Cell object and INITIAL_PERMANENCE
     * value.
     *
     * @param cell
     *            The Cell object that will be providing data for a Synapse
     *            object.
     */
    public Synapse(CellType cell, int cellXPosition, int cellYPosition) {
	if (cell == null) {
	    throw new IllegalArgumentException(
		    "cell in Synapse class constructor cannot be null");
	} else {
	    this.cell = cell;
	    this.permanenceValue = INITIAL_PERMANENCE;
	}
	this.cellXPosition = cellXPosition;
	this.cellYPosition = cellYPosition;
    }

    /**
     * @return This Synapse's Cell if it is connected. Otherwise return null.
     */
    public CellType getCell() {
	if (this.isConnected()) {
	    return this.cell;
	} else {
	    return null;
	}
    }

    /**
     * @return true if this Synapses' permanenceValue is greater than the
     *         MINIMAL_CONNECTED_PERMANCE represented the Synapse and Cell are
     *         connected.
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

    public int getCellXPosition() {
	return this.cellXPosition;
    }

    public int getCellYPosition() {
	return this.cellYPosition;
    }

    @Override
    public String toString() {
	StringBuilder stringBuilder = new StringBuilder();
	Cell cell = this.getCell();
	stringBuilder.append("\n===========================");
	stringBuilder.append("\n----Synapse Information----");
	stringBuilder.append("\n Connected to a: ");
	stringBuilder.append(this.cell.toString());
	stringBuilder.append("\n Located at: ");
	stringBuilder.append("(" + this.cellXPosition + ", ");
	stringBuilder.append(this.cellYPosition + ")");
	stringBuilder.append("\npermanenceValue: ");
	stringBuilder.append(this.permanenceValue);
	stringBuilder.append("\n    isConnected: ");
	if (this.getCell() == null) {
	    stringBuilder.append("false");
	} else {
	    stringBuilder.append("true");
	}
	;
	stringBuilder.append("\n===========================");
	String synapseInformation = stringBuilder.toString();
	return synapseInformation;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((cell == null) ? 0 : cell.hashCode());
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
	if (cell == null) {
	    if (other.cell != null)
		return false;
	} else if (!cell.equals(other.cell))
	    return false;
	if (Double.doubleToLongBits(permanenceValue) != Double
		.doubleToLongBits(other.permanenceValue))
	    return false;
	return true;
    }
}
