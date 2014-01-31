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
	this.retine.seeBMPImage("2.bmp");

	VisionCell[][] visionCells = this.retine.getVisionCells();

	for (int yPixel = 0; yPixel < visionCells[0].length; yPixel++) {
	    for (int xPixel = 0; xPixel < visionCells.length; xPixel++) {
		if (visionCells[xPixel][yPixel].getActiveState() == true) {
		    // System.out.print("1");
		} else {
		    // System.out.print("0");
		}
	    }
	    // System.out.print("\n");
	}
    }
}
