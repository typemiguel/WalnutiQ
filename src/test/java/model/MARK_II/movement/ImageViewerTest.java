package model.MARK_II.movement;

import java.awt.Point;

public class ImageViewerTest extends junit.framework.TestCase {
    private int[][] big1;

    private ImageViewer imageViewer;

    public void setUp() {
	this.big1 = new int[8][10];
	for (int x = 0; x < this.big1.length; x++) {
	    for (int y = 0; y < this.big1[0].length; y++) {
		this.big1[x][y] = 0;
	    }
	}
	this.big1[0][0] = 1;
	this.big1[1][0] = 1;
	this.big1[1][1] = 1;
	this.big1[0][1] = 1;

	this.big1[0][2] = 1;
	this.big1[0][3] = 1;
	this.big1[1][2] = 1;

	this.big1[0][4] = 1;
	this.big1[1][4] = 1;

	this.big1[0][6] = 1;

	this.imageViewer = new ImageViewer(this.big1, new SaccadingRetina(66,
		66, new ViewPosition(new Point(0, 0), 2)));
    }

//       big1          small1
//    1111101000
//    1110100000       11000
//    0000000000       00000
//    0000000000   =>  00000
//    0000000000       00000
//    0000000000
//    0000000000
//    0000000000
    public void test_manipulatedImage() {
	int[][] manipulatedImage = this.imageViewer.manipulatedImage(this.big1, 2);
	assertEquals(1, manipulatedImage[0][0]);
	assertEquals(1, manipulatedImage[0][1]);
	assertEquals(0, manipulatedImage[0][2]);
	assertEquals(0, manipulatedImage[1][0]);
    }
}
