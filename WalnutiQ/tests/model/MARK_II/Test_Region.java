package model.MARK_II;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK II | June 13, 2013
 */
public class Test_Region extends junit.framework.TestCase {
    private Region region;

    public void setUp() {
	this.region = new Region("root", 5, 7, 4, 20, 3);
    }

    public void test_Region() {
	try {
	    this.region = new Region("V1", 0, 7, 4, 20, 3);
	    fail("should've thrown an exception!");
	} catch (IllegalArgumentException expected) {
	    assertEquals("numberOfColumnsAlongXAxis in Region constructor cannot be less than 1",
		    expected.getMessage());
	}

	try {
	    this.region = new Region("V1", 5, 7, 0, 20, 3);
	    fail("should've thrown an exception!");
	} catch (IllegalArgumentException expected) {
	    assertEquals("cellsPerColumn in Region constructor cannot be less than 1",
		    expected.getMessage());
	}

	try {
	    this.region = new Region("V1", 5, 7, 1, -20, 3);
	    fail("should've thrown an exception!");
	} catch (IllegalArgumentException expected) {
	    assertEquals("percentMinimumOverlapScore in Region constructor must be between 0 and 100",
		    expected.getMessage());
	}
    }

    public void test_toString() {
	Region region2 = new Region("child1", 5, 7, 4, 20, 3);
	Region region3 = new Region("child2", 5, 7, 4, 20, 3);

	this.region.addChildRegion(region2);
	this.region.addChildRegion(region3);

	System.out.println(this.region.toString());
    }
}
