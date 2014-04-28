package model.MARK_II.movement;

import java.io.IOException;

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

    public ImageViewer(String BMPFileName, SaccadingRetina retina) throws IOException {
	this.retina = retina;
	this.retina.seeBMPImage(BMPFileName);
	this.image = this.retina.getDoubleIntArrayRepresentationOfVisionCells();

    }

    public void updateRetinaWithSeenPartOfImageBasedOnCurrentPosition() {
	int[][] seenAreaFromMainImage = this.getSeenAreaFromMainImage();

	int[][] fittedToRetina = this.fitToRetina(seenAreaFromMainImage);

	this.retina.see2DIntArray(fittedToRetina);
    }

    int[][] getSeenAreaFromMainImage() {
	double retinaX = this.retina.getPosition().getX();
	double retinaY = this.retina.getPosition().getY();
	double d = this.retina.getDistanceBetweenImageAndRetina();
	Point bottomLeftOfSeen = new Point((int) (retinaX - d),
		(int) (retinaY - d));
	int widthOfSeen = (int) d * 2;
	int lengthOfSeen = (int) d * 2;

	int[][] imageToReturn = new int[widthOfSeen][lengthOfSeen];
	int i = 0;
	int j = 0;

	int initialX = bottomLeftOfSeen.x;
	int finalX = initialX + widthOfSeen;
	int initialY = bottomLeftOfSeen.y;
	int finalY = initialY + lengthOfSeen;

	for (int x = initialX; x < finalX; x++) {
	    for (int y = initialY; y < finalY; y++) {
		if (x < 0 || y < 0 || x >= this.image.length
			|| y >= this.image[0].length) {
		    // this may happen when d is very large and retinaX or
		    // retinaY are small
		} else if (i < 0 || j < 0 || i >= widthOfSeen
			|| j >= lengthOfSeen) {
		    // this may happen when d is very large and retinaX or
		    // retinaY are small
		} else {
		    imageToReturn[i][j] = 1;//this.image[x][y];
		}
		j++;
	    }
	    i++;
	}
	return imageToReturn;
    }

    /**
     * For now assume int[][] seenAreaFromMainImage is a square and retina is a
     * square as well to keep things simple.
     */
    int[][] fitToRetina(int[][] seenAreaFromMainImage) {
	// if retina is 4 times bigger than seenAreaFromMainImage then
	// reductionScale = 2
	int reductionScale = this.retina.getVisionCells().length
		/ seenAreaFromMainImage.length;
	int[][] fittedImage = this.manipulatedImage(seenAreaFromMainImage,
		reductionScale);
	return fittedImage;
    }

    /**
     * @param image
     * @param reductionScale
     *            If = 2 an image 1/4th the size of the original image will be
     *            returned
     * @return manipulated image based on reductionScale
     */
    int[][] manipulatedImage(int[][] image, double reductionScale) {

	int[][] manipulatedImage = new int[(int)(image.length / reductionScale)][(int)(image[0].length
		/ reductionScale)];

	for (int i = 0; i < manipulatedImage.length; i++) {
	    for (int j = 0; j < manipulatedImage[0].length; j++) {
		int numberOf1sInLocalImageArea = 0;
		for (int x = 0; x < reductionScale; x++) {
		    for (int y = 0; y < reductionScale; y++) {
			numberOf1sInLocalImageArea += image[(int)(i * reductionScale
				+ x)][(int)(j * reductionScale + y)];
		    }
		}
		manipulatedImage[i][j] = numberOf1sInLocalImageArea > (reductionScale
			* reductionScale / 2) ? 1 : 0;
	    }
	}

	return manipulatedImage;
    }
}
