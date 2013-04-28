package model;

import java.io.Serializable;

/**
 * Provides the minimal behaviors for a cell.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @author Michael Cogswell (cogswell@vt.edu)
 * @version MARK II | April 4, 2013
 */
public abstract class MARKII_AbstractCell implements Serializable {
    protected boolean isActive;
    private int x;
    private int y;

    public MARKII_AbstractCell() {
	this.isActive = false;
    }

    public boolean getActiveState() {
	return this.isActive;
    }

    public int getX() {
	return this.x;
    }

    public int getY() {
	return this.y;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + (isActive ? 1231 : 1237);
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
	MARKII_AbstractCell other = (MARKII_AbstractCell) obj;
	if (isActive != other.isActive)
	    return false;
	return true;
    }
}
