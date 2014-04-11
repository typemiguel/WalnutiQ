package model.MARK_I.observableDesign;

public class SimpleRetina {
    private int visionCells;

    public SimpleRetina() {
	this.visionCells = -1;
    }

    public int getVisionCells() {
	return this.visionCells;
    }

    public void seeNewNumber(int number) {
	this.visionCells = number;
    }
}
