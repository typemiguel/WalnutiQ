package model.util;

import model.MARK_I.connectTypes.SensorCellsToRegionConnect;
import model.MARK_I.connectTypes.SensorCellsToRegionRectangleConnect;

import model.MARK_I.Region;
import model.MARK_I.SpatialPooler;
import model.MARK_I.VisionCell;

import com.google.gson.Gson;
import java.io.IOException;
import model.LateralGeniculateNucleus;
import model.Retine;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version July 29, 2013
 */
public class SynapsePermanencesViewerTest extends junit.framework.TestCase {
    SynapsePermanencesViewer spv;
    private Region LGNRegion;

    public void setUp() throws IOException {

    }

    public void test_saveRegionToBeOpenedInSynapsePermanencesViewer()
	    throws IOException {
	// construct LGN
	this.LGNRegion = new Region("LGN", 8, 8, 1, 50, 10);
	LateralGeniculateNucleus unconnectedLGN = new LateralGeniculateNucleus(
		this.LGNRegion);

	// construct Retina
	VisionCell[][] visionCells = new VisionCell[64][64];
	for (int x = 0; x < visionCells.length; x++) {
	    for (int y = 0; y < visionCells[0].length; y++) {
		visionCells[x][y] = new VisionCell();
	    }
	}
	Retine retina = new Retine(visionCells);

	SensorCellsToRegionConnect retinaToLGN = new SensorCellsToRegionRectangleConnect();
	retinaToLGN.connect(visionCells, this.LGNRegion, 0, 0);
	retina.seeBMPImage("2.bmp");

	SpatialPooler spatialPooler = new SpatialPooler(this.LGNRegion);
	spatialPooler.setLearningState(true);

	for (int i = 0; i < 100; i++) {
	    spatialPooler.performSpatialPoolingOnRegion();
	}

	//Set<ColumnPosition> LGNNeuronActivity = spatialPooler
	//	.getActiveColumnPositions();
	//assertEquals(11, LGNNeuronActivity.size());

	Gson gson = new Gson();
	String regionObject = gson.toJson(this.LGNRegion);
	JsonFileInputOutput.saveObjectToTextFile(regionObject,
		"./tests/model/util/test_saveRegionObject.txt");
    }

    public void test_openRegionObject() throws IOException {
	String regionAsString = JsonFileInputOutput
		.openObjectInTextFile("./tests/model/util/test_saveRegionObject.txt");
	Gson gson = new Gson();
	Region LGNRegion = gson.fromJson(regionAsString, Region.class);
	// TODO: assert equals with output stream
	//System.out.println(LGNRegion.toString());
    }
}
