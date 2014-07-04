package model.MARK_II.vision;

import com.google.gson.Gson;
import model.ImageViewer;
import model.MARK_II.ColumnPosition;
import model.MARK_II.Neocortex;
import model.MARK_II.Region;
import model.MARK_II.SpatialPooler;
import model.MARK_II.connectTypes.*;
import model.ParietalLobeRegion;
import model.Retina;
import model.unimplementedBiology.LGN;
import model.unimplementedBiology.NervousSystem;
import model.util.JsonFileInputOutput;
import model.util.Point3D;

import java.io.IOException;
import java.util.Set;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version July 3, 2014
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
     * A, B, C, D, E, F, & G are Region names.
     * C = parietal lobe region
     *
     * A = root region
     *          A
     *      B       C
     *     D E     F G
     *        Retina
     * ImageRetinaIsLookingAt
     */
//    private NervousSystem constructConnectedNervousSystem() {
//        Neocortex neocortex = new Neocortex(new Region("A", 10, 10, 4, 20, 3));
//
//        neocortex.addToCurrentRegion(new Region("B", 25, 25, 4, 20, 3), new RegionToRegionRectangleConnect(), 0, 0);
//        neocortex.addToCurrentRegion(new ParietalLobeRegion(("C", 50, 50, 4, 20, 3), new RegionToRegionRandomConnect(), 2, 3);
//
//        Region D = new Region("D", 144, 144, 4, 20, 3);
//        Region E = new Region("E", 144, 144, 4, 20, 3);
//        neocortex.changeCurrentRegion("B");
//        neocortex.addToCurrentRegion(D, new RegionToRegionRectangleConnect(), 1, 1);
//        neocortex.addToCurrentRegion(E, new RegionToRegionRectangleConnect(), 2, 2);
//
//        Region F = new Region("F", 400, 400, 4, 20, 3);
//        Region G = new Region("G", 400, 400, 4, 20, 3);
//        neocortex.changeCurrentRegion("C");
//        neocortex.addToCurrentRegion(F, new RegionToRegionRectangleConnect(), 1, 1);
//        neocortex.addToCurrentRegion(G, new RegionToRegionRectangleConnect(), 2, 2);
//
//        // NOTE: D, E, F, & G are connected to different parts of the same input SensorCell layer
//
//        Retina retina = new Retina(544, 544);
//
//        // what if you connect retina to Neocortex before passing it through NervousSystem
//        AbstractSensorCellsToRegionConnect opticNerve = new SensorCellsToRegionRectangleConnect();
//        opticNerve.connect(retina.getQuadrantOneVisionCells(), D, 0, 0);
//        opticNerve.connect(retina.getQuadrantTwoVisionCells(), E, 0, 0);
//        opticNerve.connect(retina.getQuadrantThreeVisionCells(), F, 0, 0);
//        opticNerve.connect(retina.getQuadrantFourVisionCells(), G, 0, 0);
//
//        NervousSystem nervousSystem = new NervousSystem(neocortex, null, retina); // no LGN for now
//        // LGN with circle surround sound input
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