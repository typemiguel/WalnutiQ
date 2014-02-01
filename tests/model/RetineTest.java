package model;

import model.MARK_I.VisionCell;

import java.io.IOException;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version June 5, 2013
 */
public class RetineTest extends junit.framework.TestCase {
    private Retine retine;

    public void setUp() {
	this.retine = new Retine(66, 66);
    }

    public void test_seeBMPImage() throws IOException {
	// comparing the entire 66x66 pixel .bmp image is too much so just
	// verify specific cells

	this.retine.seeBMPImage("2.bmp");

	VisionCell[][] visionCells = this.retine.getVisionCells();

	for (int yPixel = 0; yPixel < visionCells[0].length; yPixel++) {
	    for (int xPixel = 0; xPixel < visionCells.length; xPixel++) {
		if (visionCells[xPixel][yPixel].getActiveState() == true) {
		    System.out.print("1");
		} else {
		    System.out.print("0");
		}
	    }
	    System.out.print("\n");
	}


	this.retine.seeBMPImage("2.bmp");

	//VisionCell[][] visionCells = this.retine.getVisionCells();

	// line 8 of 2.bmp = 0000000000000000000000000000011111111111...
	// index =           0123456789012345678901234567890123456789...
	assertFalse(visionCells[8][28].getActiveState());

	assertTrue(visionCells[25][10].getActiveState());
    }
}
