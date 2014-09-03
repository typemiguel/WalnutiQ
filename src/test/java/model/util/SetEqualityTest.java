package model.util;

import junit.framework.TestCase;
import model.MARK_II.ColumnPosition;
import model.MARK_II.Region;
import model.MARK_II.SpatialPooler;
import model.MARK_II.connectTypes.AbstractSensorCellsToRegionConnect;
import model.MARK_II.connectTypes.SensorCellsToRegionRectangleConnect;
import model.Retina;

import java.io.IOException;
import java.util.Set;
import java.util.HashSet;

/**
 * @author Quinn Liu (quinnliu@vt.edu)
 * @author Nathan Waggoner (nwagg14@vt.edu)
 * @version Sept 3, 2014
 */
public class SetEqualityTest extends TestCase {

    public void setUp() {
    }

    public void test_getActiveColumnPositionsAsString() throws IOException {
        Retina retina = new Retina(66, 66);

        Region region = new Region("region", 8, 8, 4, 50, 1);

        AbstractSensorCellsToRegionConnect connectType2 = new SensorCellsToRegionRectangleConnect();
        connectType2.connect(retina.getVisionCells(), region, 2, 2);

        SpatialPooler spatialPooler = new SpatialPooler(region);
        spatialPooler.setLearningState(true);

        // declare sets to test on
        Set<ColumnPosition> emptySet = new HashSet<ColumnPosition>();
        Set<ColumnPosition> filledSet = new HashSet<ColumnPosition>();

        // create columns that should be in the filledSet
        ColumnPosition cp1 = new ColumnPosition(6, 5);
        ColumnPosition cp2 = new ColumnPosition(6, 2);
        ColumnPosition cp3 = new ColumnPosition(1, 2);
        ColumnPosition cp4 = new ColumnPosition(2, 5);
        ColumnPosition cp5 = new ColumnPosition(4, 4);

        // add the column positions to the filledSet
        filledSet.add(cp1);
        filledSet.add(cp2);
        filledSet.add(cp3);
        filledSet.add(cp4);
        filledSet.add(cp5);

        assertEquals(emptySet, spatialPooler.getActiveColumnPositions());

        // view the image, which will activate Columns in the spatialPooler
        retina.seeBMPImage("2.bmp");
        spatialPooler.performPooling();

        // filledSet contains ((6, 5), (6, 2), (1, 2), (2, 5), (4, 4))
        assertEquals(filledSet, spatialPooler.getActiveColumnPositions());
    }
}
