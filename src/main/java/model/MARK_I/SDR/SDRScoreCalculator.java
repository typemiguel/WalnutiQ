package main.java.model.MARK_I.SDR;

import main.java.model.MARK_I.ColumnPosition;
import java.util.Set;

/**
 * Uses the equation found here https://github.com/quinnliu/WalnutiQ/issues/21
 * for computating a score for the SDR created by spatial pooling.
 *
 * Input from constructor: set of active column positions produced by spatial
 * pooling.
 *
 * Output from calling public method: a score where the larger the score
 * represents a better sparse distributed representation.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Apr 15, 2014
 */
public class SDRScoreCalculator {
    private Set<ColumnPosition> columnActivityAfterSeeingImage;
    private double desiredPercentageOfActiveColumns;
    private int totalNumberOfColumnsInRegion;

    /**
     * Computes the SDR score for the given set of column positions.
     *
     * @param columnActivityAfterSeeingImage
     * @param desiredPercentageOfActiveColumns
     * @param totalNumberOfColumnsInRegion
     */
    public SDRScoreCalculator(
	    Set<ColumnPosition> columnActivityAfterSeeingImage,
	    double desiredPercentageOfActiveColumns,
	    int totalNumberOfColumnsInRegion) {
	this.columnActivityAfterSeeingImage = columnActivityAfterSeeingImage;
	this.desiredPercentageOfActiveColumns = desiredPercentageOfActiveColumns;
	this.totalNumberOfColumnsInRegion = totalNumberOfColumnsInRegion;
    }

    public void updateParameters(
	    Set<ColumnPosition> columnActivityAfterSeeingImage,
	    double desiredPercentageOfActiveColumns,
	    int totalNumberOfColumnsInRegion) {
	this.columnActivityAfterSeeingImage = columnActivityAfterSeeingImage;
	this.desiredPercentageOfActiveColumns = desiredPercentageOfActiveColumns;
	this.totalNumberOfColumnsInRegion = totalNumberOfColumnsInRegion;
    }

    /**
     * Please look here for a visual of the followin sparsityScore formula:
     * https://github.com/quinnliu/WalnutiQ/issues/21
     *
     * @return the sparsity score. The smaller the score the more sparsly
     *         distributed the active columns are.
     */
    public double computeSparsityScore() {

	double totalDistanceToNearestActiveColumnForAllActiveColumns = 0.0;
	// iterate through all column positions
	for (ColumnPosition currentColumnPosition : this.columnActivityAfterSeeingImage) {
	    int x1 = currentColumnPosition.getX();
	    int y1 = currentColumnPosition.getY();

	    Set<ColumnPosition> columnActivity = this.columnActivityAfterSeeingImage;

	    // find nearest active column distance(1 million is a random large
	    // number)
	    double distanceToNearestActiveColumn = 1000000;
	    for (ColumnPosition otherColumnPosition : columnActivity) {
		int x2 = otherColumnPosition.getX();
		int y2 = otherColumnPosition.getY();

		// if you find the same columnPosition as (x1, y1) skip
		if (x1 == x2 && y1 == y2) {
		    continue;
		}

		double distanceBetween_x1y1_x2y2 = Math.sqrt(Math.pow(x1 - x2,
			2) + Math.pow(y1 - y2, 2));
		if (distanceBetween_x1y1_x2y2 < distanceToNearestActiveColumn) {
		    distanceToNearestActiveColumn = distanceBetween_x1y1_x2y2;
		}
	    }

	    totalDistanceToNearestActiveColumnForAllActiveColumns += distanceToNearestActiveColumn;
	}

	double averageDistanceToNearestActiveColumn = totalDistanceToNearestActiveColumnForAllActiveColumns
		/ columnActivityAfterSeeingImage.size();

	// the best averageDistanceToNearestActiveColumn
	return -averageDistanceToNearestActiveColumn;
    }

    /**
     * @return the number of active columns score. The smaller the score the
     *         closer the numberOfActiveColumns was to
     *         desiredNumberOfActiveColumns.
     */
    public double computeNumberOfActiveColumnsScore() {
	int desiredNumberOfActiveColumns = (int) (this.totalNumberOfColumnsInRegion
		* this.desiredPercentageOfActiveColumns / 100);

	int actualNumberOfActiveColumns = this.columnActivityAfterSeeingImage
		.size();
	double difference = Math.abs(desiredNumberOfActiveColumns
		- actualNumberOfActiveColumns);


	if (difference < 0.1) {
	    difference = 0.1; // 1 / 0.1 = 10 which is a great score
			       // as it should be since desired was = to actual
	}

	return -(1 / difference);
    }

    /**
     * @return SDR score computed from given set of active column positions.
     */
    public double computeSDRScore() {
	return this.computeSparsityScore()
		+ this.computeNumberOfActiveColumnsScore();
    }
}
