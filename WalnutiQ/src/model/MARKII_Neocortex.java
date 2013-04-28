package model;

import java.util.List;

/**
 * Neocortex is a undirected tree of regions. Creating a neocortex with multiple
 * regions is similar to creating a file system. You can change where you are
 * within the neocortex tree with the changeCurrentRegion(Region) and then add a
 * child region to the currentRegion with addRegion(Region).
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @author Michael Cogswell (cogswell@vt.edu)
 * @version MARK II | April 13, 2013
 */
public class MARKII_Neocortex {
    private MARKII_Region rootRegion;
    private MARKII_Region currentRegion; // analogous to current directory
    private ConnectionInterface functor;

    public MARKII_Neocortex(MARKII_Region rootRegion, ConnectionInterface functor) {
	this.rootRegion = rootRegion;
	this.currentRegion = this.rootRegion;
	this.functor = functor;
    }

    /**
     * Sets the parameter newCurrentRegion as the currentRegion within
     * Neocortex.
     *
     * @param newCurrentRegion
     *            The region you would like to add child region(s) to.
     * @return true if newCurrentRegion exists within Neocortex, false if
     *         newCurrentRegion does not exist or is null. *
     */
    public boolean changeCurrentRegion(MARKII_Region newCurrentRegion) {
	if (newCurrentRegion != null) {
	    // TODO: check if newCurrentRegion exists within neocortex
	    this.currentRegion = newCurrentRegion;
	    return true;
	} else {
	    return false;
	}
    }

    /**
     * @param childRegion
     *            The region to be added to the currentRegion.
     * @return true if region parameter is not null, otherwise return false.
     */
    public boolean addToCurrentRegion(MARKII_Region childRegion) {
	if (childRegion != null) {
	    this.currentRegion.addChildRegion(childRegion);
	    // connect currentRegion to childRegion
	    functor.connect(this.currentRegion, childRegion);
	    return true;
	} else {
	    return false;
	}
    }

    // TODO: post-order traversal with running learning algorithm
}
