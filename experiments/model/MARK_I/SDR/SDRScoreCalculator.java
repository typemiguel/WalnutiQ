package model.MARK_I.SDR;

import java.util.Set;
import model.MARK_I.ColumnPosition;

/**
 * Write a one-sentence summary of your class here. Follow it with additional
 * details about its purpose, what abstraction it represents, and how to use it.
 *
 * view for diagrams about solving this problem @
 * https://github.com/quinnliu/WalnutiQ/issues/21
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Apr 10, 2014
 */
public class SDRScoreCalculator {
    private double xAverage;
    private double yAverage;

    private double sparsityScore;
    private double numberOfActiveColumnsScore;

    private double sparseDistributedRepresentationScore;

    /**
     * Create a new SDRScoreCalculator object.
     * @param columnActivityAfterSeeingImage
     */
    public SDRScoreCalculator(Set<ColumnPosition> columnActivityAfterSeeingImage) {
	this.computeXYAverages(columnActivityAfterSeeingImage);

	this.sparsityScore = 0;
	for (ColumnPosition columnPosition : columnActivityAfterSeeingImage) {
	    this.sparsityScore += Math.pow(columnPosition.getX()
		    - this.xAverage, 2)
		    + Math.pow(columnPosition.getY() - this.yAverage, 2);
	}

	this.sparsityScore = Math.sqrt(this.sparsityScore);

	this.numberOfActiveColumnsScore = 0;
	int desiredNumberOfActiveColumns = 10; // TODO: add computation later
	int actualNumberOfActiveColumns = columnActivityAfterSeeingImage.size();
	double difference = Math.abs(desiredNumberOfActiveColumns
		- actualNumberOfActiveColumns);
	if (difference < 0.1 && difference > -0.1) {
	    difference += 0.1; // 1 / 0.1 = 10 which is a great score
			       // as it should be since desired was = to actual
	}

	this.numberOfActiveColumnsScore = 1 / difference;

	this.sparseDistributedRepresentationScore = sparsityScore
		+ numberOfActiveColumnsScore;
    }

    private void computeXYAverages(
	    Set<ColumnPosition> columnActivityAfterSeeingImage) {
	this.xAverage = 0;
	this.yAverage = 0;

	for (ColumnPosition columnPosition : columnActivityAfterSeeingImage) {
	    // find xAverage & yAverage
	    this.xAverage += columnPosition.getX();
	    this.yAverage += columnPosition.getY();
	}
	this.xAverage = this.xAverage / columnActivityAfterSeeingImage.size();
	this.yAverage = this.yAverage / columnActivityAfterSeeingImage.size();
    }

    public double getSDRScore() {
	return this.sparseDistributedRepresentationScore;
    }
}
