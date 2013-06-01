package model.MARK_II;

import model.MARK_II.ConnectTypes.RegionToRegionConnect;

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
public class Neocortex {
    private Region rootRegion;
    private Region currentRegion; // analogous to current directory
    private RegionToRegionConnect connectType;

    public Neocortex(Region rootRegion, RegionToRegionConnect neocortexRegionToNeocortexRegion) {
	if (rootRegion == null) {
	    throw new IllegalArgumentException(
		    "rootRegion in Neocortex constructor cannot be null");
	} else if (neocortexRegionToNeocortexRegion == null) {
	    throw new IllegalArgumentException(
		    "functor in Neocortex constructor cannot be null");
	}
	this.rootRegion = rootRegion;
	this.currentRegion = this.rootRegion;
	this.connectType = neocortexRegionToNeocortexRegion;
    }

    /**
     * Sets the parameter newCurrentRegion as the currentRegion within
     * Neocortex.
     *
     * @param newCurrentRegion
     *            The region you would like to add child region(s) to.
     */
    public void changeCurrentRegion(Region newCurrentRegion) {
	if (newCurrentRegion == null) {
	    throw new IllegalArgumentException(
		    "newCurrentRegion in Neocortex method changeCurrentRegion cannot be null");
	}
	// TODO: check if newCurrentRegion exists within neocortex
	this.currentRegion = newCurrentRegion;
    }

    /**
     * @param childRegion
     *            The region to be added to the currentRegion.
     */
    public void addToCurrentRegion(Region childRegion) {
	if (childRegion == null) {
	    throw new IllegalArgumentException(
		    "childRegion in Neocortex method addToCurrentRegion cannot be null");
	}
	this.currentRegion.addChildRegion(childRegion);
	// connect currentRegion to childRegion
	connectType.connect(this.currentRegion, childRegion, 0, 0);
    }

    // TODO: post-order traversal with running learning algorithm
}
