package model;

import model.util.BoundingBox;
import model.util.ReadImage;
import model.util.Point3D;
import java.io.IOException;

/**
 * Created by Quinn Liu(quinnliu@vt.edu) on 6/28/14.
 */
public class ImageViewer {
    private int[][] entireImage;
    private Retina retina;
    private BoundingBox boxRetinaIsStuckIn;
    private Point3D retinaPositionWithinBox;

    public ImageViewer(String BMPFileName, Retina retina)
            throws IOException {
        ReadImage readImage = new ReadImage();
        this.entireImage = readImage.readBMPImage(BMPFileName);
        this.retina = retina;

        int numberOfRowPixels = this.entireImage.length;
        int numberOfColumnPixels = this.entireImage[0].length;
        double longerEntireImageDimension = Math.max(numberOfRowPixels, numberOfColumnPixels);
        double distanceZAwayFromImageToSeeEntireImage = longerEntireImageDimension / 2;

        int height = numberOfRowPixels; // Y-Axis
        int width = numberOfColumnPixels; // X-Axis
        int depth = (int) distanceZAwayFromImageToSeeEntireImage; // Z-Axis
        this.boxRetinaIsStuckIn = new BoundingBox(width, height, depth);

        this.retinaPositionWithinBox = new Point3D(width/2, height/2, depth);
    }
}
