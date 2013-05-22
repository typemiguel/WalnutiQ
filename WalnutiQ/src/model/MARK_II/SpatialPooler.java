package model.MARK_II;

import java.util.ArrayList;

import java.util.TreeSet;

import java.util.List;

import java.util.Set;
import java.util.HashSet;

/**
 * Provides implementation for running the spatial learning algorithm on a
 * region. This class provides methods that simulate brain activity within the
 * neocortex for object recognition.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK II | April 28, 2013
 */
public class SpatialPooler extends Pooler {
    private Set<Column> activeColumns;

    public SpatialPooler(Region newRegion) {
	if (newRegion == null) {
	    throw new IllegalArgumentException(
		    "newRegion in SpatialPooler constructor cannot be null");
	}
	this.region = newRegion;
	this.activeColumns = new HashSet<Column>();
    }

    /**
     * This method recomputes all column states within this functional region.
     * Through local inhibition, only a sparse set of columns become active to
     * represent the current 2D array of sensory data or a lower region's
     * output.
     *
     * @return A sparse set of active columns within this region.
     */
    public Set<Column> performSpatialPoolingOnRegion() {
	Column[][] columns = this.region.getColumns();
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
    private void computeColumnOverlapScore(Column column) {
	if (column == null) {
	    throw new IllegalArgumentException(
		    "the column in SpatialPooler method computeColumnOverlapScore cannot be null");
	}
	int newOverlapScore = column.getProximalSegment()
		.getNumberOfActiveSynapses();
	// compute minimumOverlapScore assuming all proximalSegments are
	// connected
	// to the same number of synapses
	Column[][] columns = this.region.getColumns();
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
    private void computeActiveColumnsOfRegion() {
	List<Column> neighborColumns = new ArrayList<Column>();
	Column[][] columns = this.region.getColumns();
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
     * This method models spike-timing dependent plasticity.
     * Changes synapses' permanenceValues based on input. The inhibitionRadius
     * for the Region is also computed and updated here.
     */
    private void regionLearnOneTimeStep() {
	Column[][] columns = this.region.getColumns();
	for (int x = 0; x < columns.length; x++) {
	    for (int y = 0; y < columns[0].length; y++) {
		if (columns[x][y].getActiveState()) {
		    // increase and decrease of proximal segment synapses based
		    // on each Synapses's activeState
		    Set<Synapse<Cell>> synapses = columns[x][y]
			    .getProximalSegment().getSynapses();
		    for (Synapse<Cell> synapse : synapses) {
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

		    List<Column> neighborColumns = columns[x][y]
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
    private void updateNeighborColumns(int columnXAxis, int columnYAxis) {
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

	Column[][] columns = this.region.getColumns();
	List<Column> neighborColumns = columns[columnXAxis][columnYAxis]
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
		    Column newColumn = columns[columnIndex][rowIndex];
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
    private int kthScoreOfColumns(List<Column> neighborColumns,
	    int desiredLocalActivity) {
	if (neighborColumns == null) {
	    throw new IllegalArgumentException(
		    "neighborColumns in SpatialPooler method kthScoreOfColumns cannot be null");
	}
	// TreeSet data structures are automatically sorted.
	Set<Integer> overlapScores = new TreeSet<Integer>();
	for (Column column : neighborColumns) {
	    overlapScores.add(column.getOverlapScore());
	}

	// if invalid or no local activity is desired, it is changed so that the
	// highest overlapScore is returned.
	if (desiredLocalActivity <= 0) {
	    throw new IllegalStateException(
		    "desiredLocalActivity cannot be <= 0");
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
     * number of segments. For spatial pooling since the number of synapses from
     * proximal segments to lower regions neurons is constant after
     * initialization, averageReceptiveFieldSize of a column will remain constant,
     * but will be different for different columns based on how they are
     * connected to the input cell layer or region.
     *
     * @return The average connected receptive field size.
     */
    private float averageReceptiveFieldSizeOfRegion() {
	double totalSynapseDistanceFromOriginColumn = 0.0;
	int numberOfSynapses = 0;
	Column[][] columns = this.region.getColumns();
	for (int x = 0; x < columns.length; x++) {
	    for (int y = 0; y < columns[0].length; y++) {
		if (columns[x][y] != null) {
		    Set<Synapse<Cell>> synapses = columns[x][y]
			    .getProximalSegment().getSynapses();
		    Set<Synapse<Cell>> connectedSynapes = new HashSet<Synapse<Cell>>();
		    for (Synapse<Cell> synapse : synapses) {
			if (synapse.getAbstractCell() != null) {
			    connectedSynapes.add(synapse);
			}
		    }

		    // iterates over every connected synapses and sums the
		    // distances
		    // from it's original Column to determine the average
		    // receptive
		    // field
		    for (Synapse synapse : connectedSynapes) {
//			double dx = x - synapse.getAbstractCell().getX();
//			double dy = y - synapse.getAbstractCell().getY();
//			double synapseDistance = Math.sqrt(dx * dx + dy * dy);
//			totalSynapseDistanceFromOriginColumn += synapseDistance;
			numberOfSynapses++;
		    }
		}
	    }
	}
	return (float) (totalSynapseDistanceFromOriginColumn / numberOfSynapses);
    }

    private void addActiveColumn(Column activeColumn) {
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
    private void updateOverlapDutyCycle(int columnXAxis, int columnYAxis) {
	if (columnXAxis < 0 || columnXAxis > this.region.getXAxisLength()
		|| columnYAxis < 0
		|| columnYAxis > this.region.getYAxisLength()) {
	    throw new IllegalArgumentException(
		    "the column being updated by the updateOverlapDutyCycle method"
			    + "in SpatialPooler does not exist within the region");
	}
	Column[][] columns = this.region.getColumns();
	float newOverlapDutyCycle = (1.0f - Column.EXPONENTIAL_MOVING_AVERAGE_AlPHA)
		* columns[columnXAxis][columnYAxis].getOverlapDutyCycle();

	if (columns[columnXAxis][columnYAxis].getOverlapScore() > this.region
		.getMinimumOverlapScore()) {
	    newOverlapDutyCycle += Column.EXPONENTIAL_MOVING_AVERAGE_AlPHA;
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
