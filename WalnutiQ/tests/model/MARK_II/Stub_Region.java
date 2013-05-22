package model.MARK_II;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Stub_Region implements Serializable {
    private String biologicalName;
    private List<Stub_Region> children;

    private Stub_Column[][] columns;

    private double percentMinimumOverlapScore;

    // Example: if = 10, a column will be a winner if its overlapScore is > than
    // the overlapScore of the 10th highest column within its inhibitionRadius
    private int desiredLocalActivity;

    private int inhibitionRadius;

    public Stub_Region(String biologicalName,
	    int numberOfColumnsAlongXAxis, int numberOfColumnsAlongYAxis,
	    int cellsPerColumn, double percentMinimumOverlapScore,
	    int desiredLocalActivity) {
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
	this.children = new ArrayList<Stub_Region>();
	this.columns = new Stub_Column[numberOfColumnsAlongXAxis][numberOfColumnsAlongYAxis];

	for (int x = 0; x < numberOfColumnsAlongXAxis; x++) {
	    for (int y = 0; y < numberOfColumnsAlongYAxis; y++) {
		this.columns[x][y] = new Stub_Column(cellsPerColumn);
	    }
	}

	this.percentMinimumOverlapScore = percentMinimumOverlapScore;
	this.desiredLocalActivity = desiredLocalActivity;
	this.inhibitionRadius = 1; // TODO: fix class variable
    }

    public void addChildRegion(Stub_Region region) {
	if (region == null) {
	    throw new IllegalArgumentException(
		    "region in Region method addChildRegion cannot be null");
	}
	this.children.add(region);
    }

    // --------------Getters and Setters---------------
    public Stub_Column[][] getColumns() {
	return this.columns;
    }

    public void setColumns(Stub_Column[][] columns) {
	if (columns == null) {
	    throw new IllegalArgumentException(
		    "columns in Region method setColumns cannot be null");
	}
	this.columns = columns;
    }

    public String getBiologicalName() {
	return this.biologicalName;
    }

    public void setBiologicalName(String biologicalName) {
	if (biologicalName == null) {
	    throw new IllegalArgumentException(
		    "biologicalName in Region method setBiologicalName cannot be null");
	}
	this.biologicalName = biologicalName;
    }

    public int getMinimumOverlapScore() {
	// this assumes all proximal segments will have the same number of
	// synapses
	return (int) (this.percentMinimumOverlapScore * columns[0][0]
		.getProximalSegment().getSynapses().size());
    }

    public void setPercentMinimumOverlapScore(double percentMinimumOverlapScore) {
	if (percentMinimumOverlapScore < 0 || percentMinimumOverlapScore > 100) {
	    throw new IllegalArgumentException(
		    "percentMinimumOverlapScore in Region method setPercentMinimumOverlapScore must be between 0 and 100");
	}
	this.percentMinimumOverlapScore = this.percentMinimumOverlapScore;
    }

    public int getDesiredLocalActivity() {
	return this.desiredLocalActivity;
    }

    public void setDesiredLocalActivity(int desiredLocalActivity) {
	if (desiredLocalActivity < 0
		|| desiredLocalActivity > (this.getXAxisLength() * this
			.getYAxisLength())) {
	    throw new IllegalArgumentException(
		    "desiredLocalActivity in Region method setDesiredLocalActivity must be between 0 and the total number of columns within a region");
	}
	this.desiredLocalActivity = desiredLocalActivity;
    }

    public int getInhibitionRadius() {
	return this.inhibitionRadius;
    }

    public void setInhibitionRadius(int inhibitionRadius) {
	if (inhibitionRadius < 0 || inhibitionRadius > this.getXAxisLength()
		|| inhibitionRadius > this.getYAxisLength()) {
	    throw new IllegalArgumentException(
		    "inhibition in Region method setInhibitionRadius must be >= 0 and < the number of columns along boths sides of the region");
	}
	this.inhibitionRadius = inhibitionRadius;
    }

    public int getXAxisLength() {
	return this.columns.length;
    }

    public int getYAxisLength() {
	return this.columns[0].length;
    }

    @Override
    public String toString() {
	StringBuilder stringBuilder = new StringBuilder();
	stringBuilder.append("\n====================================");
	stringBuilder.append("\n------------Region Info-------------");
	stringBuilder.append("\n       name of this region: ");
	stringBuilder.append(this.biologicalName);
	stringBuilder.append("\n     child region(s) names: ");
	for (Stub_Region region : this.children) {
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
	stringBuilder.append("\n      desiredLocalActivity: ");
	stringBuilder.append(this.desiredLocalActivity);
	stringBuilder.append("\n          inhibitionRadius: ");
	stringBuilder.append(this.inhibitionRadius);
	stringBuilder.append("\n=====================================");
	String regionInformation = stringBuilder.toString();
	return regionInformation;
    }
}
