package model.MARK_II;

import model.MARK_II.connectTypes.AbstractRegionToRegionConnect;
import model.util.Rectangle;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Neocortex is a tree(undirected graph) of Regions. Each Region in the Neocortex
 * can have as many children Regions as necessary. Each Region can receive
 * input from input SensorCellLayers, a lower Region, or a higher Region.
 *
 * Input to Neocortex: activity of Cells within VisionCellLayer, AudioCellLayer,
 * etc.
 *
 * Output from Neocortex: activity of Cells/Columns within root Region.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @author Michael Cogswell (cogswell@vt.edu)
 * @version July 12, 2014
 */
public class Neocortex {
    private Region rootRegion;
    private Region currentRegion;
    private AbstractRegionToRegionConnect connectType;

    public Neocortex(Region rootRegion, AbstractRegionToRegionConnect neocortexRegionToNeocortexRegion) {
        if (rootRegion == null) {
            throw new IllegalArgumentException(
                    "rootRegion in Neocortex constructor cannot be null");
        }
        this.rootRegion = rootRegion;
        this.currentRegion = this.rootRegion;

        if (neocortexRegionToNeocortexRegion == null) {
            throw new IllegalArgumentException(
                    "connectType in class Neocortex constructor cannot be null");
        }
        this.connectType = neocortexRegionToNeocortexRegion;
    }

    /**
     * Traverses the regions within this neocortex to find the region the desired biological name
     * and changes the currentRegion to point to this region.
     *
     * @param newCurrentRegionBiologicalName
     */
    public void changeCurrentRegionTo(String newCurrentRegionBiologicalName) {
        if (newCurrentRegionBiologicalName == null) {
            throw new IllegalArgumentException(
                    "newCurrentRegionBiologicalName in class Neocortex method changeCurrentRegionTo() cannot be null");
        }
        Region newCurrentRegion = this.getRegion(newCurrentRegionBiologicalName);
        if (newCurrentRegion == null) {
            throw new IllegalArgumentException("newCurrentRegionBiologicalName = " + newCurrentRegionBiologicalName
                    + " in class Neocortex method changeCurrentRegionTo() does not exist in the Neocortex");
        }
        // TODO: actually implement
        this.currentRegion = newCurrentRegion;
    }

    public Region getRegion(String regionBiologicalName) {
        if (regionBiologicalName == null) {
            throw new IllegalArgumentException(
                    "newCurrentRegionBiologicalName in class Neocortex method changeCurrentRegionTo() cannot be null");
        }

        // TODO: search the neocortex for the region name
        // since the neocortex is a undirected tree of regions this will take linear time
        // just use BFS shown here: https://gist.github.com/gennad/791932

        // TODO: check http://stackoverflow.com/questions/25028939/where-is-an-generic-breath-first-search-algorithm-for-custom-nodes

        // here a Region is a Node
        Queue<Region> queue = new LinkedList<Region>();
        queue.add(this.rootRegion);


        return null;
    }

    /**
     * @param childRegion The Region to be added to the currentRegion.
     */
    public boolean addToCurrentRegion(Rectangle rectanglePartOfParentRegionToConnectTo, Region childRegion,
                                      int numberOfColumnsToOverlapAlongNumberOfRows,
                                      int numberOfColumnsToOverlapAlongNumberOfColumns) {
        if (childRegion == null) {
            throw new IllegalArgumentException(
                    "childRegion in class Neocortex method addToCurrentRegion cannot be null");
        }

        // TODO: throw an exception is Region with the exact same biological name is already in the Neocortex and tell user to change biological name to be more specific

        this.currentRegion.addChildRegion(childRegion);
        // connect currentRegion to childRegion
        //this.connectType.connect(childRegion, this.currentRegion, 0, 0);
        return false;
    }

    public boolean runSingleLearningAlgorithmOneTimeStep() {
        // TODO: post-order traversal with running learning algorithm
        return false;
    }

    public Region getCurrentRegion() {
        return this.currentRegion;
    }
}
