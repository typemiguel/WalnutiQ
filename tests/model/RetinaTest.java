package model;

import model.MARK_I.VisionCell;
import java.io.IOException;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Feb 3, 2014
 */
public class RetinaTest extends junit.framework.TestCase {
    private Retina retina;

    public void setUp() {
	this.retina = new Retina(66, 66);
    }

    public void test_seeBMPImage() throws IOException {
	// comparing the entire 66x66 pixel .bmp image is too much so just
	// test specific vision cells

	this.retina.seeBMPImage("2.bmp");
	printBMPImageToConsole(this.retina.getVisionCells());

	VisionCell[][] visionCells = this.retina.getVisionCells();

	// line 8 of 2.bmp = 0000000000000000000000000000011111111111...
	// ///////// index = 0123456789012345678901234567890123456789...
	assertFalse(visionCells[7][28].getActiveState());

	// assertTrue(visionCells[7][29].getActiveState());

	// ---------------------------------------------------------------------
	// this.retina.seeBMPImage("2_with_some_noise.bmp");

	// line 8 of 2_with_some_noise.bmp
	// ///// = 0000000000000000000001000000011011111111...
	// index = 0123456789012345678901234567890123456789...

	// assertFalse(visionCells[8][28].getActiveState());

	// assertTrue(visionCells[8][31].getActiveState());

	// (this.retina.getVisionCells());

	// TODO: assert vision cells in retina has changed
    }

    private static void printBMPImageToConsole(VisionCell[][] visionCells) {
	int numberOfRows = visionCells.length;
	int numberOfColumns = visionCells[0].length;

	for (int currentRow = 0; currentRow < numberOfRows; currentRow++) {
	    for (int currentColumn = 0; currentColumn < numberOfColumns; currentColumn++) {
		if (visionCells[currentRow][currentColumn].getActiveState() == true) {
		    System.out.print("1");
		} else {
		    System.out.print("0");
		}
	    }
	    System.out.print("\n");
	}
    }
}
