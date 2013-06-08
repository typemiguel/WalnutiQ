package model.MARK_II;

import model.MARK_II.ConnectTypes.SensorCellsToRegionConnect;

import model.MARK_II.ConnectTypes.SensorCellsToRegionRectangleConnect;

import model.NervousSystem;
import model.Retina;
import model.LateralGeniculateNucleus;
import model.MARK_II.ConnectTypes.RegionToRegionRectangleConnect;
import model.MARK_II.ConnectTypes.RegionToRegionConnect;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK II | June 7, 2013
 */
public class HowToUseMARK_II extends junit.framework.TestCase {

    public void setUp() {
	System.out.println("Hello World!");

	NervousSystem nervousSystem = this.constructConnectedNervousSystem();

	// train NervousSystem
    }

    private NervousSystem constructConnectedNervousSystem() {
	// construct Neocortex with just V1
	Region rootRegionOfNeocortex = new Region("V1", 8, 8, 4, 50, 3);
	RegionToRegionConnect neocortexConnectType = new RegionToRegionRectangleConnect();
	Neocortex unconnectedNeocortex = new Neocortex(rootRegionOfNeocortex,
		neocortexConnectType);

	// construct LGN
	Region LGNRegion = new Region("LGN", 8, 8, 1, 50, 3);
	LateralGeniculateNucleus unconnectedLGN = new LateralGeniculateNucleus(
		LGNRegion);

	// construct Retina
	VisionCell[][] visionCells = new VisionCell[66][66];
	Retina unconnectedRetina = new Retina(visionCells);

	// construct 1 object of NervousSystem to encapsulate all classes in
	// MARK II
	NervousSystem nervousSystem = new NervousSystem(unconnectedNeocortex,
		unconnectedLGN, unconnectedRetina);

	// connect Retina to LGN
	Retina retina = nervousSystem.getPNS().getSNS().getRetina();

	LateralGeniculateNucleus LGN = nervousSystem.getCNS().getBrain()
		.getThalamus().getLGN();

	SensorCellsToRegionConnect retinaToLGN = new SensorCellsToRegionRectangleConnect();
	retinaToLGN.connect(retina.getVisionCells(), LGN.getRegion(), 0, 0);

	// connect LGN to V1 Region of Neocortex
	Neocortex neocortex = nervousSystem.getCNS().getBrain().getCerebrum()
		.getCerebralCortex().getNeocortex();
	RegionToRegionConnect LGNToV1 = new RegionToRegionRectangleConnect();
	LGNToV1.connect(LGN.getRegion(), neocortex.getCurrentRegion(), 0, 0);

	return nervousSystem;
    }
}
