package model.theory;

import model.MARK_II.ColumnPosition;
import java.util.HashSet;
import java.util.Set;

/**
 * Although this class only stores a set of ColumnPositions. My current hypothesis of an
 * idea is the sequence of events that lead up to a Column of Neurons to become
 * active within the Brain. An idea includes the time it took for all of the
 * SensorCell properties, Synapse properties, and Neuron properties to active a
 * specific set of Columns.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK II | June 18, 2013
 */
public class Idea {
    private String name;
    private float attentionPercentage;

    private Set<ColumnPosition> columnPositions;

    public Idea(String name) {
	if (name == null) {
            throw new IllegalArgumentException(
        	    "name in Idea class constructor cannot be null");
        }
	this.name = name;
	this.attentionPercentage = 0;
	this.columnPositions = new HashSet<ColumnPosition>();
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
		    "attentionPercentage in Idea class constructor must be between 0 and 100");
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
        	    "columnPositions in Idea class unionColumnPositions method cannot be null");
        }
	// the set within this Idea object is unioned with the parameter set of
	// ColumnPositions
	if (this.columnPositions.containsAll(columnPositions)) {
	} else {
	    this.columnPositions.addAll(columnPositions);
	}
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + Float.floatToIntBits(attentionPercentage);
	result = prime * result + ((columnPositions == null) ? 0 : columnPositions.hashCode());
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
	if (Float.floatToIntBits(attentionPercentage) != Float
		.floatToIntBits(other.attentionPercentage))
	    return false;
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
