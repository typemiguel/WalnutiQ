package model.MARK_I.movement;

import java.awt.Point;

public class ImageViewer {
    /**
     * Assumme image is (0, 0) on bottom left (image.width, image.height) on top
     * right. In other words the Image is in the 1st quadrant of the Cartesian
     * plane.
     */
    private int[][] image;

    public ImageViewer(int[][] image) {
	this.image = image;
    }

    /**
     * Depending on where the retina is and how close it is to the image a
     * different image needs to be returned.
     */
    public int[][] seenPartOfImage(ViewPosition viewPosition) {
	// TODO: manipulate this.image based on
	// informationAboutRetinaLocationAndOrientation

	int[][] manipulatedImage = this.image;

	return manipulatedImage;
    }
}
