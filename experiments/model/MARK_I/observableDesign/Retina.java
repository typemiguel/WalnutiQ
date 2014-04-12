package model.MARK_I.observableDesign;

import java.util.Observable;
import model.MARK_I.VisionCell;

public class Retina extends Observable {
    private VisionCell visionCell;

    public Retina() {
	this.visionCell = new VisionCell();
    }

    public VisionCell getVisionCells() {
	return this.visionCell;
    }

    public void seeBMPImage(String blackOrWhite) {
	if (blackOrWhite.equals("black")) {
	    this.visionCell.setActiveState(true);
	} else {
	    this.visionCell.setActiveState(false);
	}
	this.setChanged();
	this.notifyObservers(blackOrWhite);
    }
}
