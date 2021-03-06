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

        try {
            this.rectangle.setTopLeftCorner(new Point2D.Double(-1, 0));
            fail("should've thrown an exception!");
        } catch (IllegalArgumentException expected) {
            assertEquals("In class Rectangle isNewTopLeftCornerValid method the input point must be >= 0",
                    expected.getMessage());
        }

        try {
            this.rectangle.setTopLeftCorner(new Point2D.Double(1.6, 0));
            fail("should've thrown an exception!");
        } catch (IllegalArgumentException expected) {
            assertEquals("In class Rectangle isNewTopLeftCornerValid method the input point is not to the top left of the current bottom right point",
                    expected.getMessage());
        }
    }

    public void test_setBottomRightCorner() {
        this.rectangle.setBottomRightCorner(new Point2D.Double(0.2, 0.1));
        assertEquals(0.2, this.rectangle.getBottomRightCorner().getX());
        assertEquals(0.1, this.rectangle.getBottomRightCorner().getY());

        try {
            this.rectangle.setBottomRightCorner(new Point2D.Double(-1, 0.3));
            fail("should've thrown an exception!");
        } catch (IllegalArgumentException expected) {
            assertEquals("In class Rectangle isNewBottomRightCornerValid method the input point must be >= 0",
                    expected.getMessage());
        }

        try {
            this.rectangle.setBottomRightCorner(new Point2D.Double(0.05, 1));
            fail("should've thrown an exception!");
        } catch (IllegalArgumentException expected) {
            assertEquals("In class Rectangle isNewBottomRightCornerValid method the input point is not to the bottom right of the current top left point",
                    expected.getMessage());
        }
    }
}
