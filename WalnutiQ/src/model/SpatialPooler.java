package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

// -------------------------------------------------------------------------
/**
 * Provides a method to implement a spatial pooler algorithm on a Region object.
 *
 * @author Quinn Liu
 * @version Oct 25, 2012
 */
public class SpatialPooler
{
    private Region      region;
    /**
     * List of activeColumns to be used as input for the temporal pooler.
     */
    private Set<Column> activeColumns;


    // ----------------------------------------------------------
    /**
     * Create a new SpatialPooler object.
     *
     * @param region
     *            The Region object where the spatial pooler algorithm will be
     *            executed on.
     */
    public SpatialPooler(Region region)
    {
        this.region = region;
        this.activeColumns = new HashSet<Column>();
    }


    // ----------------------------------------------------------
    /**
     * Get the private Region field within this SpatialPooler object.
     *
     * @return The Region object for this SpatialPooler object.
     */
    public Region getRegion()
    {
        return this.region;
    }


    // ----------------------------------------------------------
    /**
     * Sets a Region object to this SpatialPooler object.
     *
     * @param region
     *            The Region object to be set.
     */
    public void setRegion(Region region)
    {
        this.region = region;
    }


    // ----------------------------------------------------------
    /**
     * Return a Region object's HashSet of activeColumns computed by the
     * performSpatialPooling method.
     *
     * @return A Region object's HashSet of activeColumns.
     */
    public Set<Column> getActiveColumns()
    {
        return this.activeColumns;
    }


    // ----------------------------------------------------------
    /**
     * Return a Region object's specified active Column object.
     *
     * @param x
     *            X axis position of the Column object to be returned.
     * @param y
     *            Y axis position of the Column object to be returned.
     * @return All of the active columns within a SpatialPooler object
     *         containing a Region object.
     */
    public Column getActiveColumn(int x, int y)
    {
        for (Column activeColumn : this.activeColumns)
        {
            if (activeColumn.getX() == x && activeColumn.getY() == y)
            {
                return activeColumn;
            }
        }
        return null;
    }


    // ---------------------------------------------------------
    /**
     * Adds a new activeColumn to the set of activeColumns for a Region object.
     *
     * @param activeColumn
     *            The activeColumn object to be added.
     */
    public void addActiveColumn(Column activeColumn)
    {
        this.activeColumns.add(activeColumn);
    }


    /**
     * Given a list of columns, return the kth highest overlapScore value of a
     * Column object.
     *
     * @param neighborColumns
     *            The list of column objects to be iterated through.
     * @param desiredLocalActivity
     *            The number of columns that can be active within this Region
     *            object.
     * @return The overlapScore of the kth highest column, where k is
     *         newDesiredLocalAcitivity.
     */
    int kthScoreOfColumns(List<Column> neighborColumns, int desiredLocalActivity)
    {
        // TreeSet data structures are automatically sorted.
        Set<Integer> overlapScores = new TreeSet<Integer>();
        for (Column column : neighborColumns)
        {
            overlapScores.add(column.getOverlapScore());
        }

        // if invalid or no local activity is desired, it is changed so that the
        // highest overlapScore is returned.
        if (desiredLocalActivity <= 0)
        {
            throw new IllegalStateException(
                "desiredLocalActivity cannot be <= 0");
        }

        // k is the index of the overlapScore to be returned. The overlapScore
        // is the score at position k(counting from the top) of all
        // overlapScores when arranged from smallest to greatest.
        int k = Math.max(0, overlapScores.size() - desiredLocalActivity);
        return (Integer)overlapScores.toArray()[k];
    }


    /**
     * Returns the radius of the average connected receptive field size of all
     * the columns. The connected receptive field size of a column includes only
     * the total distance of the column's connected synapses's cell's distance
     * from the column divided by the number of synapses the column has. In
     * other words the total length of segments for a column divided by the
     * number of segments. For spatial pooling since the number of synapses is
     * constant after initializing, averageReceptiveFieldSize of a column will
     * remain constant, but will be different for different columns based on how
     * they are connected to the inputCell layer.
     *
     * @return The average connected receptive field size.
     */
    float averageReceptiveFieldSizeOfRegion()
    {
        double totalSynapseDistanceFromOriginColumn = 0.0;
        int numberOfSynapses = 0;
        Set<Synapse> connectedSynapesForAColumn = new HashSet<Synapse>();

        for (Column column : this.region.getColumns())
        {
            if (column != null)
            {
                connectedSynapesForAColumn = column.getConnectedSynapses();

                // iterates over every connected synapses and sums the distances
                // from it's original Column to determine the average receptive
                // field
                for (Synapse synapse : connectedSynapesForAColumn)
                {
                    double dx =
                        column.getInputX() - synapse.getAbstractCell().getX();
                    double dy =
                        column.getInputY() - synapse.getAbstractCell().getY();
                    double synapseDistance = Math.sqrt(dx * dx + dy * dy);

                    double numberOfInputCellsForColumnToConnectTo =
                        (this.getRegion()
                            .getNumberOfInputCellsForColumnToConnectToOnXAxis() + this
                            .getRegion()
                            .getNumberOfInputCellsForColumnToConnectToOnYAxis()) / 2;
                    totalSynapseDistanceFromOriginColumn +=
                        (synapseDistance / numberOfInputCellsForColumnToConnectTo);
                    numberOfSynapses++;
                }
            }
        }
        return (float)(totalSynapseDistanceFromOriginColumn / numberOfSynapses);
    }


