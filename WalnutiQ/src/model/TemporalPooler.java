package model;

import java.util.HashSet;
import java.util.Set;

// -------------------------------------------------------------------------
/**
 * Provides a method to implement a temporal pooler algorithm on a Region
 * object.
 *
 * @author Quinn
 * @version Nov 17, 2012
 */
public class TemporalPooler
{
    private Region      region;
    /**
     * List of activeColumns created from SpatialPooler object to be used as
     * input for the TemporalPooler.
     */
    private Set<Column> activeColumns;


    // ----------------------------------------------------------
    /**
     * Create a new TemporalPooler object.
     *
     * @param region
     * @param activeColumns
     */
    public TemporalPooler(Region region, Set<Column> activeColumns)
    {
        this.region = region;
        this.activeColumns = activeColumns;
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     *
     * @return A region object for a TemporalPooler object.
     */
    public Region getRegion()
    {
        return this.region;
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     *
     * @param region
     */
    public void setRegion(Region region)
    {
        this.region = region;
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     *
     * @return A HashSet of activeColumns for a TemporalPooler object.
     */
    public HashSet<Column> getActiveColumns()
    {
        return (HashSet<Column>)this.activeColumns;
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     *
     * @param activeColumns
     */
    public void setActiveColumns(HashSet<Column> activeColumns)
    {
        this.activeColumns = activeColumns;
    }


    /**
     * Region fields inhibitionRadius, minimumOverlapScore, and temporalLearning
     * change. All other Region fields remain constant.
     */
    public void nextTimeStepForTemporalPooler()
    {

    }

    /**
     * //Assert that this TemporalPooler object's activeColumns
        //are the most up to date

        // NOTE: The SpatialPooler object's active Columns are not
        // always predicted, but a learning cell is always chosen if
        // the Region object is temporalLearning.
     */
    private void computeCellActiveAndLearningStatesWithinActiveColumns()
    {
        // Part 1: Compute the activeState for each Cell object
        // in SpatialPooler object output. Pick a Cell in each
        // Column object to become the learning Cell.
        for (Column column : this.activeColumns)
        {
            // if the SpatialPooler object output was predicted by
            // any Cell object in each Column object then those Cell(s)
            // become active.
            boolean spatialPoolerOutputPredicted = false;
            boolean learningCellChosen= false;
            for(Cell cell : column.getCells())
            {
                // if a Cell's segment is a sequence segment and
                // if the Cell's previousPredictingState was switched
                // to true due to the previous run of performTemporalPooling()
                if(cell.getPreviousPredictingState())
                {
                    // this Cell was predicted before and now we need to
                    // determine which Segment turned on this Cell's predictingState
                    // during the previous run of performTemporalPooling()

                    // the most appropriate Segment is returned if more than one
                    Segment segment = cell.getPreviousActiveSegment();

                    if (segment != null && segment.getSequenceState())
                    {
                        spatialPoolerOutputPredicted = true;
                        cell.setActiveState(true);

                        if(this.region.getTemporalLearning() && segment.previousActiveStateFromLearning())
                        {
                            learningCellChosen = true;
                            cell.setLearningState(true);
                        }
                    }
                }
            }

            // if SpatialPooler object's output active columns where not predicted
            // then all Cell object in each active Column object become active
            // and the best matching Cell object is chosen to be the learning
            // Cell.
            if (!spatialPoolerOutputPredicted)
            {
                for (Cell cell : column.getCells())
                {
                    cell.setActiveState(true);
                }
            }

            if (this.region.getTemporalLearning() && !learningCellChosen)
            {
                // also calls method to compute the best matching Segment within best matching Cell
                Cell bestMatchingCell = column.getBestMatchingCell(1, true);

                Segment bestMatchingSegment = bestMatchingCell.getBestSegment();

                bestMatchingCell.setLearningState(true);

                // bestMatchingSegment is added to queue of updates need to be
                // performed if learning before the next run of temporal pooling on new
                // input data
                // TODO: implement class SegmentUpdateInformation

                // what exactly needs to be stored for updating?

                // update best matching segment active synapses.

                // TODO: Enforce maximum number of Segments per Cell? 5,000?
            }
        }
    }

    /**
     * Compute the predictionState for each Cell. A Cell object will turn on
     * predictionState iff one of it's Segment objects become active due to
     * Synapses activating.
     */
    private void computeCellPredictionStatesWithinActiveColumns()
    {
        for (Column column : this.activeColumns)
        {
            // The Cell queues up the following changes when a Segment becomes active:
            // a) The active Segment is reinforced
            // b) a Segment that could have predicted this Cell's activation is reinforced
            for (Cell cell : column.getCells())
            {
                for (Segment segment : cell.getListOfDistalSegments())
                {
                    // update Segment activeSynapses and activeState to be used
                    segment.processCurrentTimeStep();
                }

                for (Segment segment : cell.getListOfDistalSegments())
                {
                    if (segment.getActiveState())
                    {
                        cell.setPredictingState(true);
                        cell.updatePredictionSteps();

                        // a) reinforcement of the currently active Segment
                        if (this.region.getTemporalLearning())
                        {
                            // update segment active synapses
                            // add to queue
                        }
                        break;
                    }
                }

                // b) reinforcement of a Segment that could have have predicted
                // this Cell's activation (A segment that has a (weak) match
                // to activity(clarify) during the previous time step.
                if (this.region.getTemporalLearning() && cell.getPredictingState())
                {
                    //Segment secondBestMatchingSegment = cell.getPreviousBestMatchingPreviousSegment();

                    //add Segment to queue to be updated

                    // either update secondBestMatchingSegment or add a new existing
                    // Segment to this Cell that matches the number of predictionSteps
                    // of the current active Segment for this Cell.
                }
            }
        }
    }

    /**
     * Carry out learning by updating queued Syanpse permanenceValues.
     * Queued up updates are executed once we get feed-forward input and
     * Cells are chosen as a learningCell. However, if a Cell's predictingState
     * becomes false, the Cell's segments are negatively reinforced.
     * TODO: more exact description
     */
    private void activeColumnsLearnOneTimeStep()
    {
        if(!this.region.getTemporalLearning())
        {
            return;
        }
        for (Column column : this.activeColumns)
        {
            for (Cell cell : column.getCells())
            {
                if (cell.getLearningState())
                {
                    //cell.applySegmentUpdates(true);
                }
                else if (!cell.getPredictingState() && cell.getPreviousPredictingState())
                {
                    //cell.applySegmentUpdates(false);
                }
            }
        }
    }

    /**
     * Perform one time step of the temporal pooling algorithm
     * on this Region object. This method computes the activeState
     * , predictiveState, and learningState of all Cells that are within
     * active Columns produced by the spatial pooler algorithm. The
     * output of this TemporalPooler object is these changed
     * Cell object states.
     */
    public void performTemporalPooling()
    {
        this.computeCellActiveAndLearningStatesWithinActiveColumns();

        this.computeCellPredictionStatesWithinActiveColumns();

        this.activeColumnsLearnOneTimeStep();
    }
}
