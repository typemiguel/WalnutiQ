package model.MARK_I;

import model.MARK_I.connectTypes.RegionToRegionConnect;
import model.MARK_I.connectTypes.RegionToRegionRectangleConnect;
import model.MARK_I.connectTypes.SensorCellsToRegionConnect;
import model.MARK_I.connectTypes.SensorCellsToRegionRectangleConnect;

import model.MARK_I.Cell;
import model.MARK_I.Column;
import model.MARK_I.ColumnPosition;
import model.MARK_I.Region;
import model.MARK_I.Segment;
import model.MARK_I.SpatialPooler;
import model.MARK_I.Synapse;
import model.MARK_I.VisionCell;

import java.io.IOException;
import model.Retina;
import model.util.RegionConsoleViewer;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK I | July 29, 2013
 */
public class SpatialPoolerTest extends junit.framework.TestCase {

    private Region parentRegion;
    private SpatialPooler spatialPooler;

    public void setUp() {
	this.parentRegion = new Region("parentRegion", 8, 8, 4, 20, 3);
	Region childRegion = new Region("childRegion", 66, 66, 4, 20, 3);
	RegionToRegionConnect connectType = new RegionToRegionRectangleConnect();
	connectType.connect(childRegion, this.parentRegion, 2, 2);

	this.spatialPooler = new SpatialPooler(this.parentRegion);
	this.spatialPooler.setLearningState(true);
    }

    public void test_performSpatialPoolingOnRegion() throws IOException {
	Region region = new Region("region", 8, 8, 4, 50, 1);
	this.spatialPooler.changeRegion(region);

	VisionCell[][] visionCells = new VisionCell[65][65];
	for (int x = 0; x < visionCells.length; x++) {
	    for (int y = 0; y < visionCells[0].length; y++) {
		visionCells[x][y] = new VisionCell();
	    }
	}

	SensorCellsToRegionConnect connectType2 = new SensorCellsToRegionRectangleConnect();
	connectType2.connect(visionCells, region, 2, 2);

	// update VisionCells states
	Retina retina = new Retina(visionCells);

	retina.seeBMPImage("2.bmp");
	this.spatialPooler.performSpatialPoolingOnRegion();
	Set<ColumnPosition> activeColumnPositions = this.spatialPooler.getActiveColumnPositions();
	for (ColumnPosition columnPosition : activeColumnPositions) {
	    //System.out.println(columnPosition.toString());
	}
	//System.out.println(this.spatialPooler.toString());
	//System.out.println(region.toString());

	this.parentRegion = this.spatialPooler.getRegion();
    }

    public void test_computeColumnOverlapScore() {
	// when # of active Synapses on Column
	// proximal Segment < regionMinimumOverlapScore
	// Column new overlapScore = 0
	Column[][] columns = this.parentRegion.getColumns();
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
		Cell cell = (Cell) inactiveSynapse.getConnectedCell();
		cell.setActiveState(true);
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
	Column[][] columns = this.parentRegion.getColumns();
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

	this.parentRegion.setInhibitionRadius(2);

	this.spatialPooler.computeActiveColumnsOfRegion();

	char[][] columnActiveStates = RegionConsoleViewer
		.getColumnActiveStatesCharArray(this.parentRegion);
	System.out.println("\n--test_computeActiveColumnsOfRegion()--");
	System.out.println(this.parentRegion.toString());
	RegionConsoleViewer.printDoubleCharArray(columnActiveStates);
    }

