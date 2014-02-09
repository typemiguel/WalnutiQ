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
	this.retina = new Retina(5, 15);
    }

    public void test_seeBMPImage() throws IOException {
	this.retina.seeBMPImage("Array2DTest.bmp");

	printBMPImageToConsole(this.retina.getVisionCells());

	VisionCell[][] visionCells = this.retina.getVisionCells();
	assertTrue(visionCells[1][3].getActiveState());
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
