package model;

import model.Stub_MARKII_Segment.Stub_SynapseUpdateState;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.MARKII_Segment.SynapseUpdateState;

public class Stub_MARKII_Column implements Serializable {
    // also stored as number of active synapses. This variable is created so
    // the active columns do not have to be recomputed during the same iteration of spatial pooling
    private boolean isActive;

    private Stub_MARKII_Neuron[] neurons;

    private Stub_MARKII_Segment<Stub_MARKII_AbstractCell> proximalSegment;

    // index position of chosen learning neuron within neurons array
    private int learningNeuronPosition; // may not be necessary
    private List<Stub_MARKII_Column> neighborColumns;

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

    public Stub_MARKII_Column(int numberOfCells) {
	this.neurons = new Stub_MARKII_Neuron[numberOfCells];
	for (int i = 0; i < numberOfCells; i++) {
	    this.neurons[i] = new Stub_MARKII_Neuron();
	}
	this.proximalSegment = new Stub_MARKII_Segment<Stub_MARKII_AbstractCell>();

	this.neighborColumns = new ArrayList<Stub_MARKII_Column>();

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
	    this.getProximalSegment().updateSynapsePermanences(Stub_SynapseUpdateState.INCREASE_ALL);
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
    public float maximumActiveDutyCycle(List<Stub_MARKII_Column> neighborColumns) {
	float maximumActiveDutyCycle = 0.0f;
	for (Stub_MARKII_Column column : neighborColumns) {
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

    public boolean getActiveState() {
        return this.isActive;
    }

    // -------------------Getters and Setters----------------------
    public void setActiveState(boolean activeState) {
	this.isActive = activeState;
    }

    public Stub_MARKII_Neuron[] getNeurons() {
	return this.neurons;
    }

    public boolean setNeurons(Stub_MARKII_Neuron[] neurons) {
	if (neurons != null) {
	    this.neurons = neurons;
	    return true;
	} else {
	    return false;
	}
    }

    public Stub_MARKII_Neuron getLearningNeuron() {
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

    public List<Stub_MARKII_Column> getNeighborColumns() {
	return this.neighborColumns;
    }

    public boolean setNeighborColumns(List<Stub_MARKII_Column> neighborColumns) {
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

    public boolean setActiveDutyCycle(float activeDutyCycle) {
	if (activeDutyCycle >= 0) {
	    this.activeDutyCycle = activeDutyCycle;
	    return true;
	} else {
	    return false;
	}
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

    public Stub_MARKII_Segment<Stub_MARKII_AbstractCell> getProximalSegment() {
	return this.proximalSegment;
    }

    public boolean setProximalSegment(Stub_MARKII_Segment<Stub_MARKII_AbstractCell> proximalSegment) {
	if (proximalSegment != null) {
	    this.proximalSegment = proximalSegment;
	    return true;
	} else {
	    return false;
	}
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
	Stub_MARKII_Column other = (Stub_MARKII_Column) obj;
	if (Float.floatToIntBits(activeDutyCycle) != Float
		.floatToIntBits(other.getActiveDutyCycle()))
	    return false;
	if (Float.floatToIntBits(boostValue) != Float
		.floatToIntBits(other.getBoostValue()))
	    return false;
	if (!Arrays.equals(neurons, other.getNeurons()))
	    return false;
	if (Float.floatToIntBits(overlapDutyCycle) != Float
		.floatToIntBits(other.getOverlapDutyCycle()))
	    return false;
	if (overlapScore != other.getOverlapScore())
	    return false;
	if (proximalSegment == null) {
	    if (other.getProximalSegment() != null)
		return false;
	} else if (!proximalSegment.equals(other.getProximalSegment()))
	    return false;
	return true;
    }
}
