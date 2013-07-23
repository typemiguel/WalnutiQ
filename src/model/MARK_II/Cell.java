package model.MARK_II;

/**
 * Provides the minimal behaviors for a Cell.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @author Michael Cogswell (cogswell@vt.edu)
 * @version MARK II | July 22, 2013
 */
public class Cell {
    protected boolean isActive;
    protected boolean wasActive;

    public Cell() {
	this.isActive = false;
	this.wasActive = false;
    }

    public boolean getActiveState() {
	return this.isActive;
    }

    public void setActiveState(boolean isActive) {
	this.isActive = isActive;
    }

    public boolean getPreviousActiveState() {
	return this.wasActive;
    }

    public void setPreviousActiveState(boolean wasActive) {
	this.wasActive = wasActive;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + (isActive ? 1231 : 1237);
	result = prime * result + (wasActive ? 1231 : 1237);
	return result;
    }

    @Override
    public boolean equals(Object object) {
	boolean result = false;
	if (object instanceof Cell) {
	    Cell otherCell = (Cell) object;
	    result = (this.isActive == otherCell.isActive && this.wasActive == otherCell.wasActive);
	}
	return result;
    }
}
