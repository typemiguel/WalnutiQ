package model;

import model.MARK_I.VisionCell;
import java.io.IOException;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Feb 9, 2014
 */
public class RetinaTest extends junit.framework.TestCase {
    private Retina retina;

    public void setUp() {
	this.retina = new Retina(5, 15);
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
    }
}
