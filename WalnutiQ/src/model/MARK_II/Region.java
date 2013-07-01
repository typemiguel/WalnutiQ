package model.MARK_II;

import java.awt.Dimension;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

/**
 * Node within Neocortex tree. A Region object is an undirected graph of Neuron
 * nodes.
 *
 * Input to Region: activity of Cells within a SensorCellLayer. For example,
 * VisionCellLayer, AudioCellLayer, etc.
 *
 * Output from Region: activity of Cells within this Region created by one or
 * more of the Pooler algorithms.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK II | April 13, 2013
 */
public class Region {
    private String biologicalName;
    private List<Region> children;

    private final Column[][] columns;

    private final double percentMinimumOverlapScore;

    // Example: if = 10, a column will be a winner if its overlapScore is > than
    // the overlapScore of the 10th highest column within its inhibitionRadius
    private int desiredLocalActivity;

    private int inhibitionRadius;

    public Region(String biologicalName, int numberOfColumnsAlongXAxis,
	    int numberOfColumnsAlongYAxis, int cellsPerColumn,
	    double percentMinimumOverlapScore, int desiredLocalActivity) {
	if (biologicalName == null) {
	    throw new IllegalArgumentException(
		    "biologicalName in Region constructor cannot be null");
	} else if (numberOfColumnsAlongXAxis < 1) {
	    throw new IllegalArgumentException(
		    "numberOfColumnsAlongXAxis in Region constructor cannot be less than 1");
	} else if (numberOfColumnsAlongYAxis < 1) {
	    throw new IllegalArgumentException(
		    "numberOfColumnsAlongYAxis in Region constructor cannot be less than 1");
	} else if (cellsPerColumn < 1) {
	    throw new IllegalArgumentException(
		    "cellsPerColumn in Region constructor cannot be less than 1");
	} else if (percentMinimumOverlapScore < 0
		|| percentMinimumOverlapScore > 100) {
	    throw new IllegalArgumentException(
		    "percentMinimumOverlapScore in Region constructor must be between 0 and 100");
	} else if (desiredLocalActivity < 0
		|| desiredLocalActivity > (numberOfColumnsAlongXAxis * numberOfColumnsAlongYAxis)) {
	    throw new IllegalArgumentException(
		    "desiredLocalActivity in Region constructor must be between 0 and the total number of columns within a region");
	}
	this.biologicalName = biologicalName;
	this.children = new ArrayList<Region>();
	this.columns = new Column[numberOfColumnsAlongXAxis][numberOfColumnsAlongYAxis];

	for (int x = 0; x < numberOfColumnsAlongXAxis; x++) {
	    for (int y = 0; y < numberOfColumnsAlongYAxis; y++) {
		this.columns[x][y] = new Column(cellsPerColumn);
	    }
	}

	this.percentMinimumOverlapScore = percentMinimumOverlapScore;
	this.desiredLocalActivity = desiredLocalActivity;
	this.inhibitionRadius = 1; // TODO: fix class variable
    }

    public void addChildRegion(Region region) {
	if (region == null) {
	    throw new IllegalArgumentException(
		    "region in Region method addChildRegion cannot be null");
	}
	this.children.add(region);
    }

    // --------------Getters and Setters---------------
    public Column[][] getColumns() {
	return this.columns;
    }

    public String getBiologicalName() {
	return this.biologicalName;
    }

    public int getMinimumOverlapScore() {
	// this assumes all proximal Segments will have the same number of
	// Synapses

	return (int) (this.percentMinimumOverlapScore / 100 * columns[0][0]
		.getProximalSegment().getSynapses().size());
    }

    public int getDesiredLocalActivity() {
	return this.desiredLocalActivity;
    }

    public int getInhibitionRadius() {
	return this.inhibitionRadius;
    }

    public void setInhibitionRadius(int inhibitionRadius) {
	if (inhibitionRadius < 0 || inhibitionRadius > this.getXAxisLength()
		|| inhibitionRadius > this.getYAxisLength()) {
	    throw new IllegalArgumentException(
		    "inhibition in Region class setInhibitionRadius method must " +
		    "be >= 0 and < the number of columns along boths sides of the region");
	}
	this.inhibitionRadius = inhibitionRadius;
    }

//    void setPercentMinimumOverlapScore(double percentMinimumOverlapScore) {
//	if (percentMinimumOverlapScore < 0
//		|| percentMinimumOverlapScore > 1) {
//	    throw new IllegalArgumentException(
//		    "percentMinimumOverlapScore in Region class " +
//		    "setPercentMinimumOverlapScore method must be >= 0 and <= 1");
//	}
//	this.percentMinimumOverlapScore = percentMinimumOverlapScore;
//    }

    public int getXAxisLength() {
	return this.columns.length;
    }

    public int getYAxisLength() {
	return this.columns[0].length;
    }

    public Dimension getBottomLayerXYAxisLength() {
	// get largest Synapse position of largest Column position
	Column columnWithLargestIndex = this.columns[this.columns.length - 1][this.columns[0].length - 1];

	// now find input layer x and y axis lengths whether the input layer
	// is a SensorCellLayer or a Region
	Set<Synapse<Cell>> synapses = columnWithLargestIndex
		.getProximalSegment().getSynapses();
	int greatestSynapseXIndex = 0;
	int greatestSynapseYIndex = 0;
	for (Synapse synapse : synapses) {
	    if (synapse.getCellXPosition() > greatestSynapseXIndex) {
		greatestSynapseXIndex = synapse.getCellXPosition();
	    }
	    if (synapse.getCellYPosition() > greatestSynapseYIndex) {
		greatestSynapseYIndex = synapse.getCellYPosition();
	    }
	}
	// you + 1 to each dimension because in the array the index begins at 0
	// instead of 1
	return new Dimension(greatestSynapseXIndex + 1, greatestSynapseYIndex + 1);
    }

    @Override
    public String toString() {
	StringBuilder stringBuilder = new StringBuilder();
	stringBuilder.append("\n==================================");
	stringBuilder.append("\n-----------Region Info------------");
	stringBuilder.append("\n       name of this region: ");
	stringBuilder.append(this.biologicalName);
	stringBuilder.append("\n     child region(s) names: ");
	for (Region region : this.children) {
	    stringBuilder.append(region.biologicalName + ", ");
	}
	// do not show BinaryCellConnections
	stringBuilder.append("\n # of Columns along X-axis: ");
	stringBuilder.append(this.columns.length);
	stringBuilder.append("\n # of Columns along Y-axis: ");
	stringBuilder.append(this.columns[0].length);
	stringBuilder.append("\n 	       # of layers: ");
	stringBuilder.append(this.columns[0][0].getNeurons().length);
	stringBuilder.append("\npercentMinimumOverlapScore: ");
	stringBuilder.append(this.percentMinimumOverlapScore);
	stringBuilder.append(" %");
	stringBuilder.append("\n      desiredLocalActivity: ");
	stringBuilder.append(this.desiredLocalActivity);
	stringBuilder.append("\n          inhibitionRadius: ");
	stringBuilder.append(this.inhibitionRadius);
	stringBuilder.append("\n===================================");
	String regionInformation = stringBuilder.toString();
	return regionInformation;
    }
}
