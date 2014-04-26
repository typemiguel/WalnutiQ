package model.util;

import model.MARK_II.Column;
import model.MARK_II.Region;
import model.MARK_II.SpatialPooler;

import com.google.gson.Gson;
import junit.framework.TestCase;
import model.MARK_I.connectTypes.AbstractSensorCellsToRegionConnect;
import model.MARK_I.connectTypes.SensorCellsToRegionRectangleConnect;
import model.Retina;

import java.io.IOException;
import java.util.Set;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version Feb 1, 2014
 */
public class JsonFileInputOutputTest extends TestCase {
    private Gson gson;

    public void setUp() {
	this.gson = new Gson();
    }

    public void test_saveRegionObject() throws IOException {
	Region LGNRegion = new Region("LGN", 8, 8, 1, 50, 3);

	Retina retina = new Retina(66, 66);

	AbstractSensorCellsToRegionConnect retinaToLGN = new SensorCellsToRegionRectangleConnect();
	retinaToLGN.connect(retina.getVisionCells(), LGNRegion, 0, 0);

	// run spatial pooling on a image
	SpatialPooler spatialPooler = new SpatialPooler(LGNRegion);
	spatialPooler.setLearningState(true);

	retina.seeBMPImage("2.bmp");
	Set<Column> LGNNeuronActivity = spatialPooler
		.performSpatialPoolingOnRegion();

	assertEquals(11, LGNNeuronActivity.size());

	Gson gson2 = new Gson();
	Region trainedLGNRegion = spatialPooler.getRegion();
	String regionObject = gson2.toJson(trainedLGNRegion);

	JsonFileInputOutput.saveObjectToTextFile(regionObject,
		"./src/test/java/model/util/test_saveRegionObject.txt");
    }

    public void test_openRegionObject() throws IOException {
	String regionAsString = JsonFileInputOutput
		.openObjectInTextFile("./src/test/java/model/util/test_saveRegionObject.txt");

	Gson gson2 = new Gson();
	Region trainedLGNRegion = gson2.fromJson(regionAsString, Region.class);
	assertEquals("LGN", trainedLGNRegion.getBiologicalName());
    }
}
