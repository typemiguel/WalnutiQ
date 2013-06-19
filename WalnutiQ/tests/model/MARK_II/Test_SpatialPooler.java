package model.MARK_II;

import model.util.RegionConsoleViewer;

import java.util.Set;

import model.MARK_II.ConnectTypes.RegionToRegionConnect;

import model.MARK_II.ConnectTypes.RegionToRegionRectangleConnect;

import java.util.List;

import java.util.ArrayList;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK II | June 19, 2013
 */
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
	// when # of active Synapses on Column
	// proximal Segment < regionMinimumOverlapScore
	// Column new overlapScore = 0
	Column[][] columns = this.region.getColumns();
	assertEquals(0, columns[0][0].getProximalSegment()
		.getNumberOfActiveSynapses());
	assertEquals(100, columns[0][0].getProximalSegment().getSynapses()
		.size());

	columns[0][0].setOverlapScore(69);
	assertEquals(69, columns[0][0].getOverlapScore());
	this.spatialPooler.computeColumnOverlapScore(columns[0][0]);
	assertEquals(0, columns[0][0].getOverlapScore());

	// when # of active Synapses on Column
	// proximal Segment > regionMinimumOverlapScore
	// Column new overlapScore = # of active Synapses * Column boostValue

	// the current regionMinimumOverlapScore is 20 active Synapses out of
	// 100
	// total Synapses

	// to test if method is working correctly add 21 synapses
	Set<Synapse<Cell>> oneHundredInactiveSynapses = columns[0][0]
		.getProximalSegment().getSynapses();
	int i = 0;
	for (Synapse inactiveSynapse : oneHundredInactiveSynapses) {
	    if (i < 21) {
		inactiveSynapse.getCell().setActiveState(true);
		i++;
	    } else {
		break;
	    }
	}
	// now the overlapScore will be 21
	columns[0][0].setBoostValue(1.5f); // now since the boostValue is 1.5

	// the new overlapScore will be 21 * 1.5 ~= 31
	this.spatialPooler.computeColumnOverlapScore(columns[0][0]);

	assertEquals(31, columns[0][0].getOverlapScore());
    }

    public void test_computeActiveColumnsOfRegion() {
	Column[][] columns = this.region.getColumns();
	columns[0][0].setOverlapScore(10);
	columns[0][1].setOverlapScore(11);
	columns[0][2].setOverlapScore(12);
	columns[0][3].setOverlapScore(13);
	columns[0][4].setOverlapScore(14);
	columns[1][0].setOverlapScore(15);
	columns[1][1].setOverlapScore(2);
	columns[1][2].setOverlapScore(3);
	columns[1][3].setOverlapScore(4);
	columns[1][4].setOverlapScore(16);
	columns[2][0].setOverlapScore(17);
	columns[2][1].setOverlapScore(5);
	columns[2][2].setOverlapScore(1);
	columns[2][3].setOverlapScore(6);
	columns[2][4].setOverlapScore(18);
	columns[3][0].setOverlapScore(19);
	columns[3][1].setOverlapScore(7);
	columns[3][2].setOverlapScore(8);
	columns[3][3].setOverlapScore(9);
	columns[3][4].setOverlapScore(20);
	columns[4][0].setOverlapScore(21);
	columns[4][1].setOverlapScore(22);
	columns[4][2].setOverlapScore(23);
	columns[4][3].setOverlapScore(24);
	columns[4][4].setOverlapScore(25);
	// all other Column overlapScores are 0

	this.region.setInhibitionRadius(2);

	this.spatialPooler.computeActiveColumnsOfRegion();

	char[][] columnActiveStates = RegionConsoleViewer
		.getColumnActiveStatesCharArray(this.region);
	//System.out.println("\n--test_computeActiveColumnsOfRegion()--");
	//RegionConsoleViewer.printDoubleCharArray(columnActiveStates);

    }

    public void test_regionLearnOneTimeStep() {
	Column[][] columns = this.region.getColumns();
	Segment proximalSegment_00 = columns[0][0].getProximalSegment();
	Synapse synapse_00 = proximalSegment_00.getSynapse(0, 0);

	// An "and" truth table of column.updatePermanences()
        // X-Axis: Column activeState
        // Y-Axis: Synapse/InputCell activeState
        // -----true false
        // true .315 .295
        // false.300 .300
	columns[0][0].setActiveState(true);
	synapse_00.getCell().setActiveState(true);
	assertEquals(0.3f, synapse_00.getPermanenceValue(), 0.001);
	this.spatialPooler.regionLearnOneTimeStep();
	assertEquals(0.315f, synapse_00.getPermanenceValue(), 0.001);

	// now the Synapse permanenceValue is decreased
	columns[0][0].setActiveState(true);
	synapse_00.getCell().setActiveState(false);
	assertEquals(0.315f, synapse_00.getPermanenceValue(), 0.001);
	this.spatialPooler.regionLearnOneTimeStep();
	assertEquals(0.310f, synapse_00.getPermanenceValue(), 0.001);

	// when Column activeState is false a Synapse's permanenceValue does
	// not change whether the Synapse is active itself or not
	columns[0][0].setActiveState(false);
	synapse_00.getCell().setActiveState(true);
	assertEquals(0.310f, synapse_00.getPermanenceValue(), 0.001);
	this.spatialPooler.regionLearnOneTimeStep();
	assertEquals(0.310f, synapse_00.getPermanenceValue(), 0.001);

	columns[0][0].setActiveState(false);
	synapse_00.getCell().setActiveState(false);
	assertEquals(0.310f, synapse_00.getPermanenceValue(), 0.001);
	this.spatialPooler.regionLearnOneTimeStep();
	assertEquals(0.310f, synapse_00.getPermanenceValue(), 0.001);

	// TODO: test the remainder of this SpatialPooler method
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
	// TODO: what will averageReceptiveFieldSize be used for?
	assertEquals(44.211, this.spatialPooler.averageReceptiveFieldSizeOfRegion(), 0.001);
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
	// System.out.println(this.spatialPooler.toString());
    }
}
