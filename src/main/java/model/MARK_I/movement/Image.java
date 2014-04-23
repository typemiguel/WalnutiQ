package main.java.model.MARK_I.movement;

public class Image {
    private int[][] image;

    public Image(int[][] image) {
	this.image = image;
    }

    /**
     * Depending on where the retina is and how close it is to the image a
     * different image needs to be returned
     */
    public int[][] seenPartOfImage(
	    int informationAboutRetinaLocationAndOrientation) {
	// TODO: manipulate this.image based on
	// informationAboutRetinaLocationAndOrientation

	int[][] manipulatedImage = this.image;

	return manipulatedImage;
    }
}
