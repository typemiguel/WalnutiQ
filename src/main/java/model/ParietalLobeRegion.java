package model;

import model.MARK_II.Region;
import model.util.BoundingBox;
import model.util.Point3D;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version June 30, 2014
 */
public class ParietalLobeRegion extends Region {

    public ParietalLobeRegion(String biologicalName, int numberOfColumnsAlongRowsDimension,
                              int numberOfColumnsAlongColumnsDimension, int cellsPerColumn,
                              double percentMinimumOverlapScore, int desiredLocalActivity) {
        super(biologicalName, numberOfColumnsAlongRowsDimension, numberOfColumnsAlongColumnsDimension, cellsPerColumn,
                percentMinimumOverlapScore, desiredLocalActivity);
    }

    public Point3D getMotorOutput(BoundingBox boundingBox) {
        return new Point3D(1, 2, 3); // TODO: implement for Retina in ImageViewer
    }

}
