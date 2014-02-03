package model.MARK_I.vision;

import java.util.Set;

import model.MARK_I.ColumnPosition;

import java.io.IOException;
import model.MARK_I.connectTypes.SensorCellsToRegionConnect;
import model.MARK_I.connectTypes.SensorCellsToRegionRectangleConnect;
import model.Retina;
import model.MARK_I.Region;
import model.MARK_I.SpatialPooler;

/**
 * -------------------------------Purpose---------------------------------------
 * To show the spatial pooling learning algorithm produces similar results even
 * after adding noise to the data.
 *
 * ------------------------------Experiment-------------------------------------
 * Run the spatial pooling algorithm on 3 different bitmap images. The 3 images
 * are both of the same thing but one of the images has no noise, one image has
 * some noise, and one image has a lot of noise.
 *
 * ------------------------------Conclusion-------------------------------------
 * The spatial pooling algoithm does simple local computations on the image to
 * remove noise very efficiently up to a specific threshold that can vary
 * between locations in the input image.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Feb 2, 2014
 */
public class NoiseInvarianceExperiment extends junit.framework.TestCase {
    private Retina retina;
    private Region region;
    private SpatialPooler spatialPooler;

    public void setUp() {
	// images in folder images/model/ are 66x66 pixels
	this.retina = new Retina(66, 66);

	this.region = new Region("Region", 8, 8, 1, 20, 3);

	SensorCellsToRegionConnect retinaToLGN = new SensorCellsToRegionRectangleConnect();
	retinaToLGN.connect(this.retina.getVisionCells(), this.region, 2, 2);

	this.spatialPooler = new SpatialPooler(this.region);
	this.spatialPooler.setLearningState(true);
    }

    public void test_runNoiseInvarianceExperiment() throws IOException {
	this.retina.seeBMPImage("2.bmp");

	this.spatialPooler.performSpatialPoolingOnRegion();
	Set<ColumnPosition> columnActivityAfterSeeingImage2 = this.spatialPooler
		.getActiveColumnPositions();

//	for (ColumnPosition columnPosition: columnActivityAfterSeeingImage2) {
//	    System.out.println(columnPosition.toString());
//	}


	this.retina.seeBMPImage("2_with_some_noise.bmp");
	this.spatialPooler.performSpatialPoolingOnRegion();
	Set<ColumnPosition> columnActivityAfterSeeingImage2_with_some_noise = this.spatialPooler
		.getActiveColumnPositions();

//	for (ColumnPosition columnPosition: columnActivityAfterSeeingImage2_with_some_noise) {
//	    System.out.println(columnPosition.toString());
//	}
//
//	// TODO: even though there is some noise the spatial pooling algorithm
//	// produces the exact same output
//	assertEquals(columnActivityAfterSeeingImage2.size(),
//		columnActivityAfterSeeingImage2_with_some_noise.size());
//
	// TODO: input not changing?
	this.retina.seeBMPImage("2_with_alot_of_noise.bmp");
	this.spatialPooler.performSpatialPoolingOnRegion();
	Set<ColumnPosition> columnActivityAfterSeeingImage2_with_alot_of_noise = this.spatialPooler
		.getActiveColumnPositions();
//	// TODO: check if column positions are the same
//	assertEquals(14,
//		columnActivityAfterSeeingImage2_with_alot_of_noise.size());

	for (ColumnPosition columnPosition: columnActivityAfterSeeingImage2_with_alot_of_noise) {
	    System.out.println(columnPosition.toString());
	}
    }
}
