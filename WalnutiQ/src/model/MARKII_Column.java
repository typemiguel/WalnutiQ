package model;

import java.util.Arrays;

import model.MARKII_Segment.SynapseUpdateState;

import java.io.Serializable;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

/**
 * A data structure that represents a single Column of neurons within a Region
 * object. A column contains a proximal segment and properties that determine
 * how the cells within this Column will be updated.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK II | April 4, 2013
 */
public class MARKII_Column implements Serializable {
    // also stored as number of active synapses. This variable is created so
    // the active columns do not have to be recomputed during the same iteration of spatial pooling
    private boolean isActive;

    private final MARKII_Neuron[] neurons;

    private final MARKII_Segment<MARKII_AbstractCell> proximalSegment;

    // index position of chosen learning neuron within neurons array
    private int learningNeuronPosition; // may not be necessary
    private List<MARKII_Column> neighborColumns;

    private int overlapScore;

    /**
     * value computed for a Column during learning. Always >= 1. Used to
     * increase the overlapScore for inactive Columns.
     */
    private float boostValue;

    /**
     * A sliding average representing how many times this Column has been active
     * after inhibition (e.g. over the past 1000 iterations of spatial learning)
     */
    private float activeDutyCycle;

    /**
     * A sliding average representing how many times this Column has had
     * significant overlapScore(greater than MINIMUM_OVERLAP)
     */
    private float overlapDutyCycle;

    /**
     * Used to compute the activeDutyCycle and overlapDutyCycle by decreasing it
     * exponentially less as the boostValue approached 0 and minimumOverlapScore
     * respectively.
     */
    public final static float EXPONENTIAL_MOVING_AVERAGE_AlPHA = 0.005f;

    public MARKII_Column(int numberOfCells) {
	this.neurons = new MARKII_Neuron[numberOfCells];
	for (int i = 0; i < numberOfCells; i++) {
	    this.neurons[i] = new MARKII_Neuron();
	}
	this.proximalSegment = new MARKII_Segment<MARKII_AbstractCell>();

	this.neighborColumns = new ArrayList<MARKII_Column>();

	this.overlapScore = 0;
	this.boostValue = 1.0f;
	this.activeDutyCycle = 1.0f; // Must be greater than 0, or will stay 0
	this.overlapDutyCycle = 1.0f; // Must be greater than 0, or will stay 0
    }

    /**
     * Increases the permenanceValue of every Synapse object in a Column's
     * proximal segment by "scaleFactor" number of times.
     *
     * @param scaleFactor
     *            Number of times to increasePermanences by PERMANENCE_INCREASE.
     */
    public void increaseProximalSegmentSynapsePermanences(int scaleFactor) {
	for (int i = 0; i < scaleFactor; i++) {
	    this.getProximalSegment().updateSynapsePermanences(SynapseUpdateState.INCREASE_ALL);
	}
    }

    /**
     * Returns the maximum activeDutyCycle within a given ArrayList of Column
     * objects.
     *
     * @param neighborColumns
     *            A list of Column objects.
     * @return The maximum acitveDutyCycle of a Column object.
     */
    public float maximumActiveDutyCycle(List<MARKII_Column> neighborColumns) {
	float maximumActiveDutyCycle = 0.0f;
	for (MARKII_Column column : neighborColumns) {
	    if (column.getActiveDutyCycle() > maximumActiveDutyCycle) {
		maximumActiveDutyCycle = column.getActiveDutyCycle();
	    }
	}
	return maximumActiveDutyCycle;
    }

    /**
     * Compute a moving average of how often this Column has been active after
     * inhibition. Exponential Moving Average(EMA): St = a * Yt + (1 - a) * St -
     * 1
     */
    public void updateActiveDutyCycle() {
	// newActiveDutyCylce is reduced less and less as it's value approaches
	// zero. However, newActiveDutyCylce is not always called but is always
	// increased by the maximum decreasable rate. Thus, a column's
	// activeDutyCycle has a upper bound of 1.
	float newActiveDutyCycle = (1.0f - EXPONENTIAL_MOVING_AVERAGE_AlPHA)
		* this.getActiveDutyCycle();
	if (this.getProximalSegment().getActiveState()) {
	    newActiveDutyCycle += EXPONENTIAL_MOVING_AVERAGE_AlPHA;
	}
	this.activeDutyCycle = newActiveDutyCycle;
    }



    /**
     * Computes the boostValue of a Column during learning.
     *
     * @param minimumDutyCycle
     *            Represents the minimum desired firing rate for a cell. If a
     *            cell's firing rate falls below this value, it will be boosted.
     * @return The boostValue of a Column.
     */
    // TODO: this method may only need to be in SpatialPooler
    public float boostFunction(float minimumDutyCycle) {
	if (minimumDutyCycle <= 0) {
	    throw new IllegalStateException(
		    "minimumDutyCycle cannot be less or equal to zero");
	}
	if (this.getActiveDutyCycle() <= minimumDutyCycle) {
	    // the boostValue increases linearly once the Column's
	    // activeDutyCycle becomes less than the minimumDutyCycle
	    return (minimumDutyCycle / this.getActiveDutyCycle());
	} else
	// this.activeDutyCycle > minimumDutyCycle
	{
	    return this.getActiveDutyCycle();
	}
    }

