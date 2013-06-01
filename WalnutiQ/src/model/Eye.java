package model;

import model.MARK_II.VisionCellLayer;

public class Eye {
    private VisionCellLayer retina;

    public Eye(VisionCellLayer retina) {
	this.retina = retina;
    }

    public VisionCellLayer getRetina() {
	return this.retina;
    }

    public void seeImage(String JPEGImageLocation) {
	// updates states of vision cells
    }
}
