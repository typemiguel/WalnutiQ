package model;

import model.util.BoundingBox;
import model.util.ReadImage;
import model.util.Point3D;

import java.awt.*;
import java.io.IOException;

/**
 * Created by Quinn Liu(quinnliu@vt.edu) on 6/28/14.
 */
public class ImageViewer {
    private int[][] entireImage;
    private Retina retina;
    private BoundingBox boxRetinaIsStuckIn;
    private Point3D retinaPositionWithinBox;

    /**
     * The initial position of the retina is set at the max depth and in the middle of the
     * image to allow it to see the entire image.
     *
     * @param BMPFileName
     * @param retina
     * @throws IOException
     */
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

        this.updateRetinaWithSeenPartOfImageBasedOnCurrentPosition();
    }

    void moveRetinaToNewPositionInsideOfBoundingBox(Point3D newRetinaPosition) {
        double retinaX = newRetinaPosition.getX();
        double retinaY = newRetinaPosition.getY();
        double retinaZ = newRetinaPosition.getZ();

        double boxX = this.boxRetinaIsStuckIn.getWidth();
        double boxY = this.boxRetinaIsStuckIn.getHeight();
        double boxZ = this.boxRetinaIsStuckIn.getDepth();

        if (retinaX < 0) {
            this.retinaPositionWithinBox.setX(0);
        } else if (retinaX > boxX) {
            this.retinaPositionWithinBox.setX(boxX);
        } else {
            this.retinaPositionWithinBox.setX(retinaX);
        }

        if (retinaY < 0) {
            this.retinaPositionWithinBox.setY(0);
        } else if (retinaY > boxY) {
            this.retinaPositionWithinBox.setY(boxY);
        } else {
            this.retinaPositionWithinBox.setY(retinaY);
        }

        if (retinaZ < 0) {
            this.retinaPositionWithinBox.setZ(0);
        } else if (retinaZ > boxZ) {
            this.retinaPositionWithinBox.setZ(boxZ);
        } else {
            this.retinaPositionWithinBox.setZ(retinaZ);
        }
    }

    void updateRetinaWithSeenPartOfImageBasedOnCurrentPosition() {
        int[][] seenAreaFromEntireImage = this.seenAreaOfEntireImage();
    }

    int[][] seenAreaOfEntireImage() {
        double retinaX = this.retinaPositionWithinBox.getX();
        double retinaY = this.retinaPositionWithinBox.getY();
        double retinaZ = this.retinaPositionWithinBox.getZ();

        double topLeftOfSeenX = retinaX - retinaZ;
        double topLeftOfSeenY = retinaY + retinaZ;
        int numberOfColumnsSeen = (int) retinaZ * 2;
        int numberOfRowsSeen = (int) retinaZ * 2;

        int[][] seenImageToReturn = new int[numberOfRowsSeen][numberOfColumnsSeen];
        int retinaRow = 0;
        int retinaColumn = 0;

        int rowStart = (int) topLeftOfSeenY;
        int rowEnd = rowStart + numberOfRowsSeen;

        int columnStart = (int) topLeftOfSeenX;
        int columnEnd = columnStart + numberOfColumnsSeen;
        for (int row = rowStart; row < rowEnd; row++) {
            retinaColumn = 0; // starting on new row
            for (int column = columnStart; column < columnEnd; column++) {
                if (row < 0 || row > this.entireImage.length) {
                    // row index is out of bounds
                } else if (column < 0 || column > this.entireImage[0].length) {
                    // column index is out of bounds
                } else {
                    seenImageToReturn[retinaRow][retinaColumn] = this.entireImage[row][column];
                }
                retinaColumn++;
            }
            retinaRow++;
        }

        return seenImageToReturn;
    }

    /**
     * @return Always return what the Retina is looking at based on it's
     * current position.
     */
    public int[][] whatIsTheRetinaCurrentlyLookingAt() {
        return null; // TODO:
    }
}
