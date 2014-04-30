package model.MARK_II.parameters;

import model.ImageViewer;

import model.SaccadingRetina;
import java.awt.Point;

public class FindOptimalParametersForSPandTP {

    public static double printToFileSDRScoreFor1RetinaTo1RegionModelFor1Digit() {
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
	// 3) the less newSynapses there are

	return SPandTPscore;
    }
}
