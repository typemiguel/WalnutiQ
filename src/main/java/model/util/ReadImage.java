package model.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Quinn Liu(quinnliu@vt.edu) on 6/15/14.
 */
public class ReadImage {

    public int[][] readBMPImage(String BMPImageFileName) throws IOException {
        BufferedImage image = ImageIO.read(getClass().getResource(BMPImageFileName));
        int numberOfRows = image.getHeight();
        int numberOfColumns = image.getWidth();

        int[][] imageToReturn = new int[numberOfRows][numberOfColumns];

        for (int column = 0; column < numberOfColumns; column++) {
            for (int row = 0; row < numberOfRows; row++) {

                int RGBcolor = image.getRGB(column, row);

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
