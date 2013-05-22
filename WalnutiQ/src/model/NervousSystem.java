package model;

import model.MARK_II.VisionCellLayer;

import model.MARK_II.Neocortex;
import model.MARK_II.ConnectionType;
import model.MARK_II.Region;

public class NervousSystem {
    private CentralNervousSystem CNS;
    private PeripheralNervousSystem PNS;

    public NervousSystem(Region rootRegion,
	    ConnectionType neocortexRegionToNeocortexRegion,
	    int numberOfVisionCellsAlongXAxis, int numberOfVisionCellsAlongYAxis) {
	this.CNS = new CentralNervousSystem(rootRegion,
		neocortexRegionToNeocortexRegion, numberOfVisionCellsAlongXAxis,
		numberOfVisionCellsAlongYAxis);
	this.PNS = new PeripheralNervousSystem(numberOfVisionCellsAlongXAxis,
		numberOfVisionCellsAlongYAxis);

	// The LGN is connected to V1 in the neocortex in the brain constructor.
	// The following connects the retina to LGN using using OverlapConnectFunctor.
	VisionCellLayer retina = this.PNS.getSNS().getEye().getRetina();
	LateralGeniculateNucleus LGN = this.CNS.getBrain().getThalamus().getLGN();
    }

    public CentralNervousSystem getCNS() {
	return this.CNS;
    }

    public PeripheralNervousSystem getPNS() {
	return this.PNS;
    }
}
