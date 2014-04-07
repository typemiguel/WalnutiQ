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
    }

    public void test_getBufferImageDotGetRGB() throws IOException {
	BufferedImage image = ImageIO.read(getClass().getResource(
		"Array2DTest.bmp"));

	// image.getRGB(x, y)
	assertEquals(Color.BLACK.getRGB(), image.getRGB(3, 1));
    }
}
