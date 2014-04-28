package model.MARK_I.parameters;

import java.awt.Point;
import model.MARK_II.movement.SaccadingRetina;
import model.MARK_II.movement.ImageViewer;

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

	return SPandTPscore;
    }
}
