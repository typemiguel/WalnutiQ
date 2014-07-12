package model.MARK_II.connectTypes;

import junit.framework.TestCase;
import model.MARK_II.Column;
import model.MARK_II.Region;
import model.MARK_II.SensorCell;
import model.MARK_II.VisionCell;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version June 13, 2013
 */
public class SensorCellsToRegionRectangleConnectTest extends TestCase {

    private AbstractSensorCellsToRegionConnect connectType;

    public void setUp() {
        this.connectType = new SensorCellsToRegionRectangleConnect();
    }

    public void test_connect() {
        Region leafRegion = new Region("leafRegion", 8, 8, 4, 20, 3);
        SensorCell[][] sensorCells = new VisionCell[66][66];
        for (int row = 0; row < sensorCells.length; row++) {
            for (int column = 0; column < sensorCells[0].length; column++) {
                sensorCells[row][column] = new VisionCell();
            }
        }

        this.connectType.connect(sensorCells, leafRegion, 2, 2);

        Column[][] columns = leafRegion.getColumns();
        for (int parentColumnRowPosition = 0; parentColumnRowPosition < leafRegion.getNumberOfRowsAlongRegionYAxis(); parentColumnRowPosition++) {
            for (int parentColumnColumnPosition = 0; parentColumnColumnPosition < leafRegion
                    .getNumberOfColumnsAlongRegionXAxis(); parentColumnColumnPosition++) {
                assertEquals(100, columns[parentColumnRowPosition][parentColumnColumnPosition]
                        .getProximalSegment().getSynapses().size());
            }
        }
    }
}
