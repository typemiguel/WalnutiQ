package model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Stub_MARKII_Neuron extends Stub_MARKII_AbstractCell implements Serializable {
    private boolean wasActive;
    private boolean isPredicting;
    private boolean wasPredicting;
    private Set<Stub_MARKII_Segment<Stub_MARKII_Neuron>> distalSegments;

    public Stub_MARKII_Neuron() {
	super(); // Initializes isActive state
	this.wasActive = false;
	this.isPredicting = false;
	this.wasPredicting = false;
	this.distalSegments = new HashSet<Stub_MARKII_Segment<Stub_MARKII_Neuron>>();
    }

    /**
     * Sets current active and predicting state to previous active and
     * predicting state. Then resets current active and predicting state to
     * false.
     */
    public void nextTimeStep() {
	this.wasActive = this.isActive;
	this.wasPredicting = this.isPredicting;
	this.isActive = false;
	this.isPredicting = false;
    }

    public boolean getPreviousActiveState() {
	return this.wasActive;
    }

    public void setPreviousActiveState(boolean previousActiveState) {
	this.wasActive = previousActiveState;
    }

    public boolean getPredictingState() {
	return this.isPredicting;
    }

    public void setPredictingState(boolean predictingState) {
	this.isPredicting = predictingState;
    }

    public boolean getPreviousPredictingState() {
	return this.wasPredicting;
    }

    public void setPreviousPredictingState(boolean previousPredictingState) {
	this.wasPredicting = previousPredictingState;
    }

    public Set<Stub_MARKII_Segment<Stub_MARKII_Neuron>> getDistalSegments() {
	return this.distalSegments;
    }

    public boolean setDistalSegments(Set<Stub_MARKII_Segment<Stub_MARKII_Neuron>> distalSegments) {
	if (distalSegments != null) {
	    this.distalSegments = distalSegments;
	    return true;
	} else {
	    return false;
	}
    }

    public boolean addDistalSegment(Stub_MARKII_Segment<Stub_MARKII_Neuron> distalSegment) {
	if (distalSegment != null) {
	    this.distalSegments.add(distalSegment);
	    return true;
	} else {
	    return false;
	}
    }

    @Override
    public String toString() {
	StringBuilder stringBuilder = new StringBuilder();
	stringBuilder.append("\n===========================");
	stringBuilder.append("\n--------Neuron Info--------");
	stringBuilder.append("\n          (x, y): ");
	stringBuilder.append("(" + super.getX() + ", " + super.getY() + ")");
	stringBuilder.append("\n            isActive: ");
	stringBuilder.append(this.isActive);
	stringBuilder.append("\n           wasActive: ");
	stringBuilder.append(this.wasActive);
	stringBuilder.append("\n        isPredicting: ");
	stringBuilder.append(this.isPredicting);
	stringBuilder.append("\n       wasPredicting: ");
	stringBuilder.append(this.wasPredicting);
	stringBuilder.append("\n# of distal segments: ");
	stringBuilder.append(this.distalSegments.size());
	stringBuilder.append("\n===========================");
	String NeuronInformation = stringBuilder.toString();
	return NeuronInformation;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result
		+ ((distalSegments == null) ? 0 : distalSegments.hashCode());
	result = prime * result + (isPredicting ? 1231 : 1237);
	result = prime * result + (wasActive ? 1231 : 1237);
	result = prime * result + (wasPredicting ? 1231 : 1237);
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
	Stub_MARKII_Neuron other = (Stub_MARKII_Neuron) obj;
	if (distalSegments == null) {
	    if (other.getDistalSegments() != null)
		return false;
	} else if (!distalSegments.equals(other.getDistalSegments()))
	    return false;
	if (isPredicting != other.getPredictingState())
	    return false;
	if (wasActive != other.getActiveState())
	    return false;
	if (wasPredicting != other.getPreviousPredictingState())
	    return false;
	return true;
    }
}
