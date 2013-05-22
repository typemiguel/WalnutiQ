package model.theory;

import model.MARK_II.Column;

import java.util.HashSet;
import java.util.Set;

/**
 * Although this class only stores a set of columns. My current hypothesis of an
 * idea is the sequence of events that lead up to a column of neurons to become
 * active within the brain. And idea includes the time it took for all of the
 * input cell properties, synapse properties, and neuron properties to active a
 * specific set of columns.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK II | May 11, 2013
 */
public class Idea {
    private String name;
    private float attentionPercentage;

    // NOTE: a set of columns do not represent an idea
    private Set<Column> columns;

    public Idea(String name) {
	this.name = name;
	this.attentionPercentage = 0;
	this.columns = new HashSet<Column>();
    }

    public String getName() {
	return this.name;
    }

    public float getAttentionPercentage() {
	return this.attentionPercentage;
    }

    public boolean setAttentionPercentage(float attentionPercentage) {
	if (attentionPercentage >= 0 && attentionPercentage <= 100) {
	    this.attentionPercentage = attentionPercentage;
	    return true;
	} else {
	    return false;
	}
    }

    public Set<Column> getColumns() {
	return this.columns;
    }

    public boolean addColumns(Set<Column> columns) {
	// the set within this idea object is unioned with the parameter set of
	// columns
	if (this.columns.containsAll(columns)) {
	    return false;
	} else {
	    this.columns.addAll(columns);
	    return true;
	}
    }

    public boolean removeColumn(Column column) {
        if (this.columns.contains(column)) {
            this.columns.remove(column);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + Float.floatToIntBits(attentionPercentage);
	result = prime * result + ((columns == null) ? 0 : columns.hashCode());
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
	if (columns == null) {
	    if (other.columns != null)
		return false;
	} else if (!columns.equals(other.columns))
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	return true;
    }
}
