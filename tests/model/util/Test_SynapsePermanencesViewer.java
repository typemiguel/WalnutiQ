package model.util;

import model.MARK_II.Column;

import com.google.gson.Gson;

import java.util.Set;
import model.MARK_II.ColumnPosition;
import model.MARK_II.SpatialPooler;
import java.io.IOException;
import model.MARK_II.ConnectTypes.SensorCellsToRegionConnect;
import model.MARK_II.ConnectTypes.SensorCellsToRegionRectangleConnect;
import model.LateralGeniculateNucleus;
import model.Retina;
import model.MARK_II.Region;
import model.MARK_II.VisionCell;

public class Test_SynapsePermanencesViewer extends junit.framework.TestCase {
    SynapsePermanencesViewer spv;
    private Region LGNRegion;

    public void setUp() throws IOException {

    }

    public void test_saveRegionToBeOpenedInSynapsePermanencesViewer()
	    throws IOException {
	// construct LGN
	this.LGNRegion = new Region("LGN", 8, 8, 1, 50, 2);
	LateralGeniculateNucleus unconnectedLGN = new LateralGeniculateNucleus(
		this.LGNRegion);

	// construct Retina
	VisionCell[][] visionCells = new VisionCell[64][64];
	for (int x = 0; x < visionCells.length; x++) {
	    for (int y = 0; y < visionCells[0].length; y++) {
		visionCells[x][y] = new VisionCell();
	    }
	}
	Retina retina = new Retina(visionCells);

	SensorCellsToRegionConnect retinaToLGN = new SensorCellsToRegionRectangleConnect();
	retinaToLGN.connect(visionCells, this.LGNRegion, 0, 0);
	retina.seeBMPImage("2.bmp");

	SpatialPooler spatialPooler = new SpatialPooler(this.LGNRegion);
	spatialPooler.setLearningState(true);
	spatialPooler.performSpatialPoolingOnRegion();
	Set<ColumnPosition> LGNNeuronActivity = spatialPooler
		.getActiveColumnPositions();

	assertEquals(11, LGNNeuronActivity.size());

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
	System.out.println(LGNRegion.toString());
    }
}
