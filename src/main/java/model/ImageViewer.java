package model;

import model.util.BoundingBox;
import model.util.ReadImage;
import model.util.Point3D;
import java.io.IOException;

/**
 * Created by Quinn Liu(quinnliu@vt.edu) on 6/28/14.
 */
public class ImageViewer {
    private int[][] entireImage;
    private Retina retina;
    private BoundingBox boxRetinaIsStuckIn;
    private Point3D retinaPositionWithinBox;

    public ImageViewer(String BMPFileName, Retina retina)
            throws IOException {
        ReadImage readImage = new ReadImage();
        this.entireImage = readImage.readBMPImage(BMPFileName);
        this.retina = retina;

        int numberOfRowPixels = this.entireImage.length;
        int numberOfColumnPixels = this.entireImage[0].length;
        double longerEntireImageDimension = Math.max(numberOfRowPixels, numberOfColumnPixels);
        double distanceZAwayFromImageToSeeEntireImage = longerEntireImageDimension / 2;

        int height = numberOfRowPixels; // Y-Axis
        int width = numberOfColumnPixels; // X-Axis
        int depth = (int) distanceZAwayFromImageToSeeEntireImage; // Z-Axis
        this.boxRetinaIsStuckIn = new BoundingBox(width, height, depth);

        this.retinaPositionWithinBox = new Point3D(width/2, height/2, depth);
    }

    /**
     * @param newRetinaPosition If the new position is not within the box the retina is stuck in
     *                          it is moved to be inside it.
     */
    public void saccadeRetinaToNewPosition(Point3D newRetinaPosition) {
        // TODO:
        this.moveRetinaToNewPositionInsideOfBoundingBox(newRetinaPosition);


        // updateRetinaWithSeenPartOfImageBasedOnCurrentPosition();
    }

    void moveRetinaToNewPositionInsideOfBoundingBox(Point3D newRetinaPosition) {
        double x = newRetinaPosition.getX();
        double y = newRetinaPosition.getY();
        double z = newRetinaPosition.getZ();

        double boxX = this.boxRetinaIsStuckIn.getWidth();
        double boxY = this.boxRetinaIsStuckIn.getHeight();
        double boxZ = this.boxRetinaIsStuckIn.getDepth();

        if (x < 0) {
            this.retinaPositionWithinBox.setX(0);
        } else if (x > boxX) {
            this.retinaPositionWithinBox.setX(boxX);
        } else {
            this.retinaPositionWithinBox.setX(x);
        }

        if (y < 0) {
            this.retinaPositionWithinBox.setY(0);
        } else if (y > boxY) {
            this.retinaPositionWithinBox.setY(boxY);
        } else {
            this.retinaPositionWithinBox.setY(y);
        }

        if (z < 0) {
            this.retinaPositionWithinBox.setZ(0);
        } else if (z > boxZ) {
            this.retinaPositionWithinBox.setZ(boxZ);
        } else {
            this.retinaPositionWithinBox.setZ(z);
        }
    }

    /**
     * @return Always return what the Retina is looking at based on it's
     * current position.
     */
    public int[][] whatIsTheRetinaCurrentlyLookingAt() {
        return null; // TODO:
    }
}
