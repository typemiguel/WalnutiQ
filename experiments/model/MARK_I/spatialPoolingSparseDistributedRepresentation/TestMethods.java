package model.MARK_I.spatialPoolingSparseDistributedRepresentation;

import java.util.Set;
import model.MARK_I.ColumnPosition;

import java.io.IOException;

import model.MARK_I.SpatialPooler;
import model.MARK_I.connectTypes.SensorCellsToRegionConnect;
import model.MARK_I.connectTypes.SensorCellsToRegionRectangleConnect;

import model.MARK_I.Region;

import model.Retina;

/**
 * Contains methods for running a simple MARK NULLA model with given parameters
 * & returning a score based on how well spatial pooling created a sparse
 * distributed representation.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Apr 9, 2014
 */
public class TestMethods {
    /**
     * optimization algorithm = QNSTOP
     *
     * a,b,c is list of parameters size of retina & region are initially
     * constant for this experiment
     */
    public void getSpatialPoolingScoreWithGivenParametersForMarkNullaModel(
	    double percentMinimumOverlapScore, double desiredLocalActivity)
	    throws IOException {

	Retina retina = new Retina(66, 66);
	Region region = new Region("Region", 8, 8, 1,
		percentMinimumOverlapScore, (int) desiredLocalActivity);

	SensorCellsToRegionConnect retinaToRegion = new SensorCellsToRegionRectangleConnect();
	retinaToRegion.connect(retina.getVisionCells(), region, 0, 0);

	SpatialPooler spatialPooler = new SpatialPooler(region);
	spatialPooler.setLearningState(true);

	retina.seeBMPImage("2.bmp");

	spatialPooler.performSpatialPoolingOnRegion(); // 11 active columns
	Set<ColumnPosition> columnActivityAfterSeeingImage2 = spatialPooler
		.getActiveColumnPositions();
	// = (6,5)(6, 3)(6, 2)(5, 3)(3, 5)(2, 2)(1, 3)(1, 2)(2, 5)(1, 5)(4, 4)

	int xAverage = 0;
	int yAverage = 0;

	for (ColumnPosition columnPosition : columnActivityAfterSeeingImage2) {
	    // find xAverage & yAverage
	    xAverage += columnPosition.getX();
	    yAverage += columnPosition.getY();
	}
	xAverage = xAverage / columnActivityAfterSeeingImage2.size();
	yAverage = yAverage / columnActivityAfterSeeingImage2.size();

	double sparsityScore = 0;
	double numberOfActiveColumnsScore = 0;

	for (ColumnPosition columnPosition : columnActivityAfterSeeingImage2) {
	    sparsityScore += Math.pow(columnPosition.getX() - xAverage, 2)
		    + Math.pow(columnPosition.getY() - yAverage, 2);
	}

	sparsityScore = Math.sqrt(sparsityScore);

	// TODO: add calculations for double numberOfActiveColumnsScore

	double sparseDistributedRepresentationScore = sparsityScore
		+ numberOfActiveColumnsScore;

	// TODO: print sparseDistributedRepresentationScore to file
    }
}
