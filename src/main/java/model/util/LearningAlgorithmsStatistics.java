package model.util;

import model.MARK_II.Column;
import model.MARK_II.DistalSegment;
import model.MARK_II.Neuron;
import model.MARK_II.Region;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version June 22, 2014
 */
public class LearningAlgorithmsStatistics {
    /**
     * This number will be always increasing dramatically.
     */
    private int totalNumberOfDistalSegmentsInCurrentTimeStep;

    private int totalNumberOfPreviousActiveDistalSegmentsInCurrentTimeStep;
    private int totalNumberOfActiveDistalSegmentsInCurrentTimeStep;

    /**
     * As this number gets larger and larger in each time step it means
     * that the model is predicting future input more and more
     * successfully.
     */
    private int totalNumberOfSequenceSegmentsInCurrentTimeStep;

    public LearningAlgorithmsStatistics() {
        this.totalNumberOfDistalSegmentsInCurrentTimeStep = 0;
        this.totalNumberOfPreviousActiveDistalSegmentsInCurrentTimeStep = 0;
        this.totalNumberOfActiveDistalSegmentsInCurrentTimeStep = 0;
        this.totalNumberOfSequenceSegmentsInCurrentTimeStep = 0;
    }

    public void resetForNextTimeStep() {
        this.totalNumberOfDistalSegmentsInCurrentTimeStep = 0;
        this.totalNumberOfPreviousActiveDistalSegmentsInCurrentTimeStep = 0;
        this.totalNumberOfActiveDistalSegmentsInCurrentTimeStep = 0;
        this.totalNumberOfSequenceSegmentsInCurrentTimeStep = 0;
    }

    public void updateModelLearningMetrics(Region region) {
        Column[][] columns = region.getColumns();
        for (int row = 0; row < region.getNumberOfRowsAlongRegionYAxis(); row++) {
            for (int column = 0; column < region.getNumberOfColumnsAlongRegionXAxis(); column++) {
                for (Neuron neuron : columns[row][column].getNeurons()) {
                    for (DistalSegment distalSegment : neuron
                            .getDistalSegments()) {
                        this.totalNumberOfDistalSegmentsInCurrentTimeStep++;
                        if (distalSegment.getActiveState()) {
                            this.totalNumberOfActiveDistalSegmentsInCurrentTimeStep++;
                        }
                        if (distalSegment.getPreviousActiveState()) {
                            this.totalNumberOfPreviousActiveDistalSegmentsInCurrentTimeStep++;
                        }
                        if (distalSegment
                                .getSequenceStatePredictsFeedFowardInputOnNextStep()) {
                            this.totalNumberOfSequenceSegmentsInCurrentTimeStep++;
                        }
                    }
                }
            }
        }
    }

    public int getTotalNumberOfDistalSegmentsInCurrentTimeStep() {
        return this.totalNumberOfDistalSegmentsInCurrentTimeStep;
    }

    public int getTotalNumberOfPreviousActiveDistalSegmentsInCurrentTimeStep() {
        return this.totalNumberOfPreviousActiveDistalSegmentsInCurrentTimeStep;
    }

    public int getTotalNumberOfActiveDistalSegmentsInCurrentTimeStep() {
        return this.totalNumberOfActiveDistalSegmentsInCurrentTimeStep;
    }

    public int getTotalNumberOfSequenceSegmentsInCurrentTimeStep() {
        return this.totalNumberOfSequenceSegmentsInCurrentTimeStep;
    }

    @Override
    public String toString() {
        // NOTE: updateModelLearningMetrics(region) is called in the TemporalPooler.java
        // toString() method before calling this classes toString() method

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n=============================================");
        stringBuilder.append("\n------LearningAlgorithmStatistics Info-------");
        stringBuilder.append("\n      # of distalSegments in Region: ");
        stringBuilder.append(this.totalNumberOfDistalSegmentsInCurrentTimeStep);
        stringBuilder.append("\n         # of active distalSegments: ");
        stringBuilder.append(this.totalNumberOfActiveDistalSegmentsInCurrentTimeStep);
        stringBuilder.append("\n# of previous active distalSegments: ");
        stringBuilder.append(this.totalNumberOfPreviousActiveDistalSegmentsInCurrentTimeStep);
        stringBuilder.append("\n       # of sequence distalSegments: ");
        stringBuilder.append(this.totalNumberOfSequenceSegmentsInCurrentTimeStep);
        stringBuilder.append("\n=============================================");
        String temporalPoolerStatisticInformation = stringBuilder.toString();
        return temporalPoolerStatisticInformation;
    }
}
