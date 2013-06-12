package model;

import model.theory.MemoryClassifier;

import model.theory.Memory;

import java.util.Set;

import model.MARK_II.Column;

import model.theory.Idea;

import model.MARK_II.SpatialPooler;

import java.io.IOException;

import model.MARK_II.Neocortex;
import model.MARK_II.Region;
import model.MARK_II.VisionCell;

import model.Retina;

import model.MARK_II.ConnectTypes.SensorCellsToRegionConnect;

import model.MARK_II.ConnectTypes.SensorCellsToRegionRectangleConnect;

import model.NervousSystem;
import model.LateralGeniculateNucleus;
import model.MARK_II.ConnectTypes.RegionToRegionRectangleConnect;
import model.MARK_II.ConnectTypes.RegionToRegionConnect;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version MARK II | June 7, 2013
 */
public class HowToUseMARK_II extends junit.framework.TestCase {

    public void setUp() throws IOException {
	System.out.println("Hello World!");

	NervousSystem nervousSystem = this.constructConnectedNervousSystem();


	Retina retina = nervousSystem.getPNS().getSNS().getRetina();

	Region LGNStructure = nervousSystem.getCNS().getBrain()
		.getThalamus().getLGN().getRegion();

	//Region V1 = nervousSystem.getCNS().getBrain().getCerebrum()
	//	.getCerebralCortex().getNeocortex().getCurrentRegion();

	// -------------train NervousSystem update Memory----------------
	retina.seeBMPImage("2.bmp");

	SpatialPooler spatialPooler = new SpatialPooler(LGNStructure);
	Set<Column> LGNNeuronActivity = spatialPooler.performSpatialPoolingOnRegion();

	Idea twoIdea = new Idea("two");
	twoIdea.unionColumns(LGNNeuronActivity);

	Memory digitsMemory = new Memory();
	digitsMemory.addIdea(twoIdea);

	// TODO: train LGNStructure on many more different images of 2's


	// ------------test Memory of NervousSystem--------------------
	MemoryClassifier digitsSVM = new MemoryClassifier(digitsMemory);

	// retina.seeBMPImage("new2.bmp");
	// digitsSVM.updateIdeas(spatialPooler.performSpatialPoolingOnRegion());
	// digitsSVM.toString();
    }

    private NervousSystem constructConnectedNervousSystem() {
	// construct Neocortex with just V1
	Region rootRegionOfNeocortex = new Region("V1", 4, 4, 4, 50, 3);
	RegionToRegionConnect neocortexConnectType = new RegionToRegionRectangleConnect();
	Neocortex unconnectedNeocortex = new Neocortex(rootRegionOfNeocortex,
		neocortexConnectType);

	// construct LGN
	Region LGNRegion = new Region("LGN", 8, 8, 1, 50, 3);
	LateralGeniculateNucleus unconnectedLGN = new LateralGeniculateNucleus(
		LGNRegion);

	// construct Retina
	VisionCell[][] visionCells = new VisionCell[65][65];
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
