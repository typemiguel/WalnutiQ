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
}
