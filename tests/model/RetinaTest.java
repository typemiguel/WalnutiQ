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

	VisionCell[][] visionCells = this.retina.getVisionCells();

	// line 8 of 2.bmp = 0000000000000000000000000000011111111111...
	// index =           0123456789012345678901234567890123456789...
	assertFalse(visionCells[8][28].getActiveState());

	assertTrue(visionCells[25][10].getActiveState());

	this.retina.seeBMPImage("2_with_some_noise.bmp");
	// TODO: assert vision cells in retina has changed
    }
}
