package model.util;

import junit.framework.TestCase;
import model.MARK_II.Region;
import model.MARK_II.SpatialPooler;
import model.MARK_II.connectTypes.AbstractSensorCellsToRegionConnect;
import model.MARK_II.connectTypes.SensorCellsToRegionRectangleConnect;
import model.Retina;

import java.io.IOException;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @author Kyle
 * @version August 1, 2014
 */
public class FormatterTest extends TestCase {

    public void setUp() {
    }

    public void test_getActiveColumnPositionsAsString() throws IOException {
        Retina retina = new Retina(66, 66);

        Region region = new Region("region", 8, 8, 4, 50, 1);

        AbstractSensorCellsToRegionConnect connectType2 = new SensorCellsToRegionRectangleConnect();
        connectType2.connect(retina.getVisionCells(), region, 2, 2);

        SpatialPooler spatialPooler = new SpatialPooler(region);
        spatialPooler.setLearningState(true);

        assertEquals("()",
                Formatter.format(spatialPooler.getActiveColumnPositions()));

        retina.seeBMPImage("2.bmp");
        spatialPooler.performPooling();

        assertEquals("((6, 5), (6, 2), (1, 2), (2, 5), (4, 4))",
                Formatter.format(spatialPooler.getActiveColumnPositions()));
    }
}
