package model;

import java.util.HashSet;
import java.util.Set;
import java.util.LinkedList;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// -------------------------------------------------------------------------
/**
 * Represents a Hierarchical Temporal Memory(HTM) sequence Cell object within a
 * Column object containing 1 or more Cell objects.
 *
 * @author Quinn Liu
 * @version Sep 14, 2012
 */
public class Cell
    extends AbstractCell
    implements Serializable
{
    private int                            predictionSteps;

    private boolean                        predictingState;
    private boolean                        previousPredictingState;                  // TemporalPooler

    private List<Segment>                  listOfDistalSegments;

    // TODO: test
    private Segment                        bestSegment;                              // TemporalPooler

    private List<SegmentUpdateInformation> segmentUpdateInformation;

    /**
     * Used in method...
     */
    public static final int                MINIMUM_SYNAPSES_PER_DISTAL_SEGMENT =
                                                                                   1;


    // TODO: HashSet of connected distalSegments?, inactive, and active ...
    // have add Segment method add them to all appropriate HashSets

    // ----------------------------------------------------------
    /**
     * Create a new Cell object.
     *
     * @param column
     * @param layerIndexInColumn
     */
    public Cell(Column column, int layerIndexInColumn)
    {
        super(column, layerIndexInColumn);
        this.predictionSteps = 0;
        this.predictingState = false;
        this.previousPredictingState = false;
        this.learningState = false;
        this.previousLearningState = false;
        this.listOfDistalSegments = new ArrayList<Segment>(5);
        this.bestSegment = null;
        this.segmentUpdateInformation =
            new LinkedList<SegmentUpdateInformation>();
    }


    // =====================Temporal Pooler Methods============================
    // TODO: test
    public boolean getPreviousPredictingState()
    {
        return this.previousPredictingState;
    }


    /**
     * This method is exhaustive in finding the best matching Segment by
     * searching unconnected Synapses and unactive Segments.
     *
     * @param numberPredictionSteps
     * @param previous
     * @return Segment with the greatest number of active Synapses. Otherwise;
     *         return null if no Segments are predicting Cell activation at the
     *         desired time step.
     */
    public Segment getBestMatchingSegment(
        int numberPredictionSteps,
        boolean previous)
    {
        int greatestNumberOfActiveSynapses =
            MINIMUM_SYNAPSES_PER_DISTAL_SEGMENT;
        for (Segment segment : this.getListOfDistalSegments())
        {
            if (segment.getNumberOfPredictionSteps() == numberPredictionSteps)
            {
                int numberOfActiveSynapses = 0;
                if (previous)
                {
                    numberOfActiveSynapses =
                        segment.getPreviousActiveSynapses().size();
                }
                else
                {
                    numberOfActiveSynapses = segment.getActiveSynapses().size();
                }

                if (numberOfActiveSynapses > greatestNumberOfActiveSynapses)
                {
                    greatestNumberOfActiveSynapses = numberOfActiveSynapses;
                    this.setBestSegment(segment);
                }
            }
        }
        return this.getBestSegment();
    }


    // TODO: test
    /**
     * Return a Segment that was active in the previous time step. If more than
     * one Segment was was active, sequence segments are considered, and finally
     * if more than one sequence segment that was active in the previous time
     * step, the segment with the most activity is returned.
     *
     * @return A Segment that was active in the previous time step.
     */
    public Segment getPreviousActiveSegment()
    {
        List<Segment> previousActiveSegments = new ArrayList<Segment>();
        for (Segment segment : this.listOfDistalSegments)
        {
            if (segment.getPreviousActiveState())
            {
                previousActiveSegments.add(segment);
            }
        }

        if (previousActiveSegments.size() == 0)
        {
            return null;
        }
        else if (previousActiveSegments.size() == 1)
        {
            return previousActiveSegments.get(0);
        }
        else
        // more than one Segment in List
        {
            // sequence Segments are given the priority
            List<Segment> previousActiveSequenceSegments =
                new ArrayList<Segment>();
            for (Segment segment : previousActiveSegments)
            {
                if (segment.getSequenceState())
                {
                    previousActiveSequenceSegments.add(segment);
                }
            }

            if (previousActiveSequenceSegments.size() == 1)
            {
                return previousActiveSequenceSegments.get(0);
            }
            else if (previousActiveSequenceSegments.size() > 1)
            {
                // now give priority to the Segment with the most activity
                Segment bestSegment = previousActiveSequenceSegments.get(0);
                int maximumPreviousActiveSynapses =
                    bestSegment.getPreviousActiveSynapses().size();
                for (Segment segment : previousActiveSequenceSegments)
                {
                    int previousActiveSynapses =
                        segment.getPreviousActiveSynapses().size();
                    if (maximumPreviousActiveSynapses < previousActiveSynapses)
                    {
                        maximumPreviousActiveSynapses = previousActiveSynapses;
                        bestSegment = segment;
                    }
                }
                return bestSegment;
            }
            else
            // no sequence segments TODO: make more efficient
            {
                // you do not need to return the Segment with the most
                // activity because a Segment must be a sequence segment for
                // spatialPoolerOutputPredicted to become true
                return null;
            }
        }
    }


    public int getPredictionSteps()
    {
        return this.predictionSteps;
    }


    public void setPredictionSteps(int predictionSteps)
    {
        // Ensures this.predictionSteps is set between 1 and maximum time steps
        // inclusive.
        this.predictionSteps =
            Math.min(Math.max(1, predictionSteps), Segment.MAXIMUM_TIME_STEPS);
    }


    void updatePredictionSteps()
    {
        if (this.predictingState)
        {
            // each Cell object has a unique number of prediction steps
            this.predictionSteps = Segment.MAXIMUM_TIME_STEPS;

            for (Segment segment : this.listOfDistalSegments)
            {
                if (segment.getActiveState()
                    && segment.getNumberOfPredictionSteps() < this.predictionSteps)
                    ;
                {
                    this.predictionSteps = segment.getNumberOfPredictionSteps();
                }
            }
        }
    }


    public boolean getPredictingState()
    {
        return this.predictingState;
    }


    public void setPredictingState(boolean predictingState)
    {
        this.predictingState = predictingState;
    }


    public List<Segment> getListOfDistalSegments()
    {
        return this.listOfDistalSegments;
    }


    public Segment getBestSegment()
    {
        return this.bestSegment;
    }


    public void setBestSegment(Segment bestSegment)
    {
        this.bestSegment = bestSegment;
    }


    /**
     * Return a object containing proposed changes for a specific Segment.
     *
     * @param previous
     * @param segment
     *            If segment parameter is null, a new empty Segment is created
     *            and added to this Cell.
     * @param addNewSynapsesFromCurrentLearningCells
     * @return A SegmentUpdateInformation object containing proposed changes for
     *         a specific Segment.
     */
    SegmentUpdateInformation updateSegmentActiveSynapses(
        boolean previous,
        Segment segment,
        boolean addNewSynapsesFromCurrentLearningCells)
    {
        Set<Synapse> activeSynapses = new HashSet<Synapse>();
        if (segment != null)
        {
            // active Synapses in this or previous time step need to be
            // updated.
            if (previous)
            {
                activeSynapses = segment.getPreviousActiveSynapses();
            }
            else
            {
                activeSynapses = segment.getActiveSynapses();
            }
        }

        // a new empty Segment is created and added to this Cell
        SegmentUpdateInformation segmentUpdateInformation =
            new SegmentUpdateInformation(
                this,
                segment,
                activeSynapses,
                addNewSynapsesFromCurrentLearningCells);
        // this added Segment update is only applied when the
// applySegmentUpdates
        // method is called on this Cell
        this.segmentUpdateInformation.add(segmentUpdateInformation);
        return segmentUpdateInformation;
    }


    /**
     * Process each Segment in this Cell's SegmentUpdateInformation. The
     * following changes are performed based on the information within
     * SegmentUpdateInformation:
     * 1) If positiveReinforcement is true, every
     * Synapse in activeSynapses gets permanenceValue increased.
     * 2) If positiveReinforcement is false, every Synapse in activeSynapses gets
     * permanenceValue decreased.
     * 3) Synapses in set newSynapsesToAdd are ...
     * TODO:
     */
    public void applySegmentUpdates(boolean positiveReinforcement)
    {
        for (SegmentUpdateInformation segmentUpdateInformation : this.segmentUpdateInformation)
        {
            Segment segment = segmentUpdateInformation.getSegment();

            if (segment != null)
            {
                if (positiveReinforcement)
                {
                    // increase Segment's active Synapse permanence values
                    segment.updatePermanencesInSet(segmentUpdateInformation.getActiveSynapses());
                }
                else
                {
                    // decrease Segment's active Synapse permanence values
                    segment.decreasePermanencesInSet(segmentUpdateInformation.getActiveSynapses());
                }
            }

            // add new Synapse and a new Segment if necessary
            if (segmentUpdateInformation.getAddNewSynapsesState() && positiveReinforcement)
            {
                if (segment == null)
                {
                    // only add new Synapses (containing learning Cells) if
                    // learning Cells are available
                    if (segmentUpdateInformation.getLearningCells().size() > 0)
                    {
                        // TODO: following method calls many other methods
                        segment = segmentUpdateInformation.createNewSegmentConnectedToPreviousLearningCells();
                    }
                    else if (segmentUpdateInformation.getLearningCells().size() > 0)
                    {
                        // add new Synapse to existing Segment
                        //segmentUpdateInformation.createSynapsesToLearningCells();
                    }
                }
            }
        }
        // remove all Segment update information after being applied
        this.segmentUpdateInformation.clear();
    }


    @Override
    public boolean getDistalState()
    {
        return true;
    }


    @Override
    public void nextTimeStep()
    {
        this.previousActiveState = this.activeState;
        this.previousPredictingState = this.predictingState;
        this.previousLearningState = this.learningState;
        this.activeState = false;
        this.predictingState = false;
        this.learningState = false;
        for (Segment distalSegment : this.listOfDistalSegments)
        {
            distalSegment.nextTimeStep();
        }
    }


    // ========================================================================

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Cell cell = (Cell)o;

        if (activeState != cell.activeState)
            return false;
        if (learningState != cell.learningState)
            return false;
        if (predictingState != cell.predictingState)
            return false;
        if (predictionSteps != cell.predictionSteps)
            return false;
        if (previousActiveState != cell.previousActiveState)
            return false;
        if (previousLearningState != cell.previousLearningState)
            return false;
        if (previousPredictingState != cell.previousPredictingState)
            return false;
        if (columnIndex != cell.columnIndex)
            return false;
        if (x != cell.x)
            return false;
        if (y != cell.y)
            return false;
        if (columnIndex != cell.columnIndex)
            return false;
        if (!listOfDistalSegments.equals(cell.listOfDistalSegments))
            return false;

        return true;
    }


    @Override
    public int hashCode()
    {
        int result = 0;
        result += 2 * x;
        result += 3 * y;
        result += 5 * columnIndex;
        result += 7 * (activeState ? 1 : 0);
        result += 11 * (previousActiveState ? 1 : 0);
        result += 13 * predictionSteps;
        result += 17 * (predictingState ? 1 : 0);
        result += 19 * (previousPredictingState ? 1 : 0);
        result += 23 * (learningState ? 1 : 0);
        result += 29 * (previousLearningState ? 1 : 0);
        result += 31 * listOfDistalSegments.hashCode();
        return result;
    }
}
