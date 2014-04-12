package model.MARK_I.vision;

import model.MARK_I.connectTypes.RegionToRegionConnect;
import model.MARK_I.connectTypes.RegionToRegionRectangleConnect;
import model.MARK_I.connectTypes.SensorCellsToRegionConnect;
import model.MARK_I.connectTypes.SensorCellsToRegionRectangleConnect;

import model.MARK_I.ColumnPosition;
import model.MARK_I.Neocortex;
import model.MARK_I.Region;
import model.MARK_I.SpatialPooler;

import model.util.JsonFileInputOutput;
import com.google.gson.Gson;
import java.util.Set;
import java.io.IOException;
import model.Retina;
import model.NervousSystem;
import model.LGN;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version April 12, 2014
 */
public class HowMARK_I_FitsInToBrainAnatomy extends junit.framework.TestCase {
    private NervousSystem partialNervousSystem;

    /**
     * For saving the Java NervousSystem object as a JSON file later on.
     */
    private Gson gson;

    public void setUp() throws IOException {
	this.partialNervousSystem = this.constructConnectedNervousSystem();

	this.gson = new Gson();
    }

    private NervousSystem constructConnectedNervousSystem() {
	Neocortex unconnectedNeocortex = new Neocortex(new Region("V1", 4, 4,
		4, 50, 3), new RegionToRegionRectangleConnect());

	LGN unconnectedLGN = new LGN(new Region("LGN", 8, 8, 1, 50, 3));

	Retina unconnectedRetina = new Retina(65, 65);

	NervousSystem nervousSystem = new NervousSystem(unconnectedNeocortex,
		unconnectedLGN, unconnectedRetina);

	// connect Retina to LGN
	Retina retina = nervousSystem.getPNS().getSNS().getRetine();
	LGN LGN = nervousSystem.getCNS().getBrain().getThalamus().getLGN();
	SensorCellsToRegionConnect opticNerve = new SensorCellsToRegionRectangleConnect();
	opticNerve.connect(retina.getVisionCells(), LGN.getRegion(), 0, 0);

	// connect LGN to very small part of V1 Region of Neocortex
	Neocortex neocortex = nervousSystem.getCNS().getBrain().getCerebrum()
		.getCerebralCortex().getNeocortex();
	RegionToRegionConnect regionToRegionConnect = new RegionToRegionRectangleConnect();
	regionToRegionConnect.connect(LGN.getRegion(),
		neocortex.getCurrentRegion(), 0, 0);

	return nervousSystem;
    }

    public void testHowToRunSpatialPoolingOnNervousSystem() throws IOException {
	Retina retina = partialNervousSystem.getPNS().getSNS().getRetine();

	Region LGNRegion = partialNervousSystem.getCNS().getBrain()
		.getThalamus().getLGN().getRegion();

	// Region V1 = nervousSystem.getCNS().getBrain().getCerebrum()
	// .getCerebralCortex().getNeocortex().getCurrentRegion();

	retina.seeBMPImage("2.bmp");

	SpatialPooler spatialPooler = new SpatialPooler(LGNRegion);
	spatialPooler.setLearningState(true);
	spatialPooler.performSpatialPoolingOnRegion();
	Set<ColumnPosition> LGNNeuronActivity = spatialPooler
		.getActiveColumnPositions();

	assertEquals(11, LGNNeuronActivity.size());

	// save LGNRegion object in JSON format
	String partialNervousSystemObject = this.gson
		.toJson(this.partialNervousSystem);
	JsonFileInputOutput
		.saveObjectToTextFile(partialNervousSystemObject,
			"./experiments/model/MARK_I/vision/PartialNervousSystemObject.json");
    }
}
