package model.util;

import model.MARK_I.connectTypes.SensorCellsToRegionConnect;
import model.MARK_I.connectTypes.SensorCellsToRegionRectangleConnect;

import model.MARK_I.Region;
import model.MARK_I.SpatialPooler;
import model.MARK_I.VisionCell;

import com.google.gson.Gson;
import java.io.IOException;
import model.LGN;
import model.Retina;

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
	LGN unconnectedLGN = new LGN(this.LGNRegion);

	Retina retine = new Retina(66, 66);

	SensorCellsToRegionConnect retinaToLGN = new SensorCellsToRegionRectangleConnect();
	retinaToLGN.connect(retine.getVisionCells(), this.LGNRegion, 0, 0);
	retine.seeBMPImage("2.bmp");

	SpatialPooler spatialPooler = new SpatialPooler(this.LGNRegion);
	spatialPooler.setLearningState(true);

	for (int i = 0; i < 100; i++) {
	    spatialPooler.performSpatialPoolingOnRegion();
	}

	// Set<ColumnPosition> LGNNeuronActivity = spatialPooler
	// .getActiveColumnPositions();
	// assertEquals(11, LGNNeuronActivity.size());

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

	// uncommenting the following code causes a build error with grade
	// in oracle jdk 7
	// assertEquals("LGN", LGNRegion.getBiologicalName());
	// assertEquals(8, LGNRegion.getXAxisLength());
	// assertEquals(8, LGNRegion.getYAxisLength());
	// assertEquals(10, LGNRegion.getDesiredLocalActivity());
	// assertEquals(5, LGNRegion.getInhibitionRadius());
    }
}
