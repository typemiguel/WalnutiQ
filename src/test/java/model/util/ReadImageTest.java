package model.util;

import junit.framework.TestCase;

import java.io.IOException;

/**
 * Created by qliu on 6/28/14.
 */
public class ReadImageTest extends TestCase {
    int[][] readBMPImage;
    ReadImage readImage;

    public void setUp() {
        this.readImage = new ReadImage();
    }

    public void test_readBMPImage() throws IOException {
        //RegionConsoleViewer.printDoubleIntArray(this.readImage.readBMPImage("../Array2DTest.bmp"));
        /**
         * Array2DTest.bmp =
         * 000000000000000
         * 000100000000000
         * 000000000000000
         * 000000000000000
         * 000000000000000
         */
        this.readBMPImage = this.readImage.readBMPImage("../Array2DTest.bmp");
        int numberOfRows = this.readBMPImage.length;
        int numberOfColumns = this.readBMPImage[0].length;
        assertEquals(5, numberOfRows);
        assertEquals(15, numberOfColumns);
        assertEquals(1, this.readBMPImage[1][3]);
        assertEquals(0, this.readBMPImage[0][3]);
    }
}
