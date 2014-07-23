package model;

import junit.framework.TestCase;
import model.MARK_II.VisionCell;
import model.util.Rectangle;

import java.awt.geom.Point2D;
import java.io.IOException;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Feb 9, 2014
 */
public class RetinaTest extends TestCase {
    private Retina retina;

    public void setUp() {
        this.retina = new Retina(5, 15);
    }

    public void test_getVisionCells() throws IOException {
        // TODO: not working
        this.retina.seeBMPImage("Array2DTest.bmp");

        Rectangle partialRetinaWanted = new Rectangle(new Point2D.Double(0, 0), new Point2D.Double(2, 7));
 //       VisionCell[][] partialVisionCells = this.retina.getVisionCells(partialRetinaWanted);
//        int numberOfRows = partialVisionCells.length;
//        int numberOfColumns = partialVisionCells[0].length;
//        assertEquals(2, numberOfRows);
//        assertEquals(7, numberOfColumns);
//        assertTrue(partialVisionCells[1][3].getActiveState());
//        assertFalse(partialVisionCells[0][3].getActiveState());
    }

    public void test_seeBMPImage() throws IOException {
        /**
         * Array2DTest.bmp =
         * 000000000000000
         * 000100000000000
         * 000000000000000
         * 000000000000000
         * 000000000000000
         */
        this.retina.seeBMPImage("Array2DTest.bmp");

        VisionCell[][] visionCells = this.retina.getVisionCells();
        int numberOfRows = visionCells.length;
        int numberOfColumns = visionCells[0].length;
        assertEquals(5, numberOfRows);
        assertEquals(15, numberOfColumns);
        assertTrue(visionCells[1][3].getActiveState());
        assertFalse(visionCells[0][3].getActiveState());

        this.retina.seeBMPImage("Array2DTest2.bmp");
        VisionCell[][] visionCells2 = this.retina.getVisionCells();
        /**
         * Array2DTest2.bmp =
         * 000100000000000 NOTE: visionCells2[0][3] = 1 where before it = 0
         * 000000000000000
         * 000000000000000
         * 000000000000000
         * 000000000000000
         */
        int numberOfRows2 = visionCells2.length;
        int numberOfColumns2 = visionCells2[0].length;
        assertEquals(5, numberOfRows2);
        assertEquals(15, numberOfColumns2);
        assertFalse(visionCells2[1][3].getActiveState());
        assertTrue(visionCells2[0][3].getActiveState());
    }
}
