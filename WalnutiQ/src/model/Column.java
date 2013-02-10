package model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

// -------------------------------------------------------------------------
/**
 * Represents a single Column of cells within a Region object.
 *
 * @author Quinn Liu
 * @version Oct 4, 2012
 */
public class Column
    implements Serializable
{
    private final Region  region;

    private final int     inputX, inputY;

    private final int     x, y;
    private boolean       activeState;                              // proximalSegment's
// activeState
    private int           overlapScore;
    private List<Column>  neighborColumns;

    // value computed for a Column during learning. Always >= 1. Used to
    // increase the overlapScore for inactive Columns.
    private float         boostValue;

    // a sliding average representing how many times this Column has been active
    // after inhibition (e.g. over the past 1000 iterations of spatial learning)
    private float         activeDutyCycle;

    // a sliding average representing how many times this Column has had
    // significant overlapScore(greater than MINIMUM_OVERLAP
    private float         overlapDutyCycle;

    private Cell[]        cells;

    // list of potential synapses and their permanence values
    private final Segment proximalSegment;

    /**
     * Used to compute the activeDutyCycle and overlapDutyCycle by decreasing it
     * exponentially less as the boostValue approached 0 and minimumOverlapScore
     * respectively.
     */
    public final float    EXPONENTIAL_MOVING_AVERAGE_AlPHA = 0.005f;


    // ----------------------------------------------------------
    /**
     * Create a new Column object.
     *
     * @param region
     *            The Region object that contains this Column object.
     * @param XAxisPosition
     *            X position within Region object.
     * @param YAxisPosition
     *            Y position within Region object.
     * @param inputXAxisPosition
     *            Beginning x position within InputCell objects layer.
     * @param inputYAxisPosition
     *            Beginning y position within InputCell objects layer.
     */
    public Column(
        Region region,
        int inputXAxisPosition,
        int inputYAxisPosition,
        int XAxisPosition,
        int YAxisPosition)
    {
        this.region = region;

        this.inputX = inputXAxisPosition;
        this.inputY = inputYAxisPosition;

        this.x = XAxisPosition;
        this.y = YAxisPosition;

        this.activeState = false;
        this.overlapScore = 0;
        this.neighborColumns = new ArrayList<Column>();

        this.boostValue = 1.0f;
        this.activeDutyCycle = 1.0f; // Must be greater than 0, or will stay 0
        this.overlapDutyCycle = 1.0f; // Must be greater than 0, or will stay 0

        // instantiate all cells within this Column
        int numberOfCells = this.region.getCellsPerColumn();
        this.cells = new Cell[numberOfCells];
        for (int index = 0; index < numberOfCells; index++)
        {
            this.cells[index] = new Cell(this, index);
        }

        this.proximalSegment = new Segment();

        // Attach correct square array of Synapse objects to this Column's
        // proximalSegment(already instantiated).
        int startIndexOfSynapseOnXAxis =
            (int)(this.getX() * this.getRegion()
                .getNumberOfInputCellsForColumnToConnectToOnXAxis());
        int endIndexOfSynapseOnXAxis =
            (int)(startIndexOfSynapseOnXAxis + this.getRegion()
                .getNumberOfInputCellsForColumnToConnectToOnXAxis());
        int startIndexOfSynapseOnYAxis =
            (int)(this.getY() * this.getRegion()
                .getNumberOfInputCellsForColumnToConnectToOnYAxis());
        int endIndexOfSynapseOnYAxis =
            (int)(startIndexOfSynapseOnYAxis + this.getRegion()
                .getNumberOfInputCellsForColumnToConnectToOnYAxis());

        for (int x1 = startIndexOfSynapseOnXAxis; x1 < endIndexOfSynapseOnXAxis; x1++)
        {
            for (int y1 = startIndexOfSynapseOnYAxis; y1 < endIndexOfSynapseOnYAxis; y1++)
            {
                // create a new InputCell and attach it to a new Synapse object
                AbstractCell newInputCell = new InputCell(x1, y1);
                Synapse newSynapse = new Synapse(newInputCell);
                // add new Synapse to this Column's proximalSegment
                this.getProximalSegment().addSynapse(newSynapse);
            }
        }
    }


    // ----------------------------------------------------------
    /**
     * Get the Region object of this Column object.
     *
     * @return The Region object that contains this Column object.
     */
    public Region getRegion()
    {
        return this.region;
    }


    // ----------------------------------------------------------
    /**
     * Get the input X axis index of this Column object within a Region object.
     *
     * @return The input X axis index of a Column object.
     */
    public int getInputX()
    {
        return this.inputX;
    }


    // ----------------------------------------------------------
    /**
     * Get the input Y axis index of this Column object within a Region object.
     *
     * @return The input Y axis index of a Column object.
     */
    public int getInputY()
    {
        return this.inputY;
    }


    // ----------------------------------------------------------
    /**
     * Get the X axis index of this Column object within a Region object.
     *
     * @return The X axis index of a Column object.
     */
    public int getX()
    {
        return this.x;
    }


    // ----------------------------------------------------------
    /**
     * Get the Y axis index of this Column object within a Region object.
     *
     * @return The Y axis index of a Column object.
     */
    public int getY()
    {
        return this.y;
    }


    // ----------------------------------------------------------
    /**
     * Get the private field activeState.
     *
     * @return The active of a Column object.
     */
    public boolean getActiveState()
    {
        return this.activeState;
    }


    // ----------------------------------------------------------
    /**
     * Set the activeState of a Column object.
     *
     * @param activeState
     *            Updated active state of a Column object.
     */
    public void setActiveState(boolean activeState)
    {
        this.activeState = activeState;
    }


    // ----------------------------------------------------------
    /**
     * Get private field overlapScore.
     *
     * @return The overlapScore of a Column object.
     */
    public int getOverlapScore()
    {
        return this.overlapScore;
    }


    // ----------------------------------------------------------
    /**
     * Set the overlapScore of a Column object. Used only for testing purposes.
     *
     * @param overlapScore
     *            New overlapScore of a Column object.
     */
    public void setOverlapScore(int overlapScore)
    {
        if (overlapScore > 0)
        {
            this.overlapScore = overlapScore;
        }
        else
        {
            this.overlapScore = 0;
        }

    }


    // ----------------------------------------------------------
    /**
     * Given an input vector, each Column's overlap score is calculated with
     * that vector. The overlap for each column is simply the number of
     * connected synapses with active inputs, multiplied by its boost. If a
     * Column's overlapScore is below minOverlap, that Column's overlapScore is
     * set to 0.
     */
    public void computeOverlapScore()
    {
        int newOverlapScore = this.proximalSegment.getActiveSynapses().size();

        if (newOverlapScore < this.region.getMinimumOverlapScore())
        {
            newOverlapScore = 0;
        }
        else
        {
            newOverlapScore = (int)(newOverlapScore * this.boostValue);
        }
        this.overlapScore = newOverlapScore;
    }


    /**
     * Adds all neighbor Column objects within inhibitionRadius to the
     * this.neighborColumns field of the Column object this method is invoked
     * on.
     */
    public void updateNeighborColumns()
    {
        int localInhibitionRadius =
            Math.round(this.region.getInhitbitionRadius());

        if (localInhibitionRadius <= 0)
        {
            System.out.println("Region inhibitionRadius: "
                + localInhibitionRadius);
        }

        // forced inhibition of adjacent columns
        int xInitial = Math.max(0, this.getX() - localInhibitionRadius);
        int yInitial = Math.max(0, this.getY() - localInhibitionRadius);
        int xFinal =
            Math.min(this.region.getXAxisLength(), this.getX()
                + localInhibitionRadius);
        int yFinal =
            Math.min(this.region.getYAxisLength(), this.getY()
                + localInhibitionRadius);

        // to allow double for loop to reach end portion of this.allColumns
        xFinal = Math.min(this.region.getXAxisLength(), xFinal + 1);
        yFinal = Math.min(this.region.getYAxisLength(), yFinal + 1);

        if (this.neighborColumns != null)
        {
            this.neighborColumns.clear(); // remove neighbors of previous
                                          // parameter column
        }

        for (int column = xInitial; column < xFinal; column++)
        {
            for (int row = yInitial; row < yFinal; row++)
            {
                if (column == this.getX() && row == this.getY())
                {
                    // TODO: To make inhibition a circle around input column,
                    // change to remove corners of this rectangle inhibition
                }
                else
                {
                    Column newColumn = this.region.getColumn(column, row);
                    if (newColumn != null)
                    {
                        this.neighborColumns.add(newColumn);
                    }
                }
            }
        }
    }


    // ----------------------------------------------------------
    /**
     * Return this Column object's list of neighborColumns.
     *
     * @return The updated list of neighbor columns for the column that this
     *         method was called on.
     */
    public List<Column> getNeighborColumns()
    {
        return this.neighborColumns;
    }


    // ----------------------------------------------------------
    /**
     * Set the list of neighborColumns of a Column. Used only for testing
     * purposes.
     *
     * @param neighborColumns
     *            A list of neighbor Column objects.
     */
    public void setNeighborColumns(List<Column> neighborColumns)
    {
        this.neighborColumns = neighborColumns;
    }


    // ----------------------------------------------------------
    /**
     * Get the boostValue of a Column object within a Region.
     *
     * @return A Column object's boostValue.
     */
    public float getBoostValue()
    {
        return this.boostValue;
    }


    // ----------------------------------------------------------
    /**
     * Set the boostValue of a Column object. Used only for testing purposes.
     *
     * @param boostValue
     *            The new boostValue for a Column object.
     */
    public void setBoostValue(float boostValue)
    {
        if (boostValue <= 0)
        {
            this.boostValue = 1.0f;
        }
        else if (boostValue > 0)
        {
            this.boostValue = boostValue;
        }
    }


    // ----------------------------------------------------------
    /**
     * Get the ActiveDutyCycle of a Column object within a Region object.
     *
     * @return A Column object's activeDutyCycle.
     */
    public float getActiveDutyCycle()
    {
        return this.activeDutyCycle;
    }


    // ----------------------------------------------------------
    /**
     * Set the activeDutyCyle of a Column object. Used only for testing
     * purposes.
     *
     * @param activeDutyCycle
     *            The new activeDutyCycle for a Column object. Value will be >=
     *            0 or <= 1.
     */
    public void setActiveDutyCycle(float activeDutyCycle)
    {
        if (activeDutyCycle <= 0 || activeDutyCycle > 1.0f)
        {
            this.activeDutyCycle = 1.0f;
        }
        else
        {
            this.activeDutyCycle = activeDutyCycle;
        }
    }


    // ----------------------------------------------------------
    /**
     * Get the OverlapDutyCycle of a Column object within a Region object.
     *
     * @return A Column object's overlapDutyCycle.
     */
    public float getOverlapDutyCycle()
    {
        return this.overlapDutyCycle;
    }


    // ----------------------------------------------------------
    /**
     * Set the overlapDutyCyle of a Column object. Used only for testing
     * purposes.
     *
     * @param overlapDutyCycle
     *            The new overlapDutyCycle for a Column object. Value will be >=
     *            0 or <= 1.
     */
    public void setOverlapDutyCycle(float overlapDutyCycle)
    {
        if (overlapDutyCycle <= 0 || overlapDutyCycle > 1.0f)
        {
            this.overlapDutyCycle = 1.0f;
        }
        else
        {
            this.overlapDutyCycle = overlapDutyCycle;
        }
    }


    // ----------------------------------------------------------
    /**
     * Returns a Column object's list of Cell objects.
     *
     * @return A Column object's array of Cell objects.
     */
    public Cell[] getCells()
    {
        return this.cells;
    }


    // ----------------------------------------------------------
    /**
     * Return a Column object's proximalSegment.
     *
     * @return A proximal Segment object.
     */
    public Segment getProximalSegment()
    {
        return this.proximalSegment;
    }


    /**
     * Increases the permenanceValue of every Synapse object in a Column object
     * by "scaleFactor" number of times.
     *
     * @param scaleFactor
     *            Number of times to increasePermanences by PERMANENCE_INCREASE.
     */
    public void increasePermanences(int scaleFactor)
    {
        for (int i = 0; i < scaleFactor; i++)
        {
            this.getProximalSegment().updateProximalPermanences(true);
        }
    }


    // ----------------------------------------------------------
    /**
     * Update the permanenceValue of every Synapse in a Column's proximalSegment
     * based on if the Column is active. This is the main learning rule for a
     * Column's proximal dendrite.
     */
    public void updatePermanences()
    {
        this.getProximalSegment().updateProximalPermanences();
    }


    // ----------------------------------------------------------
    /**
     * Return a set of connectedSynapses for the proximalSegment of a Column
     * object.
     *
     * @return A set of connectedSynapses.
     */
    public Set<Synapse> getConnectedSynapses()
    {
        return this.getProximalSegment().getConnectedSynapses();
    }


    // ----------------------------------------------------------
    /**
     * Add a connectedSynapse to the proximalSegment of a Column object.
     *
     * @param connectedSynapse
     *            A connected Synapse object.
     */
    public void addConnectedSynapse(Synapse connectedSynapse)
    {
        this.getProximalSegment().getSynapses().add(connectedSynapse);
    }


    // ----------------------------------------------------------
    /**
     * Returns the maximum activeDutyCycle within a given ArrayList of Column
     * objects.
     *
     * @param neighborColumn
     *            A list of Column objects.
     * @return The maximum acitveDutyCycle of a Column object.
     */
    float maximumActiveDutyCycle(List<Column> neighborColumn)
    {
        float maximumActiveDutyCycle = 0.0f;
        for (Column column : neighborColumn)
        {
            if (column.getActiveDutyCycle() > maximumActiveDutyCycle)
            {
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
    void updateActiveDutyCycle()
    {
        // newActiveDutyCylce is reduced less and less as it's value approaches
        // zero. However, newActiveDutyCylce is not always called but is always
        // increased by the maximum decreasable rate. Thus, a column's
        // activeDutyCycle has a upper bound of 1.
        float newActiveDutyCycle =
            (1.0f - EXPONENTIAL_MOVING_AVERAGE_AlPHA)
                * this.getActiveDutyCycle();
        if (this.getActiveState())
        {
            newActiveDutyCycle += EXPONENTIAL_MOVING_AVERAGE_AlPHA;
        }
        this.activeDutyCycle = newActiveDutyCycle;
    }


    /**
     * Compute a moving average of how often this Column has overlap greater
     * than minimumDutyOverlap. Exponential Moving Average(EMA): St = a * Yt +
     * (1 - a) * St - 1.
     */
    void updateOverlapDutyCycle()
    {
        float newOverlapDutyCycle =
            (1.0f - EXPONENTIAL_MOVING_AVERAGE_AlPHA)
                * this.getOverlapDutyCycle();

        if (this.getOverlapScore() > this.region.getMinimumOverlapScore())
        {
            newOverlapDutyCycle += EXPONENTIAL_MOVING_AVERAGE_AlPHA;
        }
        this.overlapDutyCycle = newOverlapDutyCycle;
    }


    /**
     * Computes the boostValue of a Column during learning.
     *
     * @param minimumDutyCycle
     *            Represents the minimum desired firing rate for a cell. If a
     *            cell's firing rate falls below this value, it will be boosted.
     * @return The boostValue of a Column.
     */
    float boostFunction(float minimumDutyCycle)
    {
        if (minimumDutyCycle <= 0)
        {
            throw new IllegalStateException(
                "minimumDutyCycle cannot be less or equal to zero");
        }
        if (this.getActiveDutyCycle() <= minimumDutyCycle)
        {
            // the boostValue increases linearly once the Column's
            // activeDutyCycle becomes less than the minimumDutyCycle
            return (minimumDutyCycle / this.getActiveDutyCycle());
        }
        else
        // this.activeDutyCycle > minimumDutyCycle
        {
            return this.getActiveDutyCycle();
        }
    }


    // ----------------------------------------------------------
    /**
     * Reinitializes the activeState, overlapScore, neighborColumns, and
     * boostValue fields of this Column object to be recomputed.
     */
    public void nextTimeStep()
    {
        this.activeState = false;
        this.overlapScore = 0;
        this.neighborColumns.clear();
        this.boostValue = 1.0f;
    }


    // ----------------------------------------------------------
    /**
     * 2 methods to help a column learn connections: 1) If
     * activeDutyCycle(measures winning rate) is too low, the overall boost
     * value of the column is increased. 2) If overlapDutyCycle(measures
     * connected synapses with inputs) is too low, the permanence values of the
     * Column's Synapses are boosted.
     */
    void performBoosting()
    {
        this.updateNeighborColumns();
        this.neighborColumns = this.getNeighborColumns();

        float maximumActiveDutyCycle =
            this.maximumActiveDutyCycle(this.getNeighborColumns());
        // minDutyCycle represents the minimum desired firing rate for a
        // cell(number of times is becomes active over some number of
// iterations).
        // If a cell's firing rate falls below this value, it will be boosted.
        float minimumDutyCycle = 0.01f * maximumActiveDutyCycle;
        // TODO: make 0.01f a Region field percentMinimumFiringRate

        // 1) boost if activeDutyCycle is too low
        this.updateActiveDutyCycle();
        this.boostValue = boostFunction(minimumDutyCycle);

        // 2) boost if overlapDutyCycle is too low
        this.updateOverlapDutyCycle();
        if (this.getOverlapDutyCycle() < minimumDutyCycle)
        {
            this.increasePermanences((int)(10.0f * Synapse.MINIMAL_CONNECTED_PERMANCE));
            // TODO: more biologically accurate
        }
    }


    // =======================Temporal Pooler methods==========================
    // TODO: test
    /**
     * This method is called when no Segment within the Region predicted the
     * active Cells within the active Columns that are computed from the spatial
     * pooling algorithm. This method only considers Cell Segments that are
     * predicting Cell activation to occur in numberPredictionSteps many times
     * steps from now. If no Cell has a matching Segment, then return the Cell
     * with the fewest number of Segments.
     *
     * @param numberPredictionSteps
     *            Only consider Segments that are predicting Cell activation to
     *            occur in exactly this many time steps from now.
     * @param previous
     *            if true only consider active Segments from previous time step;
     *            otherwise only consider current active Segments.
     * @return The best matching Cell.
     */
    public Cell getBestMatchingCell(int numberPredictionSteps, boolean previous)
    {
        Cell bestCell = null;
        int greatestNumberOfActiveSynapses = 0;

        for (Cell cell : this.cells)
        {
            Segment segment =
                cell.getBestMatchingSegment(numberPredictionSteps, previous);
            if (segment != null)
            {
                int numberOfActiveSynapses = 0;
                if (previous)
                {
                    numberOfActiveSynapses = segment.getPreviousActiveSynapses().size();
                }
                else
                {
                    numberOfActiveSynapses = segment.getActiveSynapses().size();
                }
                if (numberOfActiveSynapses > greatestNumberOfActiveSynapses)
                {
                    greatestNumberOfActiveSynapses = numberOfActiveSynapses;
                    bestCell = cell; // bestCell also contains updated bestSegment
                }
            }
        }

        // if no Segments are predicting Cell activation at the desired time step
        // the best Cell is the Cell with the fewest number of Segments so that
        // Segments can be evenly distributed across the Cells within a Column
        // and Region
        if (bestCell == null)
        {
            bestCell = this.cells[0];
            int smallestNumberOfSegments = bestCell.getListOfDistalSegments().size();
            for (Cell cell : this.cells)
            {
                if (cell.getListOfDistalSegments().size() < smallestNumberOfSegments)
                {
                    smallestNumberOfSegments = cell.getListOfDistalSegments().size();
                    bestCell = cell;
                }
            }
        }
        return bestCell;
    }


    // =======================================================================

    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n====================================");
        stringBuilder.append("\n---------Column Information---------");
        stringBuilder.append("\n                region layers: ");
        stringBuilder.append(this.getRegion().getCellsPerColumn());
        stringBuilder.append("\n                       inputX: ");
        stringBuilder.append(this.getInputX() + " - " + (this.getInputX() + 4));
        stringBuilder.append("\n                       inputY: ");
        stringBuilder.append(this.getInputY() + " - " + (this.getInputY() + 4));
        stringBuilder.append("\n                       (x, y): ");
        stringBuilder.append("(" + this.getX() + ", " + this.getY() + ")");
        stringBuilder.append("\n                  activeState: ");
        stringBuilder.append(this.getActiveState());
        stringBuilder.append("\n                 overlapScore: ");
        stringBuilder.append(this.getOverlapScore());
        stringBuilder.append("\n    number of neighborColumns: ");
        stringBuilder.append(this.neighborColumns.size());
        stringBuilder.append("\n                   boostValue: ");
        stringBuilder.append(this.getBoostValue());
        stringBuilder.append("\n              activeDutyCycle: ");
        stringBuilder.append(this.getActiveDutyCycle());
        stringBuilder.append("\n             overlapDutyCycle: ");
        stringBuilder.append(this.getOverlapDutyCycle());
        stringBuilder.append("\n              number of cells: ");
        stringBuilder.append(this.cells.length);
        // TODO: add learning cell printout for temporal pooler
        stringBuilder.append("\nproximalSegment's activeState: ");
        stringBuilder.append(this.getProximalSegment().getActiveState());
        stringBuilder.append("\n               moving average: ");
        stringBuilder.append(this.EXPONENTIAL_MOVING_AVERAGE_AlPHA);
        stringBuilder.append("\n=====================================");
        String columnInformation = stringBuilder.toString();
        return columnInformation;
    }


    @Override
    public boolean equals(Object columnToCompareTo)
    {
        if (this == columnToCompareTo)
        {
            return true;
        }
        if (this == null || columnToCompareTo.getClass() != this.getClass())
        {
            return false;
        }
        Column secondColumn = (Column)columnToCompareTo;
        return this.region.equals(secondColumn.getRegion())
            && this.region.equals(secondColumn.getRegion())
            && this.inputX == secondColumn.getInputX()
            && this.inputY == secondColumn.getInputY()
            && this.x == secondColumn.getX() && this.y == secondColumn.getY()
            && this.activeState == secondColumn.getActiveState()
            && this.overlapScore == secondColumn.getOverlapScore()
            && this.neighborColumns.equals(secondColumn.getNeighborColumns())
            && this.boostValue == secondColumn.getBoostValue()
            && this.activeDutyCycle == secondColumn.getActiveDutyCycle()
            && this.overlapDutyCycle == secondColumn.getOverlapDutyCycle()
            && this.cells.equals(secondColumn.getCells())
            && this.proximalSegment.equals(secondColumn.getProximalSegment());
    }


    @Override
    public int hashCode()
    {
        int code = 0;
        code += 2 * this.x;
        code += 3 * this.y;
        code += 5 * this.overlapScore;
        code += 7 * this.boostValue;
        return code;
    }
}
