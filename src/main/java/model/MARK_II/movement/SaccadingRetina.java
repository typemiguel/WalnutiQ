package model.MARK_II.movement;

import model.Retina;

/**
 *  Put all logic for updating SaccadingRetina elsewhere. In reality
 *  a retina does not update itself.
 */
public class SaccadingRetina extends Retina {
    private ViewPosition viewPosition;

    public SaccadingRetina(int numberOfVisionCellsAlongYAxis,
	    int numberOfVisionCellsAlongXAxis, ViewPosition viewPosition) {
	super(numberOfVisionCellsAlongYAxis, numberOfVisionCellsAlongXAxis);

	this.viewPosition = viewPosition;
    }

    public ViewPosition getViewPosition() {
        return viewPosition;
    }

    public void setViewPosition(ViewPosition viewPosition) {
        this.viewPosition = viewPosition;
    }
}
