package model;

import model.MARK_II.VisionCellLayer;

public class Retina {
    private VisionCellLayer visionCellLayer;

    public Retina(VisionCellLayer visionCellLayer) {
	this.visionCellLayer = visionCellLayer;
    }

    public VisionCellLayer getRetina() {
	return this.visionCellLayer;
    }

    public void seeImage(String JPEGImageLocation) {
	// updates states of vision cells
	// TODO: implement with difference of guassian filter
    }
}
