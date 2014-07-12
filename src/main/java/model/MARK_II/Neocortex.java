package model.MARK_II;

import model.MARK_II.connectTypes.AbstractRegionToRegionConnect;

/**
 * Neocortex is a undirected tree of Regions. Creating a Neocortex with multiple
 * Regions is similar to creating a file system. You can change where you are
 * within the Neocortex tree with the changeCurrentRegion(Region) and then add a
 * child Region to the currentRegion with addRegion(Region).
 *
 * Input to Neocortex: activity of Cells within VisionCellLayer, AudioCellLayer,
 * etc.
 *
 * Output from Neocortex: activity of Cells/Columns within root Region.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @author Michael Cogswell (cogswell@vt.edu)
 * @version June 8, 2013
 */
public class Neocortex {
    private Region rootRegion;
    private Region currentRegion; // analogous to current directory
    private AbstractRegionToRegionConnect connectType; // TODO: deprecate this variable

    public Neocortex(Region rootRegion) {
        if (rootRegion == null) {
            throw new IllegalArgumentException(
                    "rootRegion in Neocortex constructor cannot be null");
        }
        this.rootRegion = rootRegion;
        this.currentRegion = this.rootRegion;
    }

    public Neocortex(Region rootRegion,
                     AbstractRegionToRegionConnect neocortexRegionToNeocortexRegion) {
        this(rootRegion);
        if (neocortexRegionToNeocortexRegion == null) {
            throw new IllegalArgumentException(
                    "connectType in Neocortex constructor cannot be null");
        }
        this.connectType = neocortexRegionToNeocortexRegion;
    }

    /**
     * Traverses the regions within this neocortex to find the region the desired biological name
     * and changes the currentRegion to point to this region.
     *
     * @param newCurrentRegionBiologicalName
     */
    public boolean changeCurrentRegionTo(String newCurrentRegionBiologicalName) {
        if (newCurrentRegionBiologicalName == null) {
            throw new IllegalArgumentException(
                    "newCurrentRegionBiologicalName in Neocortex method changeCurrentRegionTo() cannot be null");
        }
        // check if newCurrentRegion exists within neocortex

        // TODO: actually implement
        return false;
    }

    /**
     * Sets the parameter newCurrentRegion as the currentRegion within
     * neocortex. // TODO: deprecate this method
     *
     * @param newCurrentRegion The Region you would like to add child region(s) to.
     */
    public void changeCurrentRegion(Region newCurrentRegion) {
        if (newCurrentRegion == null) {
            throw new IllegalArgumentException(
                    "newCurrentRegion in Neocortex method changeCurrentRegion cannot be null");
        }
        // check if newCurrentRegion exists within neocortex
        this.currentRegion = newCurrentRegion;
    }

    public Region getRegion(String regionBiologicalName) {
        // TODO: search the neocortex for the region name
        return null;
    }

    public Region getCurrentRegion() {
        return this.currentRegion;
    }

    /**
     * @param childRegion The Region to be added to the currentRegion.
     */
    public boolean addToCurrentRegion(Region childRegion) {
        if (childRegion == null) {
            throw new IllegalArgumentException(
                    "childRegion in Neocortex method addToCurrentRegion cannot be null");
        }
        this.currentRegion.addChildRegion(childRegion);
        // connect currentRegion to childRegion
        //this.connectType.connect(childRegion, this.currentRegion, 0, 0);
        return false;
    }

    public boolean runSingleLearningAlgorithmOneTimeStep() {
        // TODO: post-order traversal with running learning algorithm
        return false;
    }
}
