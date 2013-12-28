package model;

import model.MARK_I.VisionCell;

import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * Input to Retina: images of different possible formats.
 *
 * Output of Retina: activity of Cells within VisionCells.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version June 5, 2013
 */
public class Retine {
    private VisionCell[][] visionCells;

    public Retine(VisionCell[][] visionCells) {
	this.visionCells = visionCells;
    }

    public VisionCell[][] getVisionCells() {
	return this.visionCells;
    }

    /**
     * Update the state of the Retina with the given .bmp file name.
     *
     * @param BMPFileName
     */
    public void seeBMPImage(String BMPFileName) throws IOException {
	BufferedImage image = ImageIO.read(getClass().getResource(BMPFileName));

	for (int yPixel = 0; yPixel < this.visionCells[0].length; yPixel++) {
	    for (int xPixel = 0; xPixel < this.visionCells.length; xPixel++) {
		int color = image.getRGB(xPixel, yPixel);
		if (color == Color.BLACK.getRGB()) {
		    this.visionCells[xPixel][yPixel].setActiveState(true);
		} else {
		    this.visionCells[xPixel][yPixel].setActiveState(false);
		}
	    }
	}
    }

    /**
     * @param image
     *            Any element in the 2D array with a value of zero will mean the
     *            corresponding VisionCell 2D array element will have
     *            activeState set to false. Any other number sets activeState to
     *            true.
     */
    public void see2DIntArray(int[][] image) {
	for (int yPixel = 0; yPixel < this.visionCells[0].length; yPixel++) {
	    for (int xPixel = 0; xPixel < this.visionCells.length; xPixel++) {
		if (image[xPixel][yPixel] != 0) {
		    this.visionCells[xPixel][yPixel].setActiveState(true);
		} else {
		    this.visionCells[xPixel][yPixel].setActiveState(false);
		}
	    }
	}
    }

    public void seeJPEGImage(String JPEGFileName) {
	// TODO: implement...
    }
}
