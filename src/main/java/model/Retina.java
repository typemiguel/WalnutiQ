package model;

import model.MARK_II.VisionCell;
import model.util.*;
import model.util.Rectangle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Input to Retina: images of different possible formats.
 *
 * Output of Retina: activity of Cells within VisionCells.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Feb 3, 2014
 */
public class Retina {
    protected VisionCell[][] visionCells;

    public Retina(int numberOfVisionCellsAlongYAxis, int numberOfVisionCellsAlongXAxis) {
        this.visionCells = new VisionCell[numberOfVisionCellsAlongYAxis][numberOfVisionCellsAlongXAxis];

        for (int row = 0; row < numberOfVisionCellsAlongYAxis; row++) {
            for (int column = 0; column < numberOfVisionCellsAlongXAxis; column++) {
                this.visionCells[row][column] = new VisionCell();
            }
        }
    }

    public VisionCell[][] getVisionCells() {
        return this.visionCells;
    }

    /**
     * NOTE: When creating the input rectangle object remember that the point
     * parameters are (x, y) coordinates and not (row, column). For example:
     *
     * Rectangle rectangle = new Rectangle(new Point2D.Double(x1_or_column1, y1_or_row1), new Point2D.Double(x2_or_column2, y2_or_row2));
     *
     * @param rectangle The rectangle shape of VisionCells you want to
     *                  retrieve from the retina.
     * @return Partial 2D array of VisionCells in retina based on input rectangle
     *         dimensions.
     */
    public VisionCell[][] getVisionCells(Rectangle rectangle) {
        int rectangleWidth = (int) rectangle.getWidth();
        int rectangleHeight = (int) rectangle.getHeight();
        if (rectangleWidth > this.visionCells[0].length || rectangleHeight > this.visionCells.length) {
            throw new IllegalArgumentException("In class Retina method getVisionCells the input parameter Rectangle" +
                    "is larger than the VisionCell[][] 2D array");
        }
        VisionCell[][] partialVisionCells = new VisionCell[(int)rectangle.getHeight()][(int)rectangle.getWidth()];
        for (int row = 0; row < rectangle.getHeight(); row++) {
            for (int column = 0; column < rectangle.getWidth(); column++) {
                partialVisionCells[row][column] = this.visionCells[row][column];
            }
        }
        return partialVisionCells;
    }

    /**
     * Update the state of the OldRetina with the given .bmp file name.
     *
     * @param BMPFileName
     * @throws IOException
     */
    public void seeBMPImage(String BMPFileName) throws IOException {
        BufferedImage image = ImageIO.read(getClass().getResource(BMPFileName));

        int numberOfRows = this.visionCells.length;
        int numberOfColumns = this.visionCells[0].length;

        for (int row = 0; row < numberOfRows; row++) {
            for (int column = 0; column < numberOfColumns; column++) {

                int RGBcolor = image.getRGB(column, row);

                if (RGBcolor == Color.BLACK.getRGB()) {
                    this.visionCells[row][column]
                            .setActiveState(true);
                } else {
                    this.visionCells[row][column]
                            .setActiveState(false);
                }
            }
        }
    }

    /**
     * @param image Any element in the 2D array with a value of zero will mean the
     *              corresponding VisionCell 2D array element will have
     *              activeState set to false. Any other number sets activeState to
     *              true.
     */
    public void see2DIntArray(int[][] image) {
        int numberOfRows = this.visionCells.length;
        int numberOfColumns = this.visionCells[0].length;
        for (int row = 0; row < numberOfRows; row++) {
            for (int column = 0; column < numberOfColumns; column++) {
                if (row >= image.length || column >= image[0].length) {
                    this.visionCells[row][column]
                            .setActiveState(false);
                } else if (image[row][column] != 0) {
                    this.visionCells[row][column]
                            .setActiveState(true);
                } else {
                    this.visionCells[row][column]
                            .setActiveState(false);
                }
            }
        }
    }
}
