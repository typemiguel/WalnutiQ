package model;

import junit.framework.TestCase;
import model.util.ReadImage;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Quinn Liu(quinnliu@vt.edu) on 6/15/14.
 */
public class ImageViewerTest extends TestCase {

    private ImageViewer imageViewer;
    private ReadImage readImage;

    public void setUp() throws IOException {
        this.readImage = new ReadImage();
        Retina retina = new Retina(5, 15);
        //this.imageViewer = new ImageViewer("Array2DTest.bmp", retina);
    }

    public void test_resizeSeenAreaToFitRetina() throws IOException {
        //TODO:
        assertEquals(1, 2 - 1);
    }
}
