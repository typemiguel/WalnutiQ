package model.util;

import javax.imageio.ImageIO;
import java.awt.*;
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

        for (int currentColumn = 0; currentColumn < numberOfColumns; currentColumn++) {
            for (int currentRow = 0; currentRow < numberOfRows; currentRow++) {

                int RGBcolor = image.getRGB(currentColumn, currentRow);

                if (RGBcolor == Color.BLACK.getRGB()) {
                    imageToReturn[currentRow][currentColumn] = 1;
                } else {
                    imageToReturn[currentRow][currentColumn] = 0;
                }
            }
        }

        return imageToReturn;
    }
}
