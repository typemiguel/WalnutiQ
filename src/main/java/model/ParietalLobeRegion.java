package model;

import model.MARK_II.Region;

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
}