    public void test_regionLearnOneTimeStep() {
	Column[][] columns = this.parentRegion.getColumns();
	Segment proximalSegment_00 = columns[0][0].getProximalSegment();
	Synapse synapse_00 = proximalSegment_00.getSynapse(0, 0);

	// An "and" truth table of column.updatePermanences()
        // X-Axis: Column activeState
        // Y-Axis: Synapse/InputCell activeState
        // -----true false
        // true .315 .295
        // false.300 .300
	columns[0][0].setActiveState(true);
	Cell cell_00 = (Cell) synapse_00.getConnectedCell();
	cell_00.setActiveState(true);
	assertEquals(0.3f, synapse_00.getPermanenceValue(), 0.001);
	this.spatialPooler.regionLearnOneTimeStep();
	assertEquals(0.315f, synapse_00.getPermanenceValue(), 0.001);

	// now the Synapse permanenceValue is decreased
	columns[0][0].setActiveState(true);
	cell_00.setActiveState(false);
	assertEquals(0.315f, synapse_00.getPermanenceValue(), 0.001);
	this.spatialPooler.regionLearnOneTimeStep();
	assertEquals(0.310f, synapse_00.getPermanenceValue(), 0.001);

	// when Column activeState is false a Synapse's permanenceValue does
	// not change whether the Synapse is active itself or not
	columns[0][0].setActiveState(false);
	cell_00.setActiveState(true);
	assertEquals(0.310f, synapse_00.getPermanenceValue(), 0.001);
	this.spatialPooler.regionLearnOneTimeStep();
	assertEquals(0.310f, synapse_00.getPermanenceValue(), 0.001);

	columns[0][0].setActiveState(false);
	cell_00.setActiveState(false);
	assertEquals(0.310f, synapse_00.getPermanenceValue(), 0.001);
	this.spatialPooler.regionLearnOneTimeStep();
	assertEquals(0.310f, synapse_00.getPermanenceValue(), 0.001);

	// TODO: test the remainder of this SpatialPooler method
    }

    public void test_updateNeighborColumns() {
	// test on Column at position (0, 0) of Region
	this.parentRegion.setInhibitionRadius(0);
	this.spatialPooler.updateNeighborColumns(0, 0);
	Column[][] columns = this.parentRegion.getColumns();
	List<ColumnPosition> neighborColumns1 = columns[0][0].getNeighborColumns();
	assertEquals(0, neighborColumns1.size());

	this.parentRegion.setInhibitionRadius(1);
	this.spatialPooler.updateNeighborColumns(0, 0);
	List<ColumnPosition> neighborColumns2 = columns[0][0].getNeighborColumns();
	assertEquals(3, neighborColumns2.size());

	this.parentRegion.setInhibitionRadius(2);
	this.spatialPooler.updateNeighborColumns(0, 0);
	List<ColumnPosition> neighborColumns3 = columns[0][0].getNeighborColumns();
	assertEquals(8, neighborColumns3.size());

	this.parentRegion.setInhibitionRadius(3);
	this.spatialPooler.updateNeighborColumns(0, 0);
	List<ColumnPosition> neighborColumns4 = columns[0][0].getNeighborColumns();
	assertEquals(15, neighborColumns4.size());

	// test on Column at position (3, 3) of Region
	this.parentRegion.setInhibitionRadius(0);
	this.spatialPooler.updateNeighborColumns(3, 3);
	List<ColumnPosition> neighborColumns5 = columns[3][3].getNeighborColumns();
	assertEquals(0, neighborColumns5.size());

	this.parentRegion.setInhibitionRadius(1);
	this.spatialPooler.updateNeighborColumns(3, 3);
	List<ColumnPosition> neighborColumns6 = columns[3][3].getNeighborColumns();
	assertEquals(8, neighborColumns6.size());

	this.parentRegion.setInhibitionRadius(2);
	this.spatialPooler.updateNeighborColumns(3, 3);
	List<ColumnPosition> neighborColumns7 = columns[3][3].getNeighborColumns();
	assertEquals(24, neighborColumns7.size());

	this.parentRegion.setInhibitionRadius(3);
	this.spatialPooler.updateNeighborColumns(3, 3);
	List<ColumnPosition> neighborColumns8 = columns[3][3].getNeighborColumns();
	assertEquals(48, neighborColumns8.size());
    }

    public void test_kthScoreOfColumns() {
	// change overlapScores of columns for testing
	// the correct returning kth score
	Column[][] columns = this.parentRegion.getColumns();
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
	assertEquals(5.358, this.spatialPooler.averageReceptiveFieldSizeOfRegion(), 0.001);
    }

    public void test_updateOverlapDutyCycle() {
	Column[][] columns = this.parentRegion.getColumns();

	this.spatialPooler.updateOverlapDutyCycle(0, 0);
	assertEquals(0.995f, columns[0][0].getOverlapDutyCycle(), 0.0001);

	columns[0][0].setOverlapDutyCycle(0.5f);
	this.spatialPooler.updateOverlapDutyCycle(0, 0);
	assertEquals(0.4975f, columns[0][0].getOverlapDutyCycle(), 0.0001);
    }
}
