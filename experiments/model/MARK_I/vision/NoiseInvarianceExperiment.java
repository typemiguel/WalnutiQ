package model.MARK_I.vision;

import model.MARK_II.Region;
import model.MARK_II.SpatialPooler;

import junit.framework.TestCase;
import model.MARK_I.connectTypes.AbstractSensorCellsToRegionConnect;
import model.MARK_I.connectTypes.SensorCellsToRegionRectangleConnect;
import model.Retina;

import java.io.IOException;

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
 * The spatial pooling algoithm does simple local computations on it's input to
 * remove noise very efficiently up to a specific threshold that can vary
 * between locations in the input.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version April 12, 2014
 */
public class NoiseInvarianceExperiment extends TestCase {
    private Retina retina;
    private Region region;
    private SpatialPooler spatialPooler;

    public void setUp() {
	// images this retina will see are all 66x66 pixels
	this.retina = new Retina(66, 66);

	this.region = new Region("Region", 8, 8, 1, 77.8, 1);

	AbstractSensorCellsToRegionConnect retinaToRegion = new SensorCellsToRegionRectangleConnect();
	retinaToRegion.connect(this.retina.getVisionCells(), this.region, 0, 0);

	this.spatialPooler = new SpatialPooler(this.region);
	this.spatialPooler.setLearningState(true);
    }

    public void test_NoiseInvarianceExperiment() throws IOException {
	// View all three images of digit 2 @ https://db.tt/ElvG0WLM
	// --------------------------"2.bmp"------------------------------------
	this.retina.seeBMPImage("2.bmp");

	this.spatialPooler.performSpatialPoolingOnRegionWithoutInhibitionRadiusUpdate();

	assertEquals("((6, 2), (1, 3), (1, 5), (4, 4))",
		this.spatialPooler.getActiveColumnPositionsAsString());

	// -------------------"2_with_some_noise.bmp"---------------------------
	this.retina.seeBMPImage("2_with_some_noise.bmp");

	this.spatialPooler.performSpatialPoolingOnRegionWithoutInhibitionRadiusUpdate();

	assertEquals("((6, 2), (1, 3), (1, 5), (4, 4))",
		this.spatialPooler.getActiveColumnPositionsAsString());

	// -------------------"2_with_alot_of_noise.bmp"------------------------
	this.retina.seeBMPImage("2_with_alot_of_noise.bmp");

	this.spatialPooler.performSpatialPoolingOnRegionWithoutInhibitionRadiusUpdate();

	assertEquals("((6, 2), (1, 3), (2, 5))",
		this.spatialPooler.getActiveColumnPositionsAsString());
    }
}
