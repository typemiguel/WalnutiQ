package model.MARK_II.movement;

public class EyeMuscles {
    private SaccadingRetina retina;

    // input = ??? neuron activity at top region of hierarhcy???
    // output = saccade Retina based on input
    public EyeMuscles(SaccadingRetina retina) {
	this.retina = retina;
    }

    /**
     * TODO: the retina visionCells[][] need to be updated here or at least what
     * part of the image can be seen needs to be updated here
     *
     * NOTE: the retina should be able to see any part of the entire Image
     * at any size
     */
    public void moveRetina() {

    }
}
