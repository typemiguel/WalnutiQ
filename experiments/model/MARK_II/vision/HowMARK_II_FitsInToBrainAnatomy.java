package model.MARK_II.vision;

import com.google.gson.Gson;
import model.ImageViewer;
import model.Layer5Region;
import model.MARK_II.Neocortex;
import model.MARK_II.Region;
import model.MARK_II.connectTypes.AbstractSensorCellsToRegionConnect;
import model.MARK_II.connectTypes.RegionToRegionRandomConnect;
import model.MARK_II.connectTypes.RegionToRegionRectangleConnect;
import model.MARK_II.connectTypes.SensorCellsToRegionRectangleConnect;
import model.Retina;
import model.unimplementedBiology.NervousSystem;
import model.util.JsonFileInputOutput;
import model.util.Point3D;

import java.io.IOException;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version July 12, 2014
 */
public class HowMARK_II_FitsInToBrainAnatomy {
    private NervousSystem partialNervousSystem;

    /**
     * For saving the Java NervousSystem object as a JSON file later on.
     */
    private Gson gson;

    public void setUp() throws IOException {
        //this.partialNervousSystem = this.constructConnectedNervousSystem();

        this.gson = new Gson();
    }

    /**
     * A, B, C, ... Z are Region names
     * M = parietal lobe region meaning it's neuron activity directly causes the Retina to move to it's new
     * position within the box Retina is stuck in.
     *
     *             root (runs just temporal pooling algorithm)
     *            /    \
     *          A        B (runs spatial & temporal pooling learning)
     *          |        |
     *          C        D (higher Layer 3 runs spatial pooling sparsifying & learning algorithm)
     *         / \      / \
     * +<---- E   F    G   H (Layer 4 runs spatial pooling & temporal pooling predictive algorithm)
     * |      |   |    |   |
     * |      I   J    K   L (Layer 3 runs spatial pooling sparsifying & learning algorithm)
     * M      |   |    |   |
     * |       \  |    |  /
     * |  +-----\-|----|-/-----+
     * |  |      \|    |/      |
     * +--------> Retina       | <= box Retina is stuck in
     *    |                    |
     *    ImageRetinaIsLookingAt
     *
     * Detailed image of above diagram here: https://github.com/WalnutiQ/WalnutiQ/issues/107
     */
//    private NervousSystem constructConnectedNervousSystem() {
//
//        final int LAYER_4_CELLS_PER_COLUMN = 4;
//        final int LAYER_3_CELLS_PER_COLUMN = 1;
//        double percentMinimumOverlap = 20;
//        int desiredLocalActivity = 3;
//
//        Neocortex neocortex = new Neocortex(new Region("root", 10, 10, LAYER_4_CELLS_PER_COLUMN, percentMinimumOverlap, desiredLocalActivity));
//        neocortex.addToCurrentRegion(squareOfParentRegionToConnectTo, new Region("A", 25, 25, 4, 20, 3), new RegionToRegionRandomConnect(), 0, 0);
//        neocortex.addToCurrentRegion(squareOfParentRegionToConnectTo, new Layer5Region(("B", 50, 50, 4, 20, 3), new RegionToRegionRandomConnect(), 0, 0);
//
//        neocortex.changeCurrentRegionTo("A");
//        neocortex.addToCurrentRegion(squareOfParentRegionToConnectTo, new Region("C", 25, 25, 4, 20, 3), new RegionToRegionRandomConnect(), 0, 0);
//
//        neocortex.changeCurrentRegionTo("B");
//        neocortex.addToCurrentRegion(squareOfParentRegionToConnectTo, new Region("D", 25, 25, 4, 20, 3), new RegionToRegionRandomConnect(), 0, 0);
//
//        neocortex.changeCurrentRegionTo("C");
//        neocortex.addToCurrentRegion(squareOfParentRegionToConnectTo, new Region("E", 25, 25, 4, 20, 3), new RegionToRegionRandomConnect(), 0, 0);
//        neocortex.addToCurrentRegion(squareOfParentRegionToConnectTo, new Region("F", 25, 25, 4, 20, 3), new RegionToRegionRandomConnect(), 0, 0);
//
//        neocortex.changeCurrentRegionTo("D");
//        neocortex.addToCurrentRegion(squareOfParentRegionToConnectTo, new Region("G", 25, 25, 4, 20, 3), new RegionToRegionRandomConnect(), 0, 0);
//        neocortex.addToCurrentRegion(squareOfParentRegionToConnectTo, new Region("H", 25, 25, 4, 20, 3), new RegionToRegionRandomConnect(), 0, 0);
//
//        Region I = new Region("I", 144, 144, 4, 20, 3);
//        neocortex.changeCurrentRegionTo("E");
//        neocortex.addToCurrentRegion(squareOfParentRegionToConnectTo, I, new RegionToRegionRectangleConnect(), 0, 0);
//
//        Region J = new Region("J", 144, 144, 4, 20, 3);
//        neocortex.changeCurrentRegionTo("F");
//        neocortex.addToCurrentRegion(squareOfParentRegionToConnectTo, J, new RegionToRegionRectangleConnect(), 0, 0);
//
//        Region K = new Region("K", 144, 144, 4, 20, 3);
//        neocortex.changeCurrentRegionTo("G");
//        neocortex.addToCurrentRegion(squareOfParentRegionToConnectTo, K, new RegionToRegionRectangleConnect(), 0, 0);
//
//        Region L = new Region("L", 144, 144, 4, 20, 3);
//        neocortex.changeCurrentRegionTo("H");
//        neocortex.addToCurrentRegion(squareOfParentRegionToConnectTo, L, new RegionToRegionRectangleConnect(), 0, 0);
//
//        // NOTE: I, J, K, & L are connected to different parts of the same Retina
//        Retina retina = new Retina(544, 544);
//
//        AbstractSensorCellsToRegionConnect opticNerve = new SensorCellsToRegionRectangleConnect();
//        opticNerve.connect(retina.getVisionCells(0, 0, 544/2, 544/2), I, 0, 0); // .getVisionCells(topLeftPoint, bottomRightPoint)
//        opticNerve.connect(retina.getVisionCells(0, 544/2, 544/2, 544), J, 0, 0);
//        opticNerve.connect(retina.getVisionCells(544/2, 0, 544, 544/2), K, 0, 0);
//        opticNerve.connect(retina.getVisionCells(544/2, 544/2, 544, 544), L, 0, 0);
//
//        NervousSystem nervousSystem = new NervousSystem(neocortex, null, retina); // no LGN with circle surround input for now
//
//        return nervousSystem;
//    }

//    public void test_HowToRunSingleLearningAlgorithmOnNervousSystem() throws IOException {
//        Neocortex neocortex = this.partialNervousSystem.getCNS().getBrain().getCerebrum().getCerebralCortex().getNeocortex();
//
//        ImageViewer imageViewer = new ImageViewer("imageOfHumanFace544x544pixels.bmp",
//                this.partialNervousSystem.getPNS().getSNS().getRetina());
//
//        int numberOfTimesToRunLearningAlgorithm = 1000;
//        for (int i = 0; i < numberOfTimesToRunLearningAlgorithm; i++) {
//
//            neocortex.runSingleLearningAlgorithmOneTimeStep();
//            Point3D nextRetinaPosition = neocortex.getCurrentRegion("C") // C = ParietalLobeRegion class
//                    .getMotorOutput(imageViewer.getBoxRetinaIsStuckIn());
//            imageViewer.saccadeRetinaToNewPositionAndGetWhatItSees(nextRetinaPosition);
//        }
//
//        // save partialNervousSystemObject object in JSON format
//        String partialNervousSystemObject = this.gson
//                .toJson(this.partialNervousSystem);
//        JsonFileInputOutput
//                .saveObjectToTextFile(partialNervousSystemObject,
//                        "./experiments/model/MARK_II/vision/PartialNervousSystem_MARK_II.json");
//    }
}