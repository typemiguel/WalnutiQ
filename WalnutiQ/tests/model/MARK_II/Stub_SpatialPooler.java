package model.MARK_II;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Stub_SpatialPooler extends Stub_Pooler {
    private Set<Stub_Column> activeColumns;

    public Stub_SpatialPooler(Stub_Region newRegion) {
	if (newRegion == null) {
	    throw new IllegalArgumentException(
		    "newRegion in SpatialPooler constructor cannot be null");
	}
	this.region = newRegion;
	this.activeColumns = new HashSet<Stub_Column>();
    }

    public Set<Stub_Column> getActiveColumns() {
	return this.activeColumns;
    }

    /**
     * This method recomputes all column states within this functional region.
     * Through local inhibition, only a sparse set of columns become active to
     * represent the current 2D array of sensory data or a lower region's
     * output.
     *
     * @return A sparse set of active columns within this region.
     */
    public Set<Stub_Column> performSpatialPoolingOnRegion() {
	Stub_Column[][] columns = this.region.getColumns();
	for (int x = 0; x < columns.length; x++) {
	    for (int y = 0; y < columns[0].length; y++) {
		this.computeColumnOverlapScore(columns[x][y]);
	    }
	}

	// a sparse set of columns become active after local inhibition
	this.computeActiveColumnsOfRegion();

	// simulate learning by boosting specific synapses
	this.regionLearnOneTimeStep();

	return this.activeColumns;
    }

    /**
     * The overlapScore for each column is the number of synapses connected to
     * cells with active inputs multiplied by that columns's boostValue. If a
     * Column's overlapScore is below minOverlap, that Column's overlapScore is
     * set to 0.
     */
    public void computeColumnOverlapScore(Stub_Column column) {
	if (column == null) {
	    throw new IllegalArgumentException(
		    "the column in SpatialPooler method computeColumnOverlapScore cannot be null");
	}
	int newOverlapScore = column.getProximalSegment()
		.getNumberOfActiveSynapses();
	// compute minimumOverlapScore assuming all proximalSegments are
	// connected
	// to the same number of synapses
	Stub_Column[][] columns = this.region.getColumns();
	int regionMinimumOverlapScore = this.region.getMinimumOverlapScore();
	if (newOverlapScore < regionMinimumOverlapScore) {
	    newOverlapScore = 0;
	} else {
	    newOverlapScore = (int) (newOverlapScore * column.getBoostValue());
	}
	column.setOverlapScore(newOverlapScore);
    }

    /**
     * This method is called by performSpatialPooling and computes the
     * activeColumns(t)-the list of columns that win due to the bottom-up input
     * at time t.
     */
    public void computeActiveColumnsOfRegion() {
	List<Stub_Column> neighborColumns = new ArrayList<Stub_Column>();
	Stub_Column[][] columns = this.region.getColumns();
	for (int x = 0; x < columns.length; x++) {
	    for (int y = 0; y < columns[0].length; y++) {
		columns[x][y].setActiveState(false);
		this.updateNeighborColumns(x, y);
		neighborColumns = columns[x][y].getNeighborColumns(); // necessary
								      // for
								      // calculating
								      // kthScoreOfColumns

		int minimumLocalOverlapScore = this.kthScoreOfColumns(
			neighborColumns, this.region.getDesiredLocalActivity());

		// more than (this.region.desiredLocalActivity) number of
		// columns can become active since it is applied to each Column
		// object's neighborColumns
		if (columns[x][y].getOverlapScore() > 0
			&& columns[x][y].getOverlapScore() >= minimumLocalOverlapScore) {
		    columns[x][y].setActiveState(true);
		    this.addActiveColumn(columns[x][y]); // use for temporal
							 // pooler
		}
	    }
	}
    }

    /**
     * This method models spike-timing dependent plasticity. Changes synapses'
     * permanenceValues based on input. The inhibitionRadius for the Region is
     * also computed and updated here.
     */
    public void regionLearnOneTimeStep() {
	Stub_Column[][] columns = this.region.getColumns();
	for (int x = 0; x < columns.length; x++) {
	    for (int y = 0; y < columns[0].length; y++) {
		if (columns[x][y].getActiveState()) {
		    // increase and decrease of proximal segment synapses based
		    // on each Synapses's activeState
		    Set<Stub_Synapse<Stub_Cell>> synapses = columns[x][y]
			    .getProximalSegment().getSynapses();
		    for (Stub_Synapse<Stub_Cell> synapse : synapses) {
			if (synapse.getAbstractCell() != null
				&& synapse.getAbstractCell().getActiveState()) {
			    synapse.increasePermanence();
			} else {
			    synapse.decreasePermanence();
			}
		    }
		}
	    }
	}

	for (int x = 0; x < columns.length; x++) {
	    for (int y = 0; y < columns[0].length; y++) {
		if (columns[x][y].getActiveState()) {
		    // increase and decrease of proximal segment Synapses based
		    // on each Synapses's activeState
		    // columns[x][y].performBoosting();

		    // 2 methods to help a column learn connections:
		    // 1) If activeDutyCycle(measures winning rate) is too low.
		    // The overall boost value of the columns is increased.
		    // 2) If overlapDutyCycle(measures connected synapses with
		    // inputs) is too low, the permanence values of the
		    // Column's synapses are boosted.
		    this.updateNeighborColumns(x, y);

		    List<Stub_Column> neighborColumns = columns[x][y]
			    .getNeighborColumns();

		    float maximumActiveDutyCycle = columns[x][y]
			    .maximumActiveDutyCycle(neighborColumns);

		    // minDutyCycle represents the minimum desired firing rate
		    // for a
		    // cell(number of times is becomes active over some number
		    // of
		    // iterations).
		    // If a cell's firing rate falls below this value, it will
		    // be boosted.
		    float minimumDutyCycle = 0.01f * maximumActiveDutyCycle;

		    // TODO: make 0.01f a Region field percentMinimumFiringRate

		    // 1) boost if activeDutyCycle is too low
		    columns[x][y].updateActiveDutyCycle();

		    columns[x][y].setBoostValue(columns[x][y]
			    .boostFunction(minimumDutyCycle));

		    // 2) boost if overlapDutyCycle is too low
		    this.updateOverlapDutyCycle(x, y);
		    if (columns[x][y].getOverlapDutyCycle() < minimumDutyCycle) {
			columns[x][y]
				.increaseProximalSegmentSynapsePermanences(10);
			// TODO: more biologically accurate
		    }
		}
	    }
	}
	this.region
		.setInhibitionRadius((int) averageReceptiveFieldSizeOfRegion());
    }

    /**
     * Adds all columns within inhitionRadius of the parameter column to the
     * neighborColumns field within the parameter column.
     */
    public void updateNeighborColumns(int columnXAxis, int columnYAxis) {
	if (columnXAxis < 0 || columnXAxis > this.region.getXAxisLength()
		|| columnYAxis < 0
		|| columnYAxis > this.region.getYAxisLength()) {
	    throw new IllegalArgumentException(
		    "the column being updated by the updateNeighborColumns method"
			    + "in SpatialPooler does not exist within the region");
	}
	int localInhibitionRadius = this.region.getInhibitionRadius();
	assert (localInhibitionRadius >= 0);

	// forced inhibition of adjacent columns
	int xInitial = Math.max(0, columnXAxis - localInhibitionRadius);
	int yInitial = Math.max(0, columnYAxis - localInhibitionRadius);
	int xFinal = Math.min(this.region.getXAxisLength(), columnXAxis
		+ localInhibitionRadius);
	int yFinal = Math.min(this.region.getYAxisLength(), columnYAxis
		+ localInhibitionRadius);

	// to allow double for loop to reach end portion of this.allColumns
	xFinal = Math.min(this.region.getXAxisLength(), xFinal + 1);
	yFinal = Math.min(this.region.getYAxisLength(), yFinal + 1);

	Stub_Column[][] columns = this.region.getColumns();
	List<Stub_Column> neighborColumns = columns[columnXAxis][columnYAxis]
		.getNeighborColumns();

	if (neighborColumns != null) {
	    neighborColumns.clear(); // remove neighbors of column computed with
				     // old inhibitionRadius
	}

	for (int columnIndex = xInitial; columnIndex < xFinal; columnIndex++) {
	    for (int rowIndex = yInitial; rowIndex < yFinal; rowIndex++) {
		if (columnIndex == columnXAxis && rowIndex == columnYAxis) {
		    // TODO: To make inhibition a circle around input column,
		    // change to remove corners of this rectangle inhibition
		} else {
		    Stub_Column newColumn = columns[columnIndex][rowIndex];
		    if (newColumn != null) {
			neighborColumns.add(newColumn);
		    }
		}
	    }
	}
    }

    /**
     * Given a list of columns, return the kth highest overlapScore value of a
     * Column object within that list.
     */
    public int kthScoreOfColumns(List<Stub_Column> neighborColumns,
	    int desiredLocalActivity) {
	if (neighborColumns == null) {
	    throw new IllegalArgumentException(
		    "neighborColumns in SpatialPooler method kthScoreOfColumns cannot be null");
	}
	// TreeSet data structures are automatically sorted.
	Set<Integer> overlapScores = new TreeSet<Integer>();
	for (Stub_Column column : neighborColumns) {
	    overlapScores.add(column.getOverlapScore());
	}

	// if invalid or no local activity is desired, it is changed so that the
	// highest overlapScore is returned.
	if (desiredLocalActivity <= 0) {
	    throw new IllegalStateException(
		    "desiredLocalActivity in SpatialPooler mehtod kthScoreOfColumns cannot be <= 0");
	}

	// k is the index of the overlapScore to be returned. The overlapScore
	// is the score at position k(counting from the top) of all
	// overlapScores when arranged from smallest to greatest.
	int k = Math.max(0, overlapScores.size() - desiredLocalActivity);
	return (Integer) overlapScores.toArray()[k];
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
    public float averageReceptiveFieldSizeOfRegion() {
	double totalSynapseDistanceFromOriginColumn = 0.0;
	int numberOfSynapses = 0;
	Stub_Column[][] columns = this.region.getColumns();
	for (int x = 0; x < columns.length; x++) {
	    for (int y = 0; y < columns[0].length; y++) {
		if (columns[x][y] != null) {
		    Set<Stub_Synapse<Stub_Cell>> synapses = columns[x][y]
			    .getProximalSegment().getSynapses();
		    Set<Stub_Synapse<Stub_Cell>> connectedSynapes = new HashSet<Stub_Synapse<Stub_Cell>>();
		    for (Stub_Synapse<Stub_Cell> synapse : synapses) {
			if (synapse.getAbstractCell() != null) {
			    connectedSynapes.add(synapse);
			}
		    }

		    // iterates over every connected synapses and sums the
		    // distances
		    // from it's original Column to determine the average
		    // receptive
		    // field
		    for (Stub_Synapse synapse : connectedSynapes) {
			double dx = x - synapse.getAbstractCellXPosition();
			double dy = y - synapse.getAbstractCellYPosition();
			double synapseDistance = Math.sqrt(dx * dx + dy * dy);
			totalSynapseDistanceFromOriginColumn += synapseDistance;
			numberOfSynapses++;
		    }
		}
	    }
	}
	return (float) (totalSynapseDistanceFromOriginColumn / numberOfSynapses);
    }

    public void addActiveColumn(Stub_Column activeColumn) {
	if (activeColumn == null) {
	    throw new IllegalArgumentException(
		    "activeColumn in SpatialPooler method addActiveColumn cannot be null");
	}
	this.activeColumns.add(activeColumn);
    }

    /**
     * Compute a moving average of how often this Column has overlap greater
     * than minimumDutyOverlap. Exponential Moving Average(EMA): St = a * Yt +
     * (1 - a) * St - 1.
     */
    public void updateOverlapDutyCycle(int columnXAxis, int columnYAxis) {
	if (columnXAxis < 0 || columnXAxis > this.region.getXAxisLength()
		|| columnYAxis < 0
		|| columnYAxis > this.region.getYAxisLength()) {
	    throw new IllegalArgumentException(
		    "the column being updated by the updateOverlapDutyCycle method"
			    + "in SpatialPooler does not exist within the region");
	}
	Stub_Column[][] columns = this.region.getColumns();
	float newOverlapDutyCycle = (1.0f - Stub_Column.EXPONENTIAL_MOVING_AVERAGE_AlPHA)
		* columns[columnXAxis][columnYAxis].getOverlapDutyCycle();

	if (columns[columnXAxis][columnYAxis].getOverlapScore() > this.region
		.getMinimumOverlapScore()) {
	    newOverlapDutyCycle += Stub_Column.EXPONENTIAL_MOVING_AVERAGE_AlPHA;
	}
	columns[columnXAxis][columnYAxis]
		.setOverlapDutyCycle(newOverlapDutyCycle);
    }

    @Override
    public String toString() {
	StringBuilder stringBuilder = new StringBuilder();
	stringBuilder.append("\n=============================");
	stringBuilder.append("\n--SpatialPooler Information--");
	stringBuilder.append("\nbiological region name: ");
	stringBuilder.append(this.region.getBiologicalName());
	stringBuilder.append("\n# of activeColumns produced: ");
	stringBuilder.append(this.activeColumns.size());
	stringBuilder.append("\n=============================");
	String spatialPoolerInformation = stringBuilder.toString();
	return spatialPoolerInformation;
    }
}
