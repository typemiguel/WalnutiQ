package model;

import junit.framework.TestCase;
import model.util.ReadImage;

import java.io.IOException;

/**
 * Created by Quinn Liu(quinnliu@vt.edu) on 6/15/14.
 */
public class ImageViewerTest extends TestCase {

    //private ImageViewer imageViewer;
    private ReadImage readImage;

    public void setUp() throws IOException {
        this.readImage = new ReadImage();
        int[][] image = this.readImage.readBMPImage("../Array2DTest.bmp");
        Retina retina = new Retina(5, 15);
        // this.imageViewer = new ImageViewer(image, retina); // retina, image
    }

    public void test_viewImageWhileRetinaInsideBoundingBox() {
//        assertEquals(viewOfImageFromMiddleOfBoudningBox, retina.getSeen())
//
//
//        this.imageViewer.updatePositionOfRetina(new position);
//
//        int[][] seenAreaByRetina = this.imageViewer.getSeenAreaByRetina();
    }

    public void test_stupid() {
        assertEquals(1, 3 - 2);
    }
}
