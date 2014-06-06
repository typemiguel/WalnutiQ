package model;

import java.awt.geom.Point2D;

import model.util.BoundingBox;

import java.io.IOException;

import java.awt.Point;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version May 31, 2014
 */
public class ImageViewer {
	private SaccadingRetina retina;

	/**
	 * Assumme image is (0, 0) on bottom left (image.width, image.height) on top
	 * right. In other words the Image is in the 1st quadrant of the Cartesian
	 * plane.
	 */
	private int[][] image;
	private BoundingBox boxRetinaIsStuckIn;

	public ImageViewer(String BMPFileName, SaccadingRetina retina)
			throws IOException {
		this.retina = retina;
		this.retina.seeBMPImage(BMPFileName);
		this.image = this.retina.getDoubleIntArrayRepresentationOfVisionCells();

		double longerImageDimension = Math.max(image.length, image[0].length);
		double distanceZAwayFromImageToSeeEntireImage = longerImageDimension / 2;
		this.boxRetinaIsStuckIn = new BoundingBox(image.length,
				image[0].length, distanceZAwayFromImageToSeeEntireImage);
	}

	public void updateRetinaWithSeenPartOfImageBasedOnCurrentPosition(
			Point retinaPosition, double distanceBetweenImageAndRetina) {
		if (this.boxRetinaIsStuckIn.contains(retinaPosition,
				distanceBetweenImageAndRetina)) {
			// retina is at a valid location so do nothing
		} else {
			this.moveRetinaInsideOfBoundingBox(this.retina,
					this.boxRetinaIsStuckIn);
		}

		int[][] seenAreaFromMainImage = this.getSeenAreaFromMainImage();

		int[][] fittedToRetina = this.fitToRetina(seenAreaFromMainImage);

		this.retina.see2DIntArray(fittedToRetina);
	}

	void moveRetinaInsideOfBoundingBox(SaccadingRetina retina,
			BoundingBox boundingBox) {
		double retinaX = retina.getPosition().getX();
		double retinaY = retina.getPosition().getY();
		double retinaZ = retina.getDistanceBetweenImageAndRetina();

		double boxX = boundingBox.getWidth();
		double boxY = boundingBox.getHeight();
		double boxZ = boundingBox.getDepth();

		if (retinaX < 0) {
			retina.setPosition(new Point2D.Double(0.0, retina.getPosition()
					.getY()));
		} else if (retinaX > boxX) {
			retina.setPosition(new Point2D.Double(boxX, retina.getPosition()
					.getY()));
		}

		if (retinaY < 0) {
			retina.setPosition(new Point2D.Double(retina.getPosition().getX(),
					0.0));
		} else if (retinaY > boxY) {
			retina.setPosition(new Point2D.Double(retina.getPosition().getX(),
					boxY));
		}

		if (retinaZ < 0) {
			retina.setDistanceBetweenImageAndRetina(0);
		} else if (retinaZ > boxZ) {
			retina.setDistanceBetweenImageAndRetina(boxZ);
		}
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
					imageToReturn[i][j] = 1;// this.image[x][y];
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
		int[][] fittedImage = this.convertImage(seenAreaFromMainImage,
				reductionScale);
		return fittedImage;
	}

	/**
	 * @param image
	 * @param reductionScale
	 *            If = 2 an image 1/4th the size of the original image will be
	 *            returned. Note because we limit the movement in the z-axis to
	 *            be half of the longest distance in the case of a square retina
	 *            we will never need to enlarge the image.
	 * @return manipulated image based on reductionScale
	 */
	int[][] convertImage(int[][] image, double reductionScale) {

		int[][] manipulatedImage = new int[(int) (image.length / reductionScale)][(int) (image[0].length / reductionScale)];

		for (int i = 0; i < manipulatedImage.length; i++) {
			for (int j = 0; j < manipulatedImage[0].length; j++) {
				int numberOf1sInLocalImageArea = 0;
				for (int x = 0; x < reductionScale; x++) {
					for (int y = 0; y < reductionScale; y++) {
						numberOf1sInLocalImageArea += image[(int) (i
								* reductionScale + x)][(int) (j
								* reductionScale + y)];
					}
				}
				manipulatedImage[i][j] = numberOf1sInLocalImageArea > (reductionScale
						* reductionScale / 2) ? 1 : 0;
			}
		}

		return manipulatedImage;
	}
}
