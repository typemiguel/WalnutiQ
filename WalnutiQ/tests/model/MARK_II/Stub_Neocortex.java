package model.MARK_II;
import model.MARK_II.ConnectTypes.Stub_RegionToRegionConnect;

public class Stub_Neocortex {
    private Stub_Region rootRegion;
    private Stub_Region currentRegion; // analogous to current directory
    private Stub_RegionToRegionConnect connectType;

    public Stub_Neocortex(Stub_Region rootRegion,
	    Stub_RegionToRegionConnect connectType) {
	if (rootRegion == null) {
	    throw new IllegalArgumentException(
		    "rootRegion in Neocortex constructor cannot be null");
	} else if (connectType == null) {
	    throw new IllegalArgumentException(
		    "functor in Neocortex constructor cannot be null");
	}
	this.rootRegion = rootRegion;
	this.currentRegion = this.rootRegion;
	this.connectType = connectType;
    }

    /**
     * Sets the parameter newCurrentRegion as the currentRegion within
     * Neocortex.
     *
     * @param newCurrentRegion
     *            The region you would like to add child region(s) to.
     */
    public void changeCurrentRegion(Stub_Region newCurrentRegion) {
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
    public void addToCurrentRegion(Stub_Region childRegion) {
	if (childRegion == null) {
	    throw new IllegalArgumentException(
		    "childRegion in Neocortex method addToCurrentRegion cannot be null");
	}
	this.currentRegion.addChildRegion(childRegion);
	// connect currentRegion to childRegion
	this.connectType.connect(childRegion, this.currentRegion, 0, 0);
    }

    public Stub_Region getRootRegion() {
	return rootRegion;
    }

    public void setRootRegion(Stub_Region rootRegion) {
	if (rootRegion == null) {
	    throw new IllegalArgumentException(
		    "rootRegion in Neocortex method setRootRegion cannot be null");
	}
	this.rootRegion = rootRegion;
    }

    public Stub_Region getCurrentRegion() {
	return currentRegion;
    }

    public void setCurrentRegion(Stub_Region currentRegion) {
	if (currentRegion == null) {
	    throw new IllegalArgumentException(
		    "currentRegion in Neocortex method setCurrentRegion cannot be null");
	}
	this.currentRegion = currentRegion;
    }

    public Stub_RegionToRegionConnect getConnectType() {
	return this.connectType;
    }

    public void setConnectType(Stub_RegionToRegionConnect connectType) {
	if (connectType == null) {
	    throw new IllegalArgumentException(
		    "connectType in Neocortex method setConnectType cannot be null");
	}
	this.connectType = connectType;
    }

    // TODO: post-order traversal with running learning algorithm
}
