package model.MARK_I.SDR;

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
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Apr 10, 2014
 */
public class FindOptimalParametersForSDR {

    /**
     * Contains methods for running a simple MARK NULLA model with given
     * parameters & returning a score based on how well spatial pooling created
     * a sparse distributed representation.
     *
     * optimization algorithm = QNSTOP
     *
     * a,b,c is list of parameters size of retina & region are initially
     * constant for this experiment
     *
     * @param percentMinimumOverlapScore
     * @param desiredLocalActivity
     * @param locationOfFileWithFileNameToSaveScore
     *
     * @throws IOException
     */
    public static void printToFileSDRScoreFor1RetinaTo1RegionModel(
	    double percentMinimumOverlapScore, double desiredLocalActivity,
	    String locationOfFileWithFileNameToSaveScore) throws IOException {
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

	SDRScoreCalculator sdrScoreCalculator = new SDRScoreCalculator(
		columnActivityAfterSeeingImage2);

	double SDRScore = sdrScoreCalculator.getSDRScore();

	try {
	    BufferedWriter out2 = new BufferedWriter(new FileWriter(
		    locationOfFileWithFileNameToSaveScore));
	    out2.write(Double.toString(SDRScore));
	    out2.close();
	} catch (IOException e) {

	}
    }
}
