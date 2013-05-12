package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Stub_MARKII_Region implements Serializable {
    private String biologicalName;
    private List<Stub_MARKII_Region> children;

    private Stub_MARKII_Column[][] columns;

    private double percentMinimumOverlapScore;

    // Example: if = 10, a column will be a winner if its overlapScore is > than
    // the overlapScore of the 10th highest column within its inhibitionRadius
    private int desiredLocalActivity;

    private int inhibitionRadius;

    public Stub_MARKII_Region(String biologicalName, int numberOfColumnsAlongXAxis,
	    int numberOfColumnsAlongYAxis, int cellsPerColumn,
	    double percentMinimumOverlapScore, int desiredLocalActivity) {
	this.biologicalName = biologicalName;
	this.children = new ArrayList<Stub_MARKII_Region>();
	this.columns = new Stub_MARKII_Column[numberOfColumnsAlongXAxis][numberOfColumnsAlongYAxis];

	for (int x = 0; x < numberOfColumnsAlongXAxis; x++) {
	    for (int y = 0; y < numberOfColumnsAlongYAxis; y++) {
		this.columns[x][y] = new Stub_MARKII_Column(cellsPerColumn);
	    }
	}

	this.percentMinimumOverlapScore = percentMinimumOverlapScore;
	this.desiredLocalActivity = desiredLocalActivity;
	this.inhibitionRadius = 1; // TODO: fix class variable
    }

    public boolean addChildRegion(Stub_MARKII_Region region) {
	if (region != null)
	{
	    this.children.add(region);
	    return true;
	} else {
	    return false;
	}
    }

    // --------------Getters and Setters---------------
    public Stub_MARKII_Column[][] getColumns() {
	return this.columns;
    }

    public boolean setColumns(Stub_MARKII_Column[][] columns) {
	if (columns != null) {
	    this.columns = columns;
	    return true;
	} else {
	    return false;
	}
    }

    public String getBiologicalName() {
	return this.biologicalName;
    }

    public boolean setBiologicalName(String biologicalName) {
	if (biologicalName != null) {
	    this.biologicalName = biologicalName;
	    return true;
	} else {
	    return false;
	}
    }

    public int getMinimumOverlapScore() {
	// this assumes all proximal segments will have the same number of synapses

	return (int) (this.percentMinimumOverlapScore * columns[0][0]
		.getProximalSegment().getSynapses().size());
    }

    public boolean setPercentMinimumOverlapScore(double percentMinimumOverlapScore) {
	if (percentMinimumOverlapScore >= 0 && percentMinimumOverlapScore <= 100) {
	    this.percentMinimumOverlapScore = this.percentMinimumOverlapScore;
	    return true;
	} else {
	    return false;
	}
    }

    public int getDesiredLocalActivity() {
	return this.desiredLocalActivity;
    }

    public boolean setDesiredLocalActivity(int desiredLocalActivity) {
	if (desiredLocalActivity >= 0) {
    		this.desiredLocalActivity = desiredLocalActivity;
    		return true;
	} else {
	    return false;
	}
    }

    public int getInhibitionRadius() {
	return this.inhibitionRadius;
    }

    public boolean setInhibitionRadius(int inhibitionRadius) {
	if (inhibitionRadius >= 0 && inhibitionRadius <= this.columns.length &&
		inhibitionRadius <= this.columns[0].length) {
	    this.inhibitionRadius = inhibitionRadius;
	    return true;
	} else {
	    return false;
	}
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
	for (Stub_MARKII_Region region : this.children) {
	    stringBuilder.append(region.getBiologicalName() + ", ");
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

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result
		+ ((biologicalName == null) ? 0 : biologicalName.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Stub_MARKII_Region other = (Stub_MARKII_Region) obj;
	if (biologicalName == null) {
	    if (other.biologicalName != null)
		return false;
	} else if (!biologicalName.equals(other.biologicalName))
	    return false;
	return true;
    }
}
