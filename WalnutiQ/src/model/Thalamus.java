package model;

public class Thalamus {
    private LateralGeniculateNucleus LGN;

    public Thalamus(int numberOfVisionCellsAlongXAxis,
	    int numberOfVisionCellsAlongYAxis) {
	this.LGN = new LateralGeniculateNucleus(numberOfVisionCellsAlongXAxis,
		numberOfVisionCellsAlongYAxis);
    }

    public LateralGeniculateNucleus getLGN() {
	return this.LGN;
    }
}