    /**
     * Reinitializes the activeState, overlapScore, neighborColumns, and
     * boostValue fields of this Column object to be recomputed.
     */
    public void nextTimeStep() {
	// TODO: nextTimeStep for neurons and proximalSegment
	this.overlapScore = 0;
	this.neighborColumns.clear();
	this.boostValue = 1.0f;
    }

    // TODO: no method to updatePermanences() proximalSegment synapses from
    // column
    // instead get proximalSegment then call updateSynapsePermanences()

    // no addConnectedSynapses()

    // -------------------Getters and Setters----------------------
    public void setActiveState(boolean activeState) {
	this.isActive = activeState;
    }

    public boolean getActiveState() {
	return this.isActive;
    }

    public MARKII_Neuron[] getNeurons() {
	return this.neurons;
    }

    public MARKII_Neuron getLearningNeuron() {
	return this.neurons[this.learningNeuronPosition];
    }

    public boolean setLearningNeuronPosition(int learningNeuronPosition) {
	if (learningNeuronPosition >= 0 && learningNeuronPosition <= this.neurons.length) {
	    this.learningNeuronPosition = learningNeuronPosition;
	    return true;
	} else {
	    return false;
	}
    }

    public List<MARKII_Column> getNeighborColumns() {
	return this.neighborColumns;
    }

    public boolean setNeighborColumns(List<MARKII_Column> neighborColumns) {
	if (neighborColumns.size() > 0) {
	    this.neighborColumns = neighborColumns;
	    return true;
	} else {
	    return false;
	}
    }

    public int getOverlapScore() {
	return this.overlapScore;
    }

    public boolean setOverlapScore(int overlapScore) {
	if (overlapScore >= 0
		&& overlapScore <= this.proximalSegment.getSynapses().size()) {
	    this.overlapScore = overlapScore;
	    return true;
	} else {
	    return false;
	}
    }

    public float getBoostValue() {
	return this.boostValue;
    }

    public boolean setBoostValue(float boostValue) {
	if (boostValue > 0) {
	    this.boostValue = boostValue;
	    return true;
	} else {
	    return false;
	}
    }

    public float getActiveDutyCycle() {
	return this.activeDutyCycle;
    }

    public float getOverlapDutyCycle() {
	return this.overlapDutyCycle;
    }

    public boolean setOverlapDutyCycle(float ovlapDutyCycle) {
	if (ovlapDutyCycle >= 0.0f) {
	    this.overlapDutyCycle = overlapDutyCycle;
	    return true;
	} else {
	    return false;
	}
    }

    public MARKII_Segment<MARKII_AbstractCell> getProximalSegment() {
	return this.proximalSegment;
    }

    @Override
    public String toString() {
	StringBuilder stringBuilder = new StringBuilder();
	stringBuilder.append("\n====================================");
	stringBuilder.append("\n------------Column Info-------------");
	stringBuilder.append("\n          number of neurons: ");
	stringBuilder.append(this.neurons.length);
	stringBuilder.append("\nproximalSegment activeState: ");
	stringBuilder.append(this.getProximalSegment().getActiveState());
	stringBuilder.append("\n     learning cell position: ");
	stringBuilder.append(this.learningNeuronPosition);
	stringBuilder.append("\n  number of neighborColumns: ");
	stringBuilder.append(this.neighborColumns.size());
	stringBuilder.append("\n               overlapScore: ");
	stringBuilder.append(this.getOverlapScore());
	stringBuilder.append("\n                 boostValue: ");
	stringBuilder.append(this.getBoostValue());
	stringBuilder.append("\n            activeDutyCycle: ");
	stringBuilder.append(this.getActiveDutyCycle());
	stringBuilder.append("\n           overlapDutyCycle: ");
	stringBuilder.append(this.getOverlapDutyCycle());
	stringBuilder.append("\n exponential moving average: ");
	stringBuilder.append(this.EXPONENTIAL_MOVING_AVERAGE_AlPHA);
	stringBuilder.append("\n=====================================");
	String columnInformation = stringBuilder.toString();
	return columnInformation;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + Float.floatToIntBits(activeDutyCycle);
	result = prime * result + Float.floatToIntBits(boostValue);
	result = prime * result + Arrays.hashCode(neurons);
	result = prime * result + Float.floatToIntBits(overlapDutyCycle);
	result = prime * result + overlapScore;
	result = prime * result
		+ ((proximalSegment == null) ? 0 : proximalSegment.hashCode());
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
	MARKII_Column other = (MARKII_Column) obj;
	if (Float.floatToIntBits(activeDutyCycle) != Float
		.floatToIntBits(other.activeDutyCycle))
	    return false;
	if (Float.floatToIntBits(boostValue) != Float
		.floatToIntBits(other.boostValue))
	    return false;
	if (!Arrays.equals(neurons, other.neurons))
	    return false;
	if (Float.floatToIntBits(overlapDutyCycle) != Float
		.floatToIntBits(other.overlapDutyCycle))
	    return false;
	if (overlapScore != other.overlapScore)
	    return false;
	if (proximalSegment == null) {
	    if (other.proximalSegment != null)
		return false;
	} else if (!proximalSegment.equals(other.proximalSegment))
	    return false;
	return true;
    }
}
