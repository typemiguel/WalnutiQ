package model.MARK_II.ConnectTypes;

import model.MARK_II.Column;

import model.MARK_II.Region;
import model.MARK_II.SensorCell;
import model.MARK_II.VisionCell;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK II | July 14th, 2013
 */
public class SensorCellsToRegionRandomConnectTest extends
	junit.framework.TestCase {

    private SensorCellsToRegionRandomConnect connectType;

    public void setUp() {
	this.connectType = new SensorCellsToRegionRandomConnect();
    }

    public void test_connect() {
	Region leafRegion = new Region("leafRegion", 8, 8, 4, 20, 3);
	SensorCell[][] sensorCells = new VisionCell[66][66];
	for (int x = 0; x < sensorCells.length; x++) {
	    for (int y = 0; y < sensorCells[0].length; y++) {
		sensorCells[x][y] = new VisionCell();
	    }
	}

	this.connectType.connect(sensorCells, leafRegion, 2, 2);
	Column[][] columns = leafRegion.getColumns();
	for (int parentColumnX = 0; parentColumnX < leafRegion.getXAxisLength(); parentColumnX++) {
	    for (int parentColumnY = 0; parentColumnY < leafRegion
		    .getYAxisLength(); parentColumnY++) {
		assertEquals(72, columns[parentColumnX][parentColumnY]
			.getProximalSegment().getSynapses().size());
	    }
	}
    }
}
