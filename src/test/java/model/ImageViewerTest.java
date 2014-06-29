package model;

import junit.framework.TestCase;
import model.util.Point3D;
import model.util.RegionConsoleViewer;

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

    public void setUp() throws IOException {
        Retina retina = new Retina(66, 66);
        this.imageViewer = new ImageViewer("2.bmp", retina);
    }

    public void test_saccadeRetinaToNewPosition() throws IOException {
        RegionConsoleViewer.printDoubleIntArray(this.imageViewer.saccadeRetinaToNewPosition(new Point3D(33, 33, 33)));
    }
}
