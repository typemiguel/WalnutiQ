package model.util;

import junit.framework.TestCase;

import java.awt.geom.Point2D;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version July 13, 2014
 */
public class RectangleTest extends TestCase {
    private Rectangle rectangle;

    public void setUp() {
        this.rectangle = new Rectangle(new Point2D.Double(0.1, 0), new Point2D.Double(1.5, 2.0));
    }

    public void test_getWidth() {
        assertEquals(1.4, this.rectangle.getWidth());
    }

    public void test_getHeight() {
        assertEquals(2.0, this.rectangle.getHeight());
    }

    public void test_setTopLeftCorner() {
        this.rectangle.setTopLeftCorner(new Point2D.Double(1.4, 1.9));
        assertEquals(1.4, this.rectangle.getTopLeftCorner().getX());
        assertEquals(1.9, this.rectangle.getTopLeftCorner().getY());
    }


    // TODO: finish unit testing
}
