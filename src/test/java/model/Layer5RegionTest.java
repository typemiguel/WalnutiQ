package model;

import junit.framework.TestCase;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version July 5, 2014
 */
public class Layer5RegionTest extends TestCase {
    private Layer5Region layer5Region;

    public void setUp() {
        this.layer5Region = new Layer5Region("layer 5 region", 100, 300, 1, 20, 3);
    }

    public void test_getMotorOutput() {
        // TODO:
    }
}
