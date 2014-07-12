package model.MARK_II.connectTypes;

import junit.framework.TestCase;
import model.MARK_II.Column;
import model.MARK_II.Region;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version June 13, 2013
 */
public class RegionToRegionRectangleConnectTest extends TestCase {
    private AbstractRegionToRegionConnect connectType;
    private Region parentRegion;
    private Region childRegion;

    public void setUp() {
        this.connectType = new RegionToRegionRectangleConnect();
        this.parentRegion = new Region("parentRegion", 8, 8, 4, 20, 3);
        this.childRegion = new Region("childRegion", 66, 66, 4, 20, 3);
    }

    public void test_ConnectWithNoOverlap() {
        this.connectType.connect(this.childRegion, this.parentRegion, 0, 0);

        Column[][] columns = this.parentRegion.getColumns();
        for (int parentRegionRow = 0; parentRegionRow < this.parentRegion
                .getNumberOfRowsAlongRegionYAxis(); parentRegionRow++) {
            for (int parentRegionColumn = 0; parentRegionColumn < this.parentRegion
                    .getNumberOfColumnsAlongRegionXAxis(); parentRegionColumn++) {
                assertEquals(64, columns[parentRegionRow][parentRegionColumn]
                        .getProximalSegment().getSynapses().size());
            }
        }
    }

    public void test_ConnectWithLittleOverlap() {
        this.connectType.connect(this.childRegion, this.parentRegion, 2, 2);

        Column[][] columns = this.parentRegion.getColumns();
        for (int parentColumnX = 0; parentColumnX < this.parentRegion
                .getNumberOfRowsAlongRegionYAxis(); parentColumnX++) {
            for (int parentColumnY = 0; parentColumnY < this.parentRegion
                    .getNumberOfColumnsAlongRegionXAxis(); parentColumnY++) {
                assertEquals(100, columns[parentColumnX][parentColumnY]
                        .getProximalSegment().getSynapses().size());
            }
        }
    }

    public void test_ConnectWithALotOverlap() {
        this.connectType.connect(this.childRegion, this.parentRegion, 8, 8);

        Column[][] columns = this.parentRegion.getColumns();
        for (int parentColumnX = 0; parentColumnX < this.parentRegion
                .getNumberOfRowsAlongRegionYAxis(); parentColumnX++) {
            for (int parentColumnY = 0; parentColumnY < this.parentRegion
                    .getNumberOfColumnsAlongRegionXAxis(); parentColumnY++) {
                assertEquals(225, columns[parentColumnX][parentColumnY]
                        .getProximalSegment().getSynapses().size());
            }
        }
    }
}
