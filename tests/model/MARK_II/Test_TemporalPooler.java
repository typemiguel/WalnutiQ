package model.MARK_II;

import model.MARK_II.ConnectTypes.RegionToRegionConnect;
import model.MARK_II.ConnectTypes.RegionToRegionRectangleConnect;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK II | July 21, 2013
 */
public class Test_TemporalPooler extends junit.framework.TestCase {
    private Region parentRegion;
    private TemporalPooler temporalPooler;

    public void setUp() {
	this.parentRegion = new Region("parentRegion", 8, 8, 4, 20, 3);
	Region childRegion = new Region("childRegion", 66, 66, 4, 20, 3);
	RegionToRegionConnect connectType = new RegionToRegionRectangleConnect();
	connectType.connect(childRegion, this.parentRegion, 2, 2);
	this.temporalPooler = new TemporalPooler(this.parentRegion);
    }
}
