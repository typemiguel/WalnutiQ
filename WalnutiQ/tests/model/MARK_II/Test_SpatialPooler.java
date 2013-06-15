package model.MARK_II;

import model.MARK_II.ConnectTypes.RegionToRegionConnect;

import model.MARK_II.ConnectTypes.RegionToRegionRectangleConnect;

import java.util.List;

import java.util.ArrayList;

public class Test_SpatialPooler extends junit.framework.TestCase {

    private Region region;

    private SpatialPooler spatialPooler;

    public void setUp() {
	this.region = new Region("V1", 8, 8, 4, 20, 3);
	Region childRegion = new Region("LGN", 66, 66, 4, 20, 3);
	RegionToRegionConnect connectType = new RegionToRegionRectangleConnect();
	connectType.connect(childRegion, this.region, 2, 2);

	this.spatialPooler = new SpatialPooler(this.region);
    }

    public void test_performSpatialPoolingOnRegion() {

    }

    public void test_computeColumnOverlapScore() {

    }

    public void test_computeActiveColumnsOfRegion() {

    }

    public void test_regionLearnOneTimeStep() {

    }

    public void test_updateNeighborColumns() {
	// test on Column at position (0, 0) of Region
	this.region.setInhibitionRadius(0);
	this.spatialPooler.updateNeighborColumns(0, 0);
	Column[][] columns = this.region.getColumns();
	List<Column> neighborColumns1 = columns[0][0].getNeighborColumns();
	assertEquals(0, neighborColumns1.size());

	this.region.setInhibitionRadius(1);
	this.spatialPooler.updateNeighborColumns(0, 0);
	List<Column> neighborColumns2 = columns[0][0].getNeighborColumns();
	assertEquals(3, neighborColumns2.size());

	this.region.setInhibitionRadius(2);
	this.spatialPooler.updateNeighborColumns(0, 0);
	List<Column> neighborColumns3 = columns[0][0].getNeighborColumns();
	assertEquals(8, neighborColumns3.size());

	this.region.setInhibitionRadius(3);
	this.spatialPooler.updateNeighborColumns(0, 0);
	List<Column> neighborColumns4 = columns[0][0].getNeighborColumns();
	assertEquals(15, neighborColumns4.size());

	// test on Column at position (3, 3) of Region
	this.region.setInhibitionRadius(0);
	this.spatialPooler.updateNeighborColumns(3, 3);
	List<Column> neighborColumns5 = columns[3][3].getNeighborColumns();
	assertEquals(0, neighborColumns5.size());

	this.region.setInhibitionRadius(1);
	this.spatialPooler.updateNeighborColumns(3, 3);
	List<Column> neighborColumns6 = columns[3][3].getNeighborColumns();
	assertEquals(8, neighborColumns6.size());

	this.region.setInhibitionRadius(2);
	this.spatialPooler.updateNeighborColumns(3, 3);
	List<Column> neighborColumns7 = columns[3][3].getNeighborColumns();
	assertEquals(24, neighborColumns7.size());

	this.region.setInhibitionRadius(3);
	this.spatialPooler.updateNeighborColumns(3, 3);
	List<Column> neighborColumns8 = columns[3][3].getNeighborColumns();
	assertEquals(48, neighborColumns8.size());
    }

    public void test_kthScoreOfColumns() {
	// change overlapScores of columns for testing
	// the correct returning kth score
	Column[][] columns = this.region.getColumns();
	columns[1][3].setOverlapScore(24);
	columns[2][3].setOverlapScore(1);
	columns[2][5].setOverlapScore(26);
	columns[3][1].setOverlapScore(20);
	columns[3][4].setOverlapScore(5);
	columns[3][6].setOverlapScore(30);
	columns[4][3].setOverlapScore(10);

	List<Column> columnsList = new ArrayList<Column>();
	columnsList.add(columns[1][3]);
	columnsList.add(columns[2][3]);
	columnsList.add(columns[2][5]);
	columnsList.add(columns[3][1]);
	columnsList.add(columns[3][4]);
	columnsList.add(columns[3][6]);
	columnsList.add(columns[4][3]);

	assertEquals(30, this.spatialPooler.kthScoreOfColumns(columnsList, 1));
	assertEquals(26, this.spatialPooler.kthScoreOfColumns(columnsList, 2));
	assertEquals(24, this.spatialPooler.kthScoreOfColumns(columnsList, 3));
	assertEquals(20, this.spatialPooler.kthScoreOfColumns(columnsList, 4));
	assertEquals(10, this.spatialPooler.kthScoreOfColumns(columnsList, 5));
	assertEquals(5, this.spatialPooler.kthScoreOfColumns(columnsList, 6));
	assertEquals(1, this.spatialPooler.kthScoreOfColumns(columnsList, 7));
    }

    public void test_averageReceptiveFieldSizeOfRegion() {

    }

    public void test_updateOverlapDutyCycle() {
	Column[][] columns = this.region.getColumns();

	this.spatialPooler.updateOverlapDutyCycle(0, 0);
	assertEquals(0.995f, columns[0][0].getOverlapDutyCycle(), 0.0001);

	columns[0][0].setOverlapDutyCycle(0.5f);
	this.spatialPooler.updateOverlapDutyCycle(0, 0);
	assertEquals(0.4975f, columns[0][0].getOverlapDutyCycle(), 0.0001);
    }

    public void test_toString() {
	System.out.println(this.spatialPooler.toString());
    }
}
