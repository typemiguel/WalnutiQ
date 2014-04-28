package model.MARK_II.movement;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Apr 28, 2014
 */
public class ImageViewer {
    /**
     * Assumme image is (0, 0) on bottom left (image.width, image.height) on top
     * right. In other words the Image is in the 1st quadrant of the Cartesian
     * plane.
     */
    private int[][] image;

    private SaccadingRetina retina;

    public ImageViewer(int[][] image, SaccadingRetina retina) {
	this.image = image;
	this.retina = retina;
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

    /**
     * @param image
     * @param reductionScale
     *            If = 2 an image 1/4th the size of the original image will be
     *            returned
     * @return manipulated image based on reductionScale
     */
    int[][] manipulatedImage(int[][] image, int reductionScale) {

	int[][] manipulatedImage = new int[image.length / reductionScale][image[0].length
		/ reductionScale];

	for (int i = 0; i < manipulatedImage.length; i++) {
	    for (int j = 0; j < manipulatedImage[0].length; j++) {
		int numberOf1sInLocalImageArea = 0;
		for (int x = 0; x < reductionScale; x++) {
		    for (int y = 0; y < reductionScale; y++) {
			numberOf1sInLocalImageArea += image[i * reductionScale
				+ x][j * reductionScale + y];
		    }
		}
		manipulatedImage[i][j] = numberOf1sInLocalImageArea > (reductionScale
			* reductionScale / 2) ? 1 : 0;
	    }
	}

	return manipulatedImage;
    }
}
