package model;

import model.MARK_II.VisionCell;

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

    public VisionCell[][] getVisionCells(int rowStart, int columnStart, int rowEnd, int columnEnd) {
        // TODO: actually implement
        return null;
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
