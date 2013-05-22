package model.MARK_II;
//package model;
//
//import java.util.HashSet;
//
//import java.util.Set;
//
///**
// * A data structure that acts as a threshold detector for active
// * synapses. If a distal segment has enough active synapses at a time
// * step t, then the distal segment also becomes active. A Neuron's predictiveState
// * is not considered in distal segments because a distal segment should not
// * propagate predictions.
// *
// * @author Quinn Liu (quinnliu@vt.edu)
// * @version MARK II | March 23, 2013
// */
//public class TEMPDistalSegment extends MARKII_Segment {
//    protected final Set<MARKII_Synapse<MARKII_Neuron>> synapses;
//    private boolean sequenceState;
//    private int numberOfPredictionSteps;
//
//    /**
//     * The maximum number of predictions into the future that can be generated.
//     */
//    public static final int MAXIMUM_TIME_STEPS = 10;
//
//    public TEMPDistalSegment() {
//	this.synapses = new HashSet<MARKII_Synapse<MARKII_Neuron>>();
//	this.sequenceState = false;
//	this.numberOfPredictionSteps = 1;
//    }
//
//    public boolean getSequenceState() {
//        return this.sequenceState;
//    }
//
//    public void setSequenceState(boolean sequenceState) {
//        this.sequenceState = sequenceState;
//    }
//
//    public int getNumberOfPredictionSteps() {
//	return this.numberOfPredictionSteps;
//    }
//
//    public void setNumberOfPredictionSteps(int numberOfPredictionSteps) {
//	this.numberOfPredictionSteps = Math.min(
//		Math.max(1, numberOfPredictionSteps),
//		TEMPDistalSegment.MAXIMUM_TIME_STEPS);
//	this.setSequenceState(numberOfPredictionSteps == 1);
//    }
//
//    @Override
//    public String toString() {
//	StringBuilder stringBuilder = new StringBuilder();
//	stringBuilder.append("\n=============================");
//	stringBuilder.append("\n-----DistalSegment Info------");
//	stringBuilder.append("\n        sequenceSegment: ");
//	stringBuilder.append(this.getSequenceState());
//	stringBuilder.append("\nnumberOfPredictionSteps: ");
//	stringBuilder.append(this.getNumberOfPredictionSteps());
//	stringBuilder.append("\n            activeState: ");
//	stringBuilder.append(this.getActiveState());
//	stringBuilder.append("\n    previousActiveState: ");
//	stringBuilder.append(this.getPreviousActiveState());
//	stringBuilder.append("\n     number of synapses: ");
//	stringBuilder.append(this.getSynapses().size());
//	stringBuilder.append("\n      connectedSynapses: ");
//	stringBuilder.append(this.getConnectedSynapses().size());
//	stringBuilder.append("\n         activeSynapses: ");
//	stringBuilder.append(this.getActiveSynapses().size());
//	stringBuilder.append("\n previousActiveSynapses: ");
//	stringBuilder.append(this.getPreviousActiveSynapses().size());
//	stringBuilder.append("\n=============================");
//	String segmentInformation = stringBuilder.toString();
//	return segmentInformation;
//    }
//}
