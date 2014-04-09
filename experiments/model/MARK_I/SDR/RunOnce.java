package model.MARK_I.SDR;

import java.io.File;

import java.io.FileWriter;

import java.io.BufferedWriter;

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
public class RunOnce {
    /**
     * optimization algorithm = QNSTOP
     *
     * a,b,c is list of parameters size of retina & region are initially
     * constant for this experiment
     *
     * view for diagrams about solving this problem @
     * https://github.com/quinnliu/WalnutiQ/issues/21
     */
    public static void getSpatialPoolingScoreWithGivenParametersForMarkNullaModel(
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

	for (ColumnPosition columnPosition : columnActivityAfterSeeingImage2) {
	    sparsityScore += Math.pow(columnPosition.getX() - xAverage, 2)
		    + Math.pow(columnPosition.getY() - yAverage, 2);
	}

	sparsityScore = Math.sqrt(sparsityScore);

	// TODO: add calculations for double numberOfActiveColumnsScore
	double numberOfActiveColumnsScore = 0;
	int desiredNumberOfActiveColumns = 10; // TODO: add computation later
	int actualNumberOfActiveColumns = columnActivityAfterSeeingImage2
		.size();
	double difference = Math.abs(desiredNumberOfActiveColumns
		- actualNumberOfActiveColumns);
	if (difference == 0) {
	    difference += 0.1; // 1 / 0.1 = 10 which is a great score
			       // as it should be since desired was = to actual
	}

	numberOfActiveColumnsScore = 1 / difference;

	double sparseDistributedRepresentationScore = sparsityScore
		+ numberOfActiveColumnsScore;

	try {
	    BufferedWriter out = new BufferedWriter(new FileWriter(
		    "./experiments/model/MARK_I/SDR/currentSDRScore.txt"));
	    out.write(Double.toString(sparseDistributedRepresentationScore));
	    out.close();
	} catch (IOException e) {
	}

//	BufferedWriter bw = null;
//	try {
//	    bw = new BufferedWriter(new FileWriter(new File("./experiments/model/MARK_I/SDR/currentSDRScore.txt")));
//	    bw.write(Double.toString(sparseDistributedRepresentationScore));
//	} finally {
//	    try {
//		bw.close();
//	    } catch (Exception e) {
//
//	    }
//	}
    }
}
