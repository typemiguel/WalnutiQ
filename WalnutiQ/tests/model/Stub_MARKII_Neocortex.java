package model;

public class Stub_MARKII_Neocortex {
    private MARKII_Region rootRegion;
    private MARKII_Region currentRegion; // analogous to current directory
    private ConnectionInterface functor;

    public Stub_MARKII_Neocortex(MARKII_Region rootRegion, ConnectionInterface functor) {
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

    public MARKII_Region getRootRegion() {
        return rootRegion;
    }

    public void setRootRegion(MARKII_Region rootRegion) {
        this.rootRegion = rootRegion;
    }

    public MARKII_Region getCurrentRegion() {
        return currentRegion;
    }

    public void setCurrentRegion(MARKII_Region currentRegion) {
        this.currentRegion = currentRegion;
    }

    public ConnectionInterface getFunctor() {
        return functor;
    }

    public void setFunctor(ConnectionInterface functor) {
        this.functor = functor;
    }

    // TODO: post-order traversal with running learning algorithm
}
