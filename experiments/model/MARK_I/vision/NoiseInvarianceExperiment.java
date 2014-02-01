package model.MARK_I.vision;

/**
 * -------------------------------Purpose---------------------------------------
 * To show the spatial pooling learning algorithm produces similar results even
 * after adding noise to the data.
 *
 * ------------------------------Experiment-------------------------------------
 * Run the spatial pooling algorithm on a Region of 2 different bitmap images.
 * The 2 images are both of the same circle but one of the images has some
 * random noise added on.
 *
 * ------------------------------Conclusion-------------------------------------
 * The spatial pooling algoithm does simple local computations on the image to
 * remove noise very efficiently up to a specific threshold that can vary
 * between locations in the input image.
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Jan 29, 2014
 */
public class NoiseInvarianceExperiment {
    // TODO: reproduce results found here http://www.walnutiq.com/#!poster/c16gs
    // TODO: use images in folder images/model/2.bmp and
    // images/model/2_with_noise.bmp
}
