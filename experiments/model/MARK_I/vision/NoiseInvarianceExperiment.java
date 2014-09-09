package model.MARK_I.vision;

import junit.framework.TestCase;
import model.MARK_II.ColumnPosition;
import model.MARK_II.Region;
import model.MARK_II.SpatialPooler;
import model.MARK_II.connectTypes.AbstractSensorCellsToRegionConnect;
import model.MARK_II.connectTypes.SensorCellsToRegionRectangleConnect;
import model.Retina;
import model.util.Formatter;

import java.io.IOException;
import java.util.Set;
import java.util.HashSet;

/**
 * -------------------------------Purpose---------------------------------------
 * To show the spatial pooling learning algorithm is good at producing the same
 * output of neural activity even when the input is very noisy.
 *
 * ------------------------------Experiment-------------------------------------
 * Run the spatial pooling algorithm on 3 different bitmap images. The 3 images
 * are both of the same thing but 1 of the images has no noise, 1 image has some
 * noise, and 1 image has a lot of noise.
 *
 * ------------------------------Conclusion-------------------------------------
 * The spatial pooling algorithm does simple local computations on it's input to
 * remove noise very efficiently up to a specific threshold that can vary
 * between locations in the input.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version August 1, 2014
 */
public class NoiseInvarianceExperiment extends TestCase {
    private Retina retina;
    private Region region;
    private SpatialPooler spatialPooler;

    public void setUp() {
        // images this oldRetina will see are all 66x66 pixels
        this.retina = new Retina(66, 66);

        this.region = new Region("Region name", 8, 8, 1, 77.8, 1);
        this.region.setInhibitionRadius(3);

        AbstractSensorCellsToRegionConnect retinaToRegion = new SensorCellsToRegionRectangleConnect();
        retinaToRegion.connect(this.retina.getVisionCells(), this.region, 0, 0);

        this.spatialPooler = new SpatialPooler(this.region);
        this.spatialPooler.setLearningState(true);
    }

    public void test_NoiseInvarianceExperiment() throws IOException {

        // create columns that should be in the set
        ColumnPosition cp1 = new ColumnPosition(6, 2);
        ColumnPosition cp2 = new ColumnPosition(1, 5);
        ColumnPosition cp3 = new ColumnPosition(2, 5);

        // create a set to test against the first two images
        Set<ColumnPosition> set1 = new HashSet<ColumnPosition>();
        set1.add(cp1);
        set1.add(cp2);

        // set to use for the final image
        Set<ColumnPosition> set2 = new HashSet<ColumnPosition>();
        set2.add(cp1);
        set2.add(cp3);

        // View all three images of digit 2 @ https://db.tt/ElvG0WLM
        // fix this test
        this.retina.seeBMPImage("2.bmp");
        this.spatialPooler.performPooling();

        // set1 = ((6,2), (1,5))
        assertEquals(set1, this.spatialPooler.getActiveColumnPositions());

        this.retina.seeBMPImage("2_with_some_noise.bmp");
        this.spatialPooler.performPooling();

        // set1 = ((6,2), (1,5))
        assertEquals(set1, this.spatialPooler.getActiveColumnPositions());

        this.retina.seeBMPImage("2_with_a_lot_of_noise.bmp");
        this.spatialPooler.performPooling();
        // when there is a lot of noise notice how the active columns are no longer the same?

        // set2 = ((6,2), (2,5))
        assertEquals(set2, this.spatialPooler.getActiveColumnPositions());
    }
}
