package model;

import model.MARK_II.VisionCellLayer;

import model.MARK_II.Neocortex;
import model.MARK_II.Region;

public class NervousSystem {
    private CentralNervousSystem CNS;
    private PeripheralNervousSystem PNS;

    public NervousSystem(Neocortex neocortex, LateralGeniculateNucleus LGN, Retina retina) {
	this.CNS = new CentralNervousSystem(neocortex, LGN);
	this.PNS = new PeripheralNervousSystem(retina);
    }

    public CentralNervousSystem getCNS() {
	return this.CNS;
    }

    public PeripheralNervousSystem getPNS() {
	return this.PNS;
    }
}