    // ----------------------------------------------------------
    /**
     * Compute each Column object's new overlapScore.
     */
    void computeAllColumnOverlapScoresOfRegion()
    {
        for (Column column : this.getRegion().getColumns())
        {
            column.computeOverlapScore();
        }
    }


    // ----------------------------------------------------------
    /**
     * This method is called by performSpatialPooling and computes the
     * activeColumns(t)-the list of columns that win due to the bottom-up input
     * at time t.
     */
    void computeActiveColumnsOfRegion()
    {
        List<Column> neighborColumns = new ArrayList<Column>();
        for (Column column : this.getRegion().getColumns())
        {
            column.setActiveState(false);
            column.updateNeighborColumns();
            neighborColumns = column.getNeighborColumns();

            int minimumLocalOverlapScore =
                this.kthScoreOfColumns(neighborColumns, this.getRegion()
                    .getDesiredLocalActivity());

            // more than (this.region.desiredLocalActivity) number of
            // columns can become active since it is applied to each Column
            // object's neighborColumns
            if (column.getOverlapScore() > 0
                && column.getOverlapScore() >= minimumLocalOverlapScore)
            {
                column.setActiveState(true);
                this.addActiveColumn(column); // use for temporal pooler
            }
        }
    }


    // ----------------------------------------------------------
    /**
     * This method only executes if the Region's spatialLearning field is true.
     * Changes Synapse permanenceValues based on Synapse and InputCell
     * activeStates. The inhibitionRadius for the Region is also computed.
     */
    void regionLearnOneTimeStep()
    {
        if (this.getRegion().getSpatialLearning())
        {
            for (Column column : this.getRegion().getColumns())
            {
                if (column.getActiveState())
                {
                    // increase and decrease of proximal segment Synapses based
                    // on each Synapses's activeState
                    column.updatePermanences();
                }
            }

            for (Column column : this.getRegion().getColumns())
            {
                column.performBoosting();
            }

            this.getRegion().setInhitbitionRadius(
                averageReceptiveFieldSizeOfRegion());
        }
    }


    // ----------------------------------------------------------
    /**
     * All SpatialPooler fields remain constant except spatialLearning. Update
     * every Abstract InputCell in this Region object with the 2-D parameter of
     * bytes for this Region to call performSpatialPooling on this time step's
     * inputData. This method acts as a nextTimeStep() method for all InputCells
     * within a Region.
     *
     * @param inputDataBytes
     *            Representation of new input at current time step.
     */
    public void nextTimeStep(byte[][] inputDataBytes)
    {
        // iterate through every Column, every Synapse, then updates InputCell
        for (Column column : this.region.getColumns())
        {
            // Note: do not need to call nextTimeStep() for Segment or Cell
            // since it is not needed to compute activeColumns for
            // performSpatialPooling().
            // Also not necessary to call on Region object because all fields
            // are constant for spatial pooling.
            // It is necessary to call nextTimeStep() for each Column object
            // because certain fields are used for only one execution of spatial
// pooling
            // and then must be updated.
            column.nextTimeStep();

            // iterate through every Synapse, get the x and y of the InputCell
            // then change the InputCells activeState based on the parameter
            for (Synapse synapse : column.getProximalSegment().getSynapses())
            {
                // iterates through every Synapse's InputCell
                int xInputPosition = synapse.getAbstractCell().getX();
                int yInputPosition = synapse.getAbstractCell().getY();

                // TODO: -2 because that's how black pixels are currently
                // represented *Change to 1
                if (inputDataBytes[xInputPosition][yInputPosition] == -2)
                {
                    synapse.getAbstractCell().nextTimeStep();
                    synapse.getAbstractCell().setActiveState(true);
                }
                else
                {
                    synapse.getAbstractCell().nextTimeStep();
                    // synapse.getAbstractCell().setActiveState(false) done by
                    // above line of code
                }
            }
        }
    }


    // ------------------------------------------------------------
    /**
     * This method recomputes all column states within this functional region.
     * Through local inhibition, only a sparse set of columns become active to
     * represent the current 2D array of sensory data or a lower region's
     * output.
     *
     * @return A sparse set of active columns within this region.
     */
    public Set<Column> performSpatialPoolingOnRegion()
    {
        computeAllColumnOverlapScoresOfRegion();

        // a sparse set of columns become active after local inhibition
        computeActiveColumnsOfRegion();

        // simulate learning by boosting specific synapses
        regionLearnOneTimeStep();

        return getActiveColumns();
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        SpatialPooler that = (SpatialPooler)o;

        if (activeColumns != null
            ? !activeColumns.equals(that.activeColumns)
            : that.activeColumns != null)
            return false;
        if (region != null ? !region.equals(that.region) : that.region != null)
            return false;

        return true;
    }


    @Override
    public int hashCode()
    {
        int result = region != null ? region.hashCode() : 0;
        result =
            31 * result
                + (activeColumns != null ? activeColumns.hashCode() : 0);
        return result;
    }


    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n=============================");
        stringBuilder.append("\n--SpatialPooler Information--");
        stringBuilder.append("\n       region layers: ");
        stringBuilder.append(this.region.getCellsPerColumn());
        stringBuilder.append("\nactiveColumns number: ");
        stringBuilder.append(this.activeColumns.size());
        stringBuilder.append("\n=============================");
        String spatialPoolerInformation = stringBuilder.toString();
        return spatialPoolerInformation;
    }
}
