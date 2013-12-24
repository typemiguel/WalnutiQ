package model.theory;

import model.MARK_I.ColumnPosition;
import model.MARK_I.Region;

import java.util.HashSet;
import java.util.Set;

/**
 * Although this class only stores a set of ColumnPositions. My current
 * hypothesis of an idea is the sequence of events that lead up to a Column of
 * Neurons to become active within the Brain. An idea includes the time it took
 * for all of the SensorCell properties, Synapse properties, and Neuron
 * properties to active a specific set of Columns.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version July 2, 2013
 */
public class Idea {
    private String name;
    private float attentionPercentage;

    // holds the ColumnPositions that best represent this Idea
    private Set<ColumnPosition> columnPositions;

    // store ColumnPosition frequencies in a 2D int array
    private int[][] activationFrequenciesOfColumns;

    public Idea(String name, Region region) {
	if (name == null) {
	    throw new IllegalArgumentException(
		    "name in Idea class constructor cannot be null");
	} else if (region == null) {
	    throw new IllegalArgumentException(
		    "region in Idea class constructor cannot be null");
	}
	this.name = name;
	this.attentionPercentage = 0;
	this.columnPositions = new HashSet<ColumnPosition>();

	this.activationFrequenciesOfColumns = new int[region.getXAxisLength()][region
		.getYAxisLength()];
    }

    public String getName() {
	return this.name;
    }

    public float getAttentionPercentage() {
	return this.attentionPercentage;
    }

    public void setAttentionPercentage(float attentionPercentage) {
	if (attentionPercentage < 0 || attentionPercentage > 100) {
	    throw new IllegalArgumentException(
		    "attentionPercentage in Idea class constructor must be "
			    + "between 0 and 100");
	} else {
	    this.attentionPercentage = attentionPercentage;
	}
    }

    public Set<ColumnPosition> getColumnPositions() {
	return this.columnPositions;
    }

    public void unionColumnPositions(Set<ColumnPosition> columnPositions) {
	if (columnPositions == null) {
	    throw new IllegalArgumentException(
		    "columnPositions in Idea class unionColumnPositions method "
			    + "cannot be null");
	}
	// the set within this Idea object is unioned with the parameter set of
	// ColumnPositions
	if (this.columnPositions.containsAll(columnPositions)) {
	} else {
	    this.columnPositions.addAll(columnPositions);
	}
    }

    public void updateActiveFrequencies(Set<ColumnPosition> columnPositions) {
	if (columnPositions == null) {
	    throw new IllegalArgumentException(
		    "columnPositions in Idea class updateActiveFrequencies "
			    + "method cannot be null");
	}
	for (ColumnPosition columnPosition : columnPositions) {
	    int x = columnPosition.getX();
	    int y = columnPosition.getY();
	    if (x > this.activationFrequenciesOfColumns.length
		    || y > this.activationFrequenciesOfColumns[0].length) {
		throw new IllegalArgumentException(
			"columnPositions in Idea class updateActiveFrequencies "
				+ "method is out of bounds for the dimensions "
				+ "of the Region this Idea was created from");
	    }
	    this.activationFrequenciesOfColumns[x][y]++;
	}
    }

    public int[][] getActivationFrequenciesOfColumns() {
	return this.activationFrequenciesOfColumns;
    }

    /**
     * In a 2-D int array find X of the greatest number(s) and return their
     * positions as a set of ColumnPositions.
     */
    public Set<ColumnPosition> getTopXActiveColumnPositions(int X) {
	if (X <= 0) {
	    throw new IllegalArgumentException(
		    "X in Idea class getTopXActiveColumnPositions "
			    + "method must be > 0");
	} else if (X > this.activationFrequenciesOfColumns.length
		* this.activationFrequenciesOfColumns[0].length) {
	    throw new IllegalArgumentException(
		    "X in Idea class getTopXActiveColumnPositions "
			    + "method must be <= number of Columns in the "
			    + "Region this Idea was trained on.");
	}

	// ColumnPosition at mostActiveColumnPositions[i]'s activation frequency
	// will be in the int array mostActiveFrequencies at index i
	ColumnPosition[] mostActiveColumnPositions = new ColumnPosition[X];
	int[] mostActiveFrequencies = new int[X];

	for (int x = 0; x < this.activationFrequenciesOfColumns.length; x++) {
	    for (int y = 0; y < this.activationFrequenciesOfColumns[x].length; y++) {
		// afoc = activation frequency of Column at position (x, y)
		int afoc = this.activationFrequenciesOfColumns[x][y];

		// check if afoc is greater than the smallest afoc in the
		// array mostActiveFrequencies
		int smallestAFOC = mostActiveFrequencies[0];
		int indexOfSmallestAFOC = 0;
		for (int i = 1; i < mostActiveFrequencies.length; i++) {
		    if (smallestAFOC > mostActiveFrequencies[i]) {
			smallestAFOC = mostActiveFrequencies[i];
			indexOfSmallestAFOC = i;
		    }
		}

		if (afoc > smallestAFOC) {
		    mostActiveFrequencies[indexOfSmallestAFOC] = afoc;
		    ColumnPosition columnPosition = new ColumnPosition(x, y);
		    mostActiveColumnPositions[indexOfSmallestAFOC] = columnPosition;
		}
	    }
	}

	Set<ColumnPosition> setOfSizeX = new HashSet<ColumnPosition>();
	// add ColumnPositions in mostActiveColumnPositions to setOfSizeX
	for (int i = 0; i < mostActiveColumnPositions.length; i++) {
	    setOfSizeX.add(mostActiveColumnPositions[i]);
	}
	return setOfSizeX;
    }

    @Override
    public String toString() {
	StringBuilder stringBuilder = new StringBuilder();
	stringBuilder.append("\n===========================");
	stringBuilder.append("\n-----Idea Information------");
	stringBuilder.append("\n                name: ");
	stringBuilder.append(this.name);
	stringBuilder.append("\n attentionPercentage: ");
	stringBuilder.append(this.attentionPercentage);
	stringBuilder.append("\n# of ColumnPositions: ");
	stringBuilder.append(this.columnPositions.size());
	stringBuilder.append("\n===========================");
	String synapseInformation = stringBuilder.toString();
	return synapseInformation;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result
		+ ((columnPositions == null) ? 0 : columnPositions.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
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
	Idea other = (Idea) obj;
	if (columnPositions == null) {
	    if (other.columnPositions != null)
		return false;
	} else if (!columnPositions.equals(other.columnPositions))
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	return true;
    }
}
