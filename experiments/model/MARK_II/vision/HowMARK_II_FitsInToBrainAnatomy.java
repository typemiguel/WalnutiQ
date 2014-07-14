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
import model.util.Rectangle;

import java.awt.*;
import java.awt.geom.Point2D;
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
     * The following is a BIRDS EYE VIEW of a connected partial nervous system with a 3D drawing here:
     * https://github.com/WalnutiQ/WalnutiQ/issues/107
     *
     * LEGEND:
     * root, A, B, C, ... Z are Region names
     * M = parietal lobe region meaning it's neuron activity directly causes the Retina to move to it's new
     * position within the box Retina is stuck in.
     *
     *             root (runs just temporal pooling algorithm)
     *           /      \
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
     */
    private NervousSystem constructConnectedNervousSystem() {
        final int LAYER_4_CELLS_PER_COLUMN = 4;
        final int LAYER_3_CELLS_PER_COLUMN = 1;
        double percentMinimumOverlap = 20;
        int desiredLocalActivity = 3;

        Neocortex neocortex = new Neocortex(new Region("root", 60, 60, LAYER_4_CELLS_PER_COLUMN, percentMinimumOverlap, desiredLocalActivity), new RegionToRegionRectangleConnect());
        neocortex.addToCurrentRegion(new Rectangle(new Point(0, 0), new Point(29, 60)), new Region("A", 125, 125, LAYER_4_CELLS_PER_COLUMN, percentMinimumOverlap, desiredLocalActivity), 0, 0);
        neocortex.addToCurrentRegion(new Rectangle(new Point(30, 0), new Point(60, 60)), new Region("B", 125, 125, LAYER_4_CELLS_PER_COLUMN, percentMinimumOverlap, desiredLocalActivity), 0, 0);

        neocortex.changeCurrentRegionTo("A");
        neocortex.addToCurrentRegion(new Rectangle(new Point(0, 0), new Point(125, 125)), new Region("C", 125, 125, LAYER_3_CELLS_PER_COLUMN, percentMinimumOverlap, desiredLocalActivity), 0, 0);

        neocortex.changeCurrentRegionTo("B");
        neocortex.addToCurrentRegion(new Rectangle(new Point(0, 0), new Point(125, 125)), new Region("D", 125, 125, LAYER_3_CELLS_PER_COLUMN, percentMinimumOverlap, desiredLocalActivity), 0, 0);

        neocortex.changeCurrentRegionTo("C");
        neocortex.addToCurrentRegion(new Rectangle(new Point(0, 0), new Point(62, 125)), new Region("E", 125, 125, LAYER_4_CELLS_PER_COLUMN, percentMinimumOverlap, desiredLocalActivity), 0, 0);
        neocortex.addToCurrentRegion(new Rectangle(new Point(63, 0), new Point(125, 125)), new Region("F", 125, 125, LAYER_4_CELLS_PER_COLUMN, percentMinimumOverlap, desiredLocalActivity), 0, 0);

        neocortex.changeCurrentRegionTo("D");
        neocortex.addToCurrentRegion(new Rectangle(new Point(0, 0), new Point(62, 125)), new Region("G", 125, 125, LAYER_4_CELLS_PER_COLUMN, percentMinimumOverlap, desiredLocalActivity), 0, 0);
        neocortex.addToCurrentRegion(new Rectangle(new Point(63, 0), new Point(125, 125)), new Region("H", 125, 125, LAYER_4_CELLS_PER_COLUMN, percentMinimumOverlap, desiredLocalActivity), 0, 0);

        Region I = new Region("I", 250, 250, LAYER_3_CELLS_PER_COLUMN, percentMinimumOverlap, desiredLocalActivity);
        Layer5Region M = new Layer5Region("M", 250, 250, LAYER_4_CELLS_PER_COLUMN, percentMinimumOverlap, desiredLocalActivity);
        neocortex.changeCurrentRegionTo("E");
        neocortex.addToCurrentRegion(new Rectangle(new Point(0, 0), new Point(125, 125)), I, 0, 0);
        neocortex.addToCurrentRegion(new Rectangle(new Point(0, 0), new Point(125, 125)), M, 0, 0);

        Region J = new Region("J", 250, 250, LAYER_3_CELLS_PER_COLUMN, percentMinimumOverlap, desiredLocalActivity);
        neocortex.changeCurrentRegionTo("F");
        neocortex.addToCurrentRegion(new Rectangle(new Point(0, 0), new Point(125, 125)), J, 0, 0);

        Region K = new Region("K", 250, 250, LAYER_3_CELLS_PER_COLUMN, percentMinimumOverlap, desiredLocalActivity);
        neocortex.changeCurrentRegionTo("G");
        neocortex.addToCurrentRegion(new Rectangle(new Point(0, 0), new Point(125, 125)), K, 0, 0);

        Region L = new Region("L", 250, 250, LAYER_3_CELLS_PER_COLUMN, percentMinimumOverlap, desiredLocalActivity);
        neocortex.changeCurrentRegionTo("H");
        neocortex.addToCurrentRegion(new Rectangle(new Point(0, 0), new Point(125, 125)), L, 0, 0);

        // NOTE: I, J, K, & L are connected to different parts of the same Retina
        Retina retina = new Retina(1000, 1000);

        AbstractSensorCellsToRegionConnect opticNerve = new SensorCellsToRegionRectangleConnect();
        opticNerve.connect(retina.getVisionCells(new Rectangle(new Point(0, 0), new Point(500, 500))), I, 0, 0); // .getVisionCells(topLeftPoint, bottomRightPoint)
        opticNerve.connect(retina.getVisionCells(new Rectangle(new Point(0, 500), new Point(500, 1000))), J, 0, 0);
        opticNerve.connect(retina.getVisionCells(new Rectangle(new Point(500, 0), new Point(1000, 500))), K, 0, 0);
        opticNerve.connect(retina.getVisionCells(new Rectangle(new Point(500, 500), new Point(1000, 1000))), L, 0, 0);

        NervousSystem nervousSystem = new NervousSystem(neocortex, null, retina); // no LGN with circle surround input for now

        return nervousSystem;
    }

    public void test_HowToRunSingleLearningAlgorithmOnNervousSystem() throws IOException {
        Neocortex neocortex = this.partialNervousSystem.getCNS().getBrain().getCerebrum().getCerebralCortex().getNeocortex();

        ImageViewer imageViewer = new ImageViewer("imageOfHumanFace1000x1000pixels.bmp",
                this.partialNervousSystem.getPNS().getSNS().getRetina());

        final int NUMBER_OF_TIMES_TO_RUN_LEARNING_ALGORITHM = 1000;
        for (int i = 0; i < NUMBER_OF_TIMES_TO_RUN_LEARNING_ALGORITHM; i++) {

            neocortex.runSingleLearningAlgorithmOneTimeStep();

            Layer5Region layer5Region = (Layer5Region) neocortex.getRegion("M");
            Point3D nextRetinaPosition = layer5Region
                    .getMotorOutput(imageViewer.getBoxRetinaIsStuckIn());
            imageViewer.saccadeRetinaToNewPositionAndGetWhatItSees(nextRetinaPosition);
        }

        // save partialNervousSystemObject object in JSON format
        String partialNervousSystemObject = this.gson
                .toJson(this.partialNervousSystem);
        JsonFileInputOutput
                .saveObjectToTextFile(partialNervousSystemObject,
                        "./experiments/model/MARK_II/vision/PartialNervousSystem_MARK_II.json");
    }
}