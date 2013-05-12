package model;

public class Stub_MARKII_Neocortex {
    private Stub_MARKII_Region rootRegion;
    private Stub_MARKII_Region currentRegion; // analogous to current directory
    private Stub_MARKII_ConnectionInterface functor;

    public Stub_MARKII_Neocortex(Stub_MARKII_Region rootRegion, Stub_MARKII_ConnectionInterface functor) {
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
    public boolean changeCurrentRegion(Stub_MARKII_Region newCurrentRegion) {
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
    public boolean addToCurrentRegion(Stub_MARKII_Region childRegion) {
	if (childRegion != null) {
	    this.currentRegion.addChildRegion(childRegion);
	    // connect currentRegion to childRegion
	    functor.connect(this.currentRegion, childRegion);
	    return true;
	} else {
	    return false;
	}
    }

    public Stub_MARKII_Region getRootRegion() {
        return rootRegion;
    }

    public boolean setRootRegion(Stub_MARKII_Region rootRegion) {
	if (rootRegion != null) {
	    this.rootRegion = rootRegion;
	    return true;
	} else {
	    return false;
	}

    }

    public Stub_MARKII_Region getCurrentRegion() {
        return currentRegion;
    }

    public boolean setCurrentRegion(Stub_MARKII_Region currentRegion) {
	if (currentRegion != null) {
	    this.currentRegion = currentRegion;
	    return true;
	} else {
	    return false;
	}

    }

    public Stub_MARKII_ConnectionInterface getFunctor() {
        return functor;
    }

    public boolean setFunctor(Stub_MARKII_ConnectionInterface functor) {
	if (functor != null) {
	    this.functor = functor;
	    return true;
	} else {
	    return false;
	}

    }

    // TODO: post-order traversal with running learning algorithm
}
