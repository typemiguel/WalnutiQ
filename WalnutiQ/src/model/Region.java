package model;

// -------------------------------------------------------------------------

import java.io.Serializable;
import java.util.Arrays;

/**
 * Data structure to represent a Region of the neocortex constructed in the
 * octant where X, Y, and Z are all positive counting numbers.
 *
 * @author Quinn Liu
 * @version Oct 1, 2012
 */
public class Region
    implements Serializable
{
    private final int      inputNumberOfColumnsOnXAxis;
    private final int      inputNumberOfColumnsOnYAxis;
    private double         inputDataScaleReductionOnXAxis;
    private double         inputDataScaleReductionOnYAxis;

    private int            numberOfColumnsOnXAxis;
    private int            numberOfColumnsOnYAxis;
    private int            cellsPerColumn;                // Z-Axis dimension
    private float          inhibitionRadius;
    private final float    percentMinimumOverlapScore;
    private float          minimumOverlapScore;

    // Example: if = 10, a column will be a winner if its overlapScore is > than
    // the overlapScore of the 10th highest column within its inhibitionRadius
    private int            desiredLocalActivity;

    private boolean        spatialLearning;
    private boolean        temporalLearning;              // TemporalPooler

    private final Column[] columns;


    // ----------------------------------------------------------
    /**
     * Constructor for a new Region object.
     *
     * @param inputNumberOfColumnsAlongXAxis
     *            inputData X-Axis length
     * @param inputNumberOfColumnsAlongYAxis
     *            inputData Y-Axis length
     * @param numberOfColumnsAlongXAxis
     *            Width of this Region
     * @param numberOfColumnsAlongYAxis
     *            Length of this Region
     * @param percentMinimumOverlapScore
     *            Minimal percentage of a Column's synapses that must be active
     *            for it to be considered as an activeColumn.
     * @param cellsPerColumn
     *            Number of layer(s) for this Region.
     * @param desiredLocalActivity
     *            Controls the number of columns that are winners after
     *            inhibition step. If 10, a colum will be a winner if it's
     *            overlapScore > than the score of the 10th highest column
     *            within its inhibition radius.
     */
    public Region(
        int inputNumberOfColumnsAlongXAxis,
        int inputNumberOfColumnsAlongYAxis,
        int numberOfColumnsAlongXAxis,
        int numberOfColumnsAlongYAxis,
        float percentMinimumOverlapScore, // used within this constructor
        int cellsPerColumn,
        int desiredLocalActivity)
    {
        this.inputNumberOfColumnsOnXAxis = inputNumberOfColumnsAlongXAxis;
        this.inputNumberOfColumnsOnYAxis = inputNumberOfColumnsAlongYAxis;
        this.numberOfColumnsOnXAxis = numberOfColumnsAlongXAxis;
        this.numberOfColumnsOnYAxis = numberOfColumnsAlongYAxis;
        this.cellsPerColumn = cellsPerColumn;
        this.inhibitionRadius = 1;
        this.percentMinimumOverlapScore = percentMinimumOverlapScore;

        // computes the minimumOverlapScore for this Region
        float percentInputPerColumn =
            1.0f / (this.numberOfColumnsOnXAxis * this.numberOfColumnsOnYAxis);
        int synapsesPerSegment =
            Math.round(this.inputNumberOfColumnsOnXAxis
                * this.inputNumberOfColumnsOnYAxis * percentInputPerColumn);
        this.minimumOverlapScore =
            synapsesPerSegment * this.percentMinimumOverlapScore / 100;

        this.desiredLocalActivity = desiredLocalActivity;
        this.spatialLearning = false;
        this.temporalLearning = false;

        // computes the number of inputCells each column must connect to along
        // each axis. The bottom layer of columns for this Region will be
        // relative to the size of the InputCell grid in both dimensions.
        this.inputDataScaleReductionOnXAxis =
            (this.inputNumberOfColumnsOnXAxis) // -1.0
                / Math.max(1.0, this.numberOfColumnsOnXAxis); // -1.0

        this.inputDataScaleReductionOnYAxis =
            (this.inputNumberOfColumnsOnXAxis) // -1.0
                / Math.max(1.0, this.numberOfColumnsOnXAxis); // -1.0

        int totalNumberOfColumnObjects =
            this.numberOfColumnsOnXAxis * this.numberOfColumnsOnYAxis;

        // stores all columns in a 1-D array based on the size of the input
        // data being connected to
        this.columns = new Column[totalNumberOfColumnObjects];
        for (int x = 0; x < this.numberOfColumnsOnXAxis; x++)
        {
            for (int y = 0; y < this.numberOfColumnsOnYAxis; y++)
            {
                int inputX =
                    (int)Math.round(x * inputDataScaleReductionOnXAxis);
                int inputY =
                    (int)Math.round(y * inputDataScaleReductionOnYAxis);
                this.columns[(y * this.numberOfColumnsOnXAxis) + x] =
                    new Column(this, inputX, inputY, x, y);
            }
        }

    }


    // ----------------------------------------------------------
    /**
     * Accessor method for private field numberOfColumnsAlongXAxis.
     *
     * @return Number of columns along the x-axis of this Region object.
     */
    public int getInputXAxisLength()
    {
        return this.inputNumberOfColumnsOnXAxis;
    }


    // ----------------------------------------------------------
    /**
     * Accessor method for private field inputNumberOfColumnsAlongYAxis.
     *
     * @return Input number of columns along the y-axis of this Region object.
     */
    public int getInputYAxisLength()
    {
        return this.inputNumberOfColumnsOnYAxis;
    }


    // ----------------------------------------------------------
    /**
     * Accessor method for private field inputDataScaleRedcutionOnXAxis.
     *
     * @return The inputDataScaleRedcutionOnXAxis field of this Region object.
     */
    public double getNumberOfInputCellsForColumnToConnectToOnXAxis()
    {
        return this.inputDataScaleReductionOnXAxis;
    }


    // ----------------------------------------------------------
    /**
     * Accessor method for private field inputDataScaleRedcutionOnYAxis.
     *
     * @return The inputDataScaleRedcutionOnYAxis field of this Region object.
     */
    public double getNumberOfInputCellsForColumnToConnectToOnYAxis()
    {
        return this.inputDataScaleReductionOnYAxis;
    }


    // ----------------------------------------------------------
    /**
     * Accessor method for private field numberOfColumnsAlongXAxis.
     *
     * @return Number of columns along the x-axis of this Region object.
     */
    public int getXAxisLength()
    {
        return this.numberOfColumnsOnXAxis;
    }


    // ----------------------------------------------------------
    /**
     * Accessor method for private field numberOfColumnsAlongYAxis.
     *
     * @return Number of columns along the y-axis of this Region object.
     */
    public int getYAxisLength()
    {
        return this.numberOfColumnsOnYAxis;
    }


    // ----------------------------------------------------------
    /**
     * Accessor method for private field cellsPerColumn.
     *
     * @return The cellsPerColumn field of this Region object.
     */
    public int getCellsPerColumn()
    {
        return this.cellsPerColumn;
    }


    // ----------------------------------------------------------
    /**
     * Accessor method for the private field inhibitionRadius.
     *
     * @return The inhibitionRadius field of this Region object.
     */
    public float getInhitbitionRadius()
    {
        return this.inhibitionRadius;
    }


    // ----------------------------------------------------------
    /**
     * Set the private inhibitionRadius field of this Region object.
     *
     * @param inhibitionRadius
     *            A radius of columns to be inhibited.
     */
    public void setInhitbitionRadius(float inhibitionRadius)
    {
        if (0 <= inhibitionRadius)
        {
            this.inhibitionRadius = inhibitionRadius;
        }
        else
        {
            this.inhibitionRadius = 0.0f;
        }
    }


    // ----------------------------------------------------------
    /**
     * Multiplied by the synapsesPerSegment to compute the minimumOverlapScore.
     *
     * @return The private field percentMinimumOverlapScore.
     */
    public float getPercentMinimumOverlapScore()
    {
        return this.percentMinimumOverlapScore;
    }


    // ----------------------------------------------------------
    /**
     * Accessor method for private field minimumOverlapScore.
     * minimumOverlapScore is computed by percentInputPerColumn * inputArea =
     * synapsesPerSegment. Then synapsesPerSegment * percentMinimumOverlapScore
     * = minimumOverlapScore
     *
     * @return The minimum overlapScore a Column object may have before being
     *         boosted.
     */
    public float getMinimumOverlapScore()
    {
        return this.minimumOverlapScore;
    }


    // ----------------------------------------------------------
    /**
     * Set a Region object's new minimumOverlapScore for testing.
     *
     * @param minimumOverlapScore
     *            The new minimumOverlapScore to be set to this Region object.
     */
    public void setMinimumOverlapScore(float minimumOverlapScore)
    {
        this.minimumOverlapScore = minimumOverlapScore;
    }


    // ----------------------------------------------------------
    /**
     * Accessor method for private field desiredLocalAcitivity.
     *
     * @return The desiredlocalActivity field of this Region object.
     */
    public int getDesiredLocalActivity()
    {
        return this.desiredLocalActivity;
    }


    // ----------------------------------------------------------
    /**
     * Accessor method for private field spatialLearning.
     *
     * @return The spatialLearning field of this Region object.
     */
    public boolean getSpatialLearning()
    {
        return this.spatialLearning;
    }


    // ----------------------------------------------------------
    /**
     * Sets if a Region object is running the spatial pooler algorithm.
     *
     * @param spatialLearning
     *            true if this Region object is spatial learning; otherwise
     *            false.
     */
    public void setSpatialLearning(boolean spatialLearning)
    {
        this.spatialLearning = spatialLearning;
    }


    // ----------------------------------------------------------
    /**
     * Access method for private field columns.
     *
     * @return An array of all the columns in this Region object.
     */
    public Column[] getColumns()
    {
        return this.columns;
    }


    /**
     * Get a reference to the Column at the specified coordinates.
     *
     * @param column
     *            The x axis position of the Column object to be returned.
     * @param row
     *            The y axis position of the Column object to be returned.
     * @return A Column object.
     */
    public Column getColumn(int column, int row)
    {
        if (column >= 0 && row >= 0 && column < this.getXAxisLength()
            && row < this.getYAxisLength())
        {
            return this.columns[row * this.getXAxisLength() + column];
        }
        return null;
    }


    // ---------------------Only Temporal Pooler Methods-----------------------
    // ----------------------------------------------------------
    /**
     * Accessor method for private field temporalLearning.
     *
     * @return The temporalLearning field of this Region object.
     */
    public boolean getTemporalLearning()
    {
        return this.temporalLearning;
    }


    // ----------------------------------------------------------
    /**
     * Sets if a Region object is running the temporal pooler algorithm.
     *
     * @param temporalLearning
     *            true if this Region object is temporal learning; otherwise
     *            false.
     */
    public void setTemporalLearning(boolean temporalLearning)
    {
        this.temporalLearning = temporalLearning;
    }
    //-------------------------------------------------------------------------


    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n=====================================");
        stringBuilder.append("\n----------Region Information---------");
        stringBuilder.append("\n              inputXAxisLength: ");
        stringBuilder.append(this.getInputXAxisLength());
        stringBuilder.append("\n              inputYAxisLength: ");
        stringBuilder.append(this.getInputYAxisLength());
        stringBuilder.append("\ninputDataScaleReductionOnXAxis: ");
        stringBuilder.append(this
            .getNumberOfInputCellsForColumnToConnectToOnXAxis());
        stringBuilder.append("\ninputDataScaleReductionOnYAxis: ");
        stringBuilder.append(this
            .getNumberOfInputCellsForColumnToConnectToOnYAxis());
        stringBuilder.append("\n             regionXAxisLength: ");
        stringBuilder.append(this.getXAxisLength());
        stringBuilder.append("\n             regionYAxisLength: ");
        stringBuilder.append(this.getYAxisLength());
        stringBuilder.append("\n                cellsPerColumn: ");
        stringBuilder.append(this.getCellsPerColumn());
        stringBuilder.append("\n              inhibitionRadius: ");
        stringBuilder.append(this.getInhitbitionRadius());
        stringBuilder.append("\n    percentMinimumOverlapScore: ");
        stringBuilder.append(this.getPercentMinimumOverlapScore());
        stringBuilder.append("\n           minimumOverlapScore: ");
        stringBuilder.append(this.getMinimumOverlapScore());
        stringBuilder.append("\n          desiredLocalActivity: ");
        stringBuilder.append(this.getDesiredLocalActivity());
        stringBuilder.append("\n               spatialLearning: ");
        stringBuilder.append(this.getSpatialLearning());
        stringBuilder.append("\n              temporalLearning: ");
        stringBuilder.append(this.getTemporalLearning());
        stringBuilder.append("\n             number of Columns: ");
        stringBuilder.append(this.columns.length);
        stringBuilder.append("\n=====================================");
        String regionInformation = stringBuilder.toString();
        return regionInformation;
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Region region = (Region)o;

        if (cellsPerColumn != region.cellsPerColumn)
            return false;
        if (desiredLocalActivity != region.desiredLocalActivity)
            return false;
        if (Float.compare(region.inhibitionRadius, inhibitionRadius) != 0)
            return false;
        if (Double.compare(
            region.inputDataScaleReductionOnXAxis,
            inputDataScaleReductionOnXAxis) != 0)
            return false;
        if (Double.compare(
            region.inputDataScaleReductionOnYAxis,
            inputDataScaleReductionOnYAxis) != 0)
            return false;
        if (inputNumberOfColumnsOnXAxis != region.inputNumberOfColumnsOnXAxis)
            return false;
        if (inputNumberOfColumnsOnYAxis != region.inputNumberOfColumnsOnYAxis)
            return false;
        if (Float.compare(region.minimumOverlapScore, minimumOverlapScore) != 0)
            return false;
        if (numberOfColumnsOnXAxis != region.numberOfColumnsOnXAxis)
            return false;
        if (numberOfColumnsOnYAxis != region.numberOfColumnsOnYAxis)
            return false;
        if (Float.compare(
            region.percentMinimumOverlapScore,
            percentMinimumOverlapScore) != 0)
            return false;
        if (spatialLearning != region.spatialLearning)
            return false;
        if (temporalLearning != region.temporalLearning)
            return false;
        if (!Arrays.equals(columns, region.columns))
            return false;

        return true;
    }


    @Override
    public int hashCode()
    {
        int result = 0;
        long temp;
        result += 2 * inputNumberOfColumnsOnXAxis;
        result += 3 * inputNumberOfColumnsOnYAxis;
        temp =
            inputDataScaleReductionOnXAxis != +0.0d ? Double
                .doubleToLongBits(inputDataScaleReductionOnXAxis) : 0L;
        result += 5 * (int)(temp ^ (temp >>> 32));
        temp =
            inputDataScaleReductionOnYAxis != +0.0d ? Double
                .doubleToLongBits(inputDataScaleReductionOnYAxis) : 0L;
        result += 7 * (int)(temp ^ (temp >>> 32));
        result += 11 * numberOfColumnsOnXAxis;
        result += 13 * numberOfColumnsOnYAxis;
        result += 17 * cellsPerColumn;
        result +=
            19 * (inhibitionRadius != +0.0f ? Float
                .floatToIntBits(inhibitionRadius) : 0);
        result +=
            23 * (percentMinimumOverlapScore != +0.0f ? Float
                .floatToIntBits(percentMinimumOverlapScore) : 0);
        result +=
            29 * (minimumOverlapScore != +0.0f ? Float
                .floatToIntBits(minimumOverlapScore) : 0);
        result += 31 * desiredLocalActivity;
        result += 37 * (spatialLearning ? 1 : 0);
        result += 41 * (temporalLearning ? 1 : 0);
        result += 43 * (columns != null ? Arrays.hashCode(columns) : 0);
        return result;
    }
}
