package model;

import model.MARK_I.VisionCell;

import java.io.IOException;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version June 5, 2013
 */
public class RetinaTest extends junit.framework.TestCase {
    private Retine retina;

    public void setUp() {
	VisionCell[][] visionCells = new VisionCell[65][65];
	for (int x = 0; x < visionCells.length; x++) {
	    for (int y = 0; y < visionCells[0].length; y++) {
		visionCells[x][y] = new VisionCell();
	    }
	}
	this.retina = new Retine(visionCells);
    }

    public void test_seeBMPImage() throws IOException {
	this.retina.seeBMPImage("2.bmp");

	VisionCell[][] visionCells = this.retina.getVisionCells();

	for (int yPixel = 0; yPixel < visionCells[0].length; yPixel++)
        {
	    for (int xPixel = 0; xPixel < visionCells.length; xPixel++)
	    {
                if (visionCells[xPixel][yPixel].getActiveState() == true) {
                    //System.out.print("1");
                } else {
                    //System.out.print("0");
                }
	    }
	    //System.out.print("\n");
        }
    }
}
