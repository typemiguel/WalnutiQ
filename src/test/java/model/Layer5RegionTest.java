package model;

import junit.framework.TestCase;
import model.util.BoundingBox;
import model.util.Point3D;

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
        Point3D newRetinaPosition = this.layer5Region.getMotorOutput(new BoundingBox(66, 66, 33));
        System.out.println("(x,y,z) = " + "(" + newRetinaPosition.getX() + "," + newRetinaPosition.getY() +
                "," + newRetinaPosition.getZ() + ")");
        //assertEquals(new Point3D(0, 1, 2), newRetinaPosition);
    }
}
