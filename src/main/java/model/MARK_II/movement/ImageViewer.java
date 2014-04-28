package model.MARK_II.movement;

import java.awt.Point;

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
    public void updateRetinaWithSeenPartOfImageBasedOnCurrentPosition() {
	double x = this.retina.getPosition().getX();
	double y = this.retina.getPosition().getY();
	double d = this.retina.getDistanceBetweenImageAndRetina();
	Point topLeftOfSeen = new Point((int) (x - d), (int) (y + d));
	int widthOfSeen = (int) d;
	int lengthOfSeen = (int) d;

	int[][] seenAreaFromMainImage = this.getSeenAreaFromMainImage(
		topLeftOfSeen, widthOfSeen, lengthOfSeen);

	int[][] fittedToRetina = this.fitToRetina();

	this.retina.see2DIntArray(fittedToRetina);
    }

    int[][] getSeenAreaFromMainImage(Point topLeftOfSeen, int widthOfSeen,
	    int lengthOfSeen) {
	return null;
    }

    int[][] fitToRetina() {
	return null;
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
