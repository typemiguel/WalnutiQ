package model.MARK_I.connectTypes;

import model.MARK_II.Column;
import model.MARK_II.Region;

import junit.framework.TestCase;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version April 19th, 2014
 */
public class RegionToRegionRandomConnectTest extends TestCase {
    private AbstractRegionToRegionConnect connectType;
    private Region parentRegion;
    private Region childRegion;

    public void setUp() {
	this.connectType = new RegionToRegionRandomConnect();
	this.parentRegion = new Region("parentRegion", 8, 8, 1, 20, 3);
	this.childRegion = new Region("childRegion", 66, 66, 1, 20, 3);
    }

    public void test_ConnectWithNoOverlap() {
	this.connectType.connect(this.childRegion, this.parentRegion, 2, 2);

	Column[][] columns = this.parentRegion.getColumns();
	for (int parentColumnX = 0; parentColumnX < this.parentRegion
		.getXAxisLength(); parentColumnX++) {
	    for (int parentColumnY = 0; parentColumnY < this.parentRegion
		    .getYAxisLength(); parentColumnY++) {
		assertEquals(72, columns[parentColumnX][parentColumnY]
			.getProximalSegment().getSynapses().size());
	    }
	}
    }
}
