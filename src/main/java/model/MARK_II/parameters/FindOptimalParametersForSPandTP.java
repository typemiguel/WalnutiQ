package model.MARK_II.parameters;

import model.MARK_II.TemporalPooler;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import model.Retina;
import model.MARK_II.Region;
import model.MARK_II.SpatialPooler;
import model.MARK_II.connectTypes.AbstractSensorCellsToRegionConnect;
import model.MARK_II.connectTypes.SensorCellsToRegionRectangleConnect;
import model.ImageViewer;
import model.SaccadingRetina;
import java.awt.Point;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Apr 30, 2014
 */
public class FindOptimalParametersForSPandTP {

    public static double printToFileSPandTPScoreFor1RetinaTo1RegionModelFor1Digit(
	    double percentMinimumOverlapScore, double desiredLocalActivity,
	    double desiredPercentageOfActiveColumns, double newSynapseCount,
	    double numberOfIterations,
	    String locationOfFileWithFileNameToSaveScore) throws IOException {

	Retina retina = new Retina(66, 66);
	Region region = new Region("Region", 8, 8, 4,
		percentMinimumOverlapScore, (int) desiredLocalActivity);

	AbstractSensorCellsToRegionConnect retinaToRegion = new SensorCellsToRegionRectangleConnect();
	retinaToRegion.connect(retina.getVisionCells(), region, 0, 0);

	SpatialPooler spatialPooler = new SpatialPooler(region);
	spatialPooler.setLearningState(true);
	TemporalPooler temporalPooler = new TemporalPooler(spatialPooler,
		(int) newSynapseCount);
	temporalPooler.setLearningState(true);

	retina.seeBMPImage("2.bmp");

	int totalNumberOfSequenceSegments = 0;
	int totalNumberOfLearningNeurons = 0;
	for (int i = 0; i < (int) numberOfIterations; i++) {
	    spatialPooler.performSpatialPoolingOnRegion();
	    temporalPooler.performTemporalPoolingOnRegion();

	    temporalPooler.updateModelLearningMetrics();
	    totalNumberOfSequenceSegments += temporalPooler
		    .getTotalNumberOfSequenceSegmentsInCurrentTimeStep();
	    totalNumberOfLearningNeurons += temporalPooler
		    .getNumberOfCurrentLearningNeurons();
	    temporalPooler.nextTimeStep();
	}

	// --------------------compute SPandTP score----------------------------

	double SPandTPScore = -(totalNumberOfSequenceSegments + totalNumberOfLearningNeurons)
		/ numberOfIterations;

	NumberFormat formatter = new DecimalFormat("0.################E0");

	// print SPandTPScore to file
	try {
	    BufferedWriter out2 = new BufferedWriter(new FileWriter(
		    locationOfFileWithFileNameToSaveScore));
	    out2.write(formatter.format(SPandTPScore));
	    out2.close();
	} catch (IOException e) {

	}

	return SPandTPScore;
    }

    public static double printToFileSPandTPScoreFor1RetinaTo9RegionModelFor5Digits() {
	double SPandTPscore = 0.0;

	// construct model
	SaccadingRetina retina = null;
	ImageViewer imageViewer = null;
	// TODO:

	// spatialPooler.performSpatialPooling();
	// temporalPooler.performSpatialPooling();

	// exact shift in current retina position and zoom level from
	// region representing parietal lobe

	retina.setDistanceBetweenImageAndRetina(1);
	retina.setRetinaPosition(new Point(2, 2));

	imageViewer.updateRetinaWithSeenPartOfImageBasedOnCurrentPosition();

	// spatialPooler.performSpatialPooling();
	// temporalPooler.performSpatialPooling();
	// ... again

	// show image 0
	// call spatial & temporal pooling

	// show image 1
	// call spatial & temporal pooling

	// show image 2
	// call spatial & temporal pooling

	// show image 3
	// call spatial & temporal pooling

	// show image 4
	// call spatial & temporal pooling

	// show image 5
	// call spatial & temporal pooling

	// good SPandTPScore when showing same 0-6 images over and over again
	// 1) the more sequence segments there are
	// 2) the more predicting neurons there are
	// 3) the less newSynapses there are NOT a good metric as it is a
	// input parameter

	return SPandTPscore;
    }
}
