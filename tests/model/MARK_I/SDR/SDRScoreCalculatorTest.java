package model.MARK_I.SDR;

import java.io.IOException;

import java.util.Set;
import model.Retina;
import model.MARK_I.ColumnPosition;
import model.MARK_I.Region;
import model.MARK_I.SpatialPooler;
import model.MARK_I.connectTypes.SensorCellsToRegionConnectInterface;
import model.MARK_I.connectTypes.SensorCellsToRegionRectangleConnect;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Apr 16, 2014
 */
public class SDRScoreCalculatorTest extends junit.framework.TestCase {
    private SDRScoreCalculator sdrScoreCalculator;

    public void setUp() throws IOException {
	Retina retina = new Retina(66, 66);
	Region region = new Region("Region", 8, 8, 1, 50, 3);

	SensorCellsToRegionConnectInterface retinaToRegion = new SensorCellsToRegionRectangleConnect();
	retinaToRegion.connect(retina.getVisionCells(), region, 0, 0);

	SpatialPooler spatialPooler = new SpatialPooler(region);
	spatialPooler.setLearningState(true);

	retina.seeBMPImage("2.bmp");

	spatialPooler.performSpatialPoolingOnRegion(); // 11 active columns
	Set<ColumnPosition> columnActivityAfterSeeingImage2 = spatialPooler
		.getActiveColumnPositions();
	// = (6,5)(6, 3)(6, 2)(5, 3)(3, 5)(2, 2)(1, 3)(1, 2)(2, 5)(1, 5)(4, 4)

	// set up SDRScoreCalculator
	int totalNumberOfColumnsInRegion = region.getXAxisLength()
		* region.getYAxisLength();
	this.sdrScoreCalculator = new SDRScoreCalculator(
		columnActivityAfterSeeingImage2, 10.0,
		totalNumberOfColumnsInRegion);
    }

    public void test_computeSparsityScore() {
	assertEquals(1.12, this.sdrScoreCalculator.computeSparsityScore(), 0.01);
    }

    public void test_computeNumberOfActiveColumnsScore() {
	assertEquals(0.2,
		this.sdrScoreCalculator.computeNumberOfActiveColumnsScore());
    }

    public void test_computeSDRScore() {

    }
}
