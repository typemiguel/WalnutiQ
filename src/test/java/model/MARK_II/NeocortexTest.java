package model.MARK_II;

import junit.framework.TestCase;
import model.MARK_II.connectTypes.AbstractRegionToRegionConnect;
import model.MARK_II.connectTypes.RegionToRegionRandomConnect;
import model.MARK_II.connectTypes.RegionToRegionRectangleConnect;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version June 13, 2013
 */
public class NeocortexTest extends TestCase {
    private Neocortex neocortex;

    public void setUp() {
//        // parameters 20 & 3 are randomly chosen in this example
//        Region rootRegion = new Region("rootRegion", 10, 10, 4, 20, 3);
//        AbstractRegionToRegionConnect connectType = new RegionToRegionRectangleConnect();
//
//        this.neocortex = new Neocortex(rootRegion);
    }

    public void test_runSpatialAndTemporalPoolingLearningAlgorithm() {
//        Region childRegion1 = new Region("childRegion1", 25, 25, 4, 20, 3);
//        Region childRegion2 = new Region("childRegion2", 50, 50, 4, 20, 3);
//
//        this.neocortex.addToCurrentRegion(childRegion1, new RegionToRegionRectangleConnect(), 0, 0); // ... overlapX, overlapY
//        this.neocortex.addToCurrentRegion(childRegion2, new RegionToRegionRandomConnect(), 2, 3);
//        // Can only add up to 2 child regions. Code figures out how to arrange them
//
//        this.neocortex.changeCurrentRegion("rootRegion"); // binary searches based on Region String name
//        // use internal .getParentRegion() .getLeftChildRegion() .getRightChildRegion()
//
//        this.neocortex.runSpatialAndTemporalPoolingLearningAlgorithm(); // runs 1 iteration of both spatial & temporal
//        // & nextTimeStep() use post-order traversal of tree
    }
}
