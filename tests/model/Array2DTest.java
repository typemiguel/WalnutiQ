package model;

import java.awt.Color;

import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * Example of how 2D arrays can be used in Java.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Feb 9, 2014
 */
public class Array2DTest extends junit.framework.TestCase {
    private int[][] array2D;

    public void setUp() {
	this.array2D = new int[5][15];
    }

    public void test_2DArray() {
	this.array2D[1][3] = 1;

	printBMPImageToConsole(this.array2D);
    }

    public void test_getBufferImageDotGetRGB() throws IOException {
	BufferedImage image = ImageIO.read(getClass().getResource(
		"Array2DTest.bmp"));

	// image.getRGB(x, y)
	assertEquals(Color.BLACK.getRGB(), image.getRGB(3, 1));
    }

    private static void printBMPImageToConsole(int[][] visionCells) {
	int numberOfRows = visionCells.length;
	int numberOfColumns = visionCells[0].length;
	System.out.println("numberOfRows = " + numberOfRows);
	System.out.println("numberOfColumns = " + numberOfColumns);

	for (int currentRow = 0; currentRow < numberOfRows; currentRow++) {
	    for (int currentColumn = 0; currentColumn < numberOfColumns; currentColumn++) {
		if (visionCells[currentRow][currentColumn] == 1) {
		    System.out.print("1");
		} else {
		    System.out.print("0");
		}
	    }
	    System.out.print("\n");
	}
    }
}
