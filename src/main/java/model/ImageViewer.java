package model;

import model.MARK_II.VisionCell;
import model.util.BoundingBox;
import model.util.ReadImage;
import model.util.Point3D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Quinn Liu(quinnliu@vt.edu) on 6/28/14.
 */
public class ImageViewer {
    private Image BMPImage;
    private int[][] int2DImage;
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
        this.BMPImage = ImageIO.read(getClass().getResource(BMPFileName));

        ReadImage readImage = new ReadImage();
        this.int2DImage = readImage.readBMPImage(BMPFileName);
        this.retina = retina;

        int numberOfRowPixels = this.int2DImage.length;
        int numberOfColumnPixels = this.int2DImage[0].length;
        double longerEntireImageDimension = Math.max(numberOfRowPixels, numberOfColumnPixels);
        double distanceZAwayFromImageToSeeEntireImage = longerEntireImageDimension / 2;

        int height = numberOfRowPixels; // Y-Axis
        int width = numberOfColumnPixels; // X-Axis
        int depth = (int) distanceZAwayFromImageToSeeEntireImage; // Z-Axis
        this.boxRetinaIsStuckIn = new BoundingBox(width, height, depth);

        this.retinaPositionWithinBox = new Point3D(width/2, height/2, depth);
    }

    public Retina getRetina() {
        return this.retina;
    }

    /**
     * @param newRetinaPosition If the new position is not within the box the retina is stuck in
     *                          it is moved to be inside it.
     */
    public void saccadeRetinaToNewPosition(Point3D newRetinaPosition) throws IOException {
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

    void updateRetinaWithSeenPartOfImageBasedOnCurrentPosition() throws IOException {
        int[][] seenAreaFromEntireImage = this.seenAreaOfEntireImage();

        int[][] seenAreaFittedToRetinaSize = this.resizeSeenAreaToFitRetina(seenAreaFromEntireImage);

        this.retina.see2DIntArray(seenAreaFittedToRetinaSize);
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
                if (row < 0 || row > this.int2DImage.length) {
                    // row index is out of bounds
                } else if (column < 0 || column > this.int2DImage[0].length) {
                    // column index is out of bounds
                } else {
                    seenImageToReturn[retinaRow][retinaColumn] = this.int2DImage[row][column];
                }
                retinaColumn++;
            }
            retinaRow++;
        }

        return seenImageToReturn;
    }

    int[][] resizeSeenAreaToFitRetina(int[][] seenAreaFromEntireImage) throws IOException {
        BufferedImage imageToResize = this.convertInt2DArrayToBufferedImage(seenAreaFromEntireImage);

        VisionCell[][] visionCells = this.retina.getVisionCells();
        int newWidth = visionCells[0].length;
        int newHeight = visionCells.length;
        BufferedImage resizedImage = this.resize(imageToResize, newWidth, newHeight);

        return this.convertBufferedImageToInt2DArray(resizedImage);
    }

    BufferedImage convertInt2DArrayToBufferedImage(int[][] int2DArray) {
        int numberOfRows = int2DArray.length;
        int numberOfColumns = int2DArray[0].length;
        BufferedImage convertedImage = new BufferedImage(numberOfColumns, numberOfRows, BufferedImage.TYPE_BYTE_BINARY);

        for (int row = 0; row < numberOfRows; row++) {
            for (int column = 0; column < numberOfColumns; column++) {
                int oneOrZero = int2DArray[row][column];

                if (oneOrZero == 1) {
                    convertedImage.setRGB(column, row, Color.BLACK.getRGB());
                } else {
                    convertedImage.setRGB(column, row, Color.WHITE.getRGB());
                }
            }
        }

        return convertedImage;
    }

    BufferedImage resize(BufferedImage imageToResize, int newWidth, int newHeight) {
        BufferedImage resizedImage = null;
        if (imageToResize != null) {
            resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_BINARY);
            Graphics2D g = resizedImage.createGraphics();
            // scaleWidth 0.5 means resizedImage width is half is wide as imageToResize
            // scaleHeight 2.0 means resizedImage height is twice as high as imageToResize
            double scaleWidth = newWidth / imageToResize.getWidth();
            double scaleHeight = newHeight / imageToResize.getHeight();
            AffineTransform AT = AffineTransform.getScaleInstance(scaleWidth, scaleHeight);
            g.drawRenderedImage(imageToResize, AT);
        }
        return resizedImage;
    }

    int[][] convertBufferedImageToInt2DArray(BufferedImage BMPImage) {
        int numberOfRows = BMPImage.getHeight();
        int numberOfColumns = BMPImage.getWidth();

        int[][] imageToReturn = new int[numberOfRows][numberOfColumns];

        for (int column = 0; column < numberOfColumns; column++) {
            for (int row = 0; row < numberOfRows; row++) {

                int RGBcolor = BMPImage.getRGB(column, row); // Image coordinates are mapped differently

                if (RGBcolor == Color.BLACK.getRGB()) {
                    imageToReturn[row][column] = 1;
                } else {
                    imageToReturn[row][column] = 0;
                }
            }
        }

        return imageToReturn;
    }
}
