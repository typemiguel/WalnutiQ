package model;


import java.awt.Color;

import java.io.IOException;

import model.MARK_II.VisionCell;

public class Test_Retina extends junit.framework.TestCase {
    private Retina retina;

    public void setUp() {
	VisionCell[][] visionCells = new VisionCell[65][65];
	for (int x = 0; x < visionCells.length; x++) {
	    for (int y = 0; y < visionCells[0].length; y++) {
		visionCells[x][y] = new VisionCell();
	    }
	}
	this.retina = new Retina(visionCells);
    }

    public void test_seeBMPImage() throws IOException {
	this.retina.seeBMPImage("2.bmp");

	VisionCell[][] visionCells = this.retina.getVisionCells();

	for (int yPixel = 0; yPixel < visionCells[0].length; yPixel++)
        {
	    for (int xPixel = 0; xPixel < visionCells.length; xPixel++)
	    {
                if (visionCells[xPixel][yPixel].getActiveState() == true) {
                    System.out.print("1");
                } else {
                    System.out.print("0");
                }
	    }
	    System.out.print("\n");
        }
    }
}
