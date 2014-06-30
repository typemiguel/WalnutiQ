package model;

import junit.framework.TestCase;
import model.util.Point3D;
import model.util.RegionConsoleViewer;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Quinn Liu(quinnliu@vt.edu) on 6/15/14.
 */
public class ImageViewerTest extends TestCase {

    private ImageViewer imageViewer;
    private int[][] twoDotBMP;

    public void setUp() throws IOException {
        Retina retina = new Retina(66, 66);
        this.imageViewer = new ImageViewer("2.bmp", retina);

        this.twoDotBMP = new int[][]{
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }
        };


    }

    public void test_saccadeRetinaToNewPosition() throws IOException {
        int[][] same = new int[][]{
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }
        };

        int[][] seenAreaFittedToRetinaSize = this.imageViewer.saccadeRetinaToNewPosition(new Point3D(33, 33, 33));
        assertTrue(Arrays.deepEquals(this.twoDotBMP, same));
        RegionConsoleViewer.printDoubleIntArray(seenAreaFittedToRetinaSize);
    }
}
