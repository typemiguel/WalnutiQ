package model;

import model.util.RegionConsoleViewer;
import model.Column;
import model.InputCell;
import model.Region;
import model.Segment;
import model.SpatialPooler;
import model.Synapse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Set;
import java.util.HashSet;
import java.util.List;

// -------------------------------------------------------------------------
/**
 * Tests all of the methods within class SpatialPooler.
 *
 * @author Quinn Liu
 * @version Nov 8, 2012
 */
public class TestSpatialPooler
    extends junit.framework.TestCase
{
    private SpatialPooler spatialPooler;
    private Region        region;
    private Column        column33;


    public void setUp()
    {
        // instantiate 1 Region, 48 Columns, 48 Segments(Proximal),
        // (48 * (30/6) * (40/8) = 1200 Synapses,
        // 1200 InputCells
        this.region = new Region(30, 40, 6, 8, 4.0f, 1, 2);
        // no other methods should initialize any object within this region

        this.spatialPooler = new SpatialPooler(this.region);

        this.column33 = this.region.getColumn(3, 3);
    }


    // ----------------------------------------------------------
    /**
     * Test getRegion and setRegion on a SpatialPooler object.
     */
    public void testGetAndSetRegion()
    {
        this.spatialPooler.setRegion(this.region);
        assertEquals(this.region, this.spatialPooler.getRegion());
        System.out.println(this.spatialPooler);
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     */
    public void testGetActiveColumns()
    {
        this.column33.setActiveState(true);
        this.spatialPooler.addActiveColumn(column33);
        Set<Column> activeColumnsSet = new HashSet<Column>();
        activeColumnsSet.add(this.column33);
        assertEquals(activeColumnsSet, this.spatialPooler.getActiveColumns());
    }


    // ----------------------------------------------------------
    /**
     * Tests getActiveColumn method on a SpatialPooler object.
     */
    public void testGetAndAddActiveColumn()
    {
        // the following line is not neccessary but is written to emphasize
        // only active columns are added to the activeColumns set within
        // a SpaitialPooler object.
        this.column33.setActiveState(true);

        this.spatialPooler.addActiveColumn(this.column33);
        assertEquals(this.column33, this.spatialPooler.getActiveColumn(3, 3));
    }


    // ----------------------------------------------------------
    /**
     * Tests the kthScore method on a region object. This is done by first
     * presetting a region with known overlapScores. Then the kthScore method is
     * called with different desiredLocalActivites and assertions are made to
     * make sure the correct overlapScores are returned.
     */
    public void testKthScoreOfRegion()
    {
        // change overlapScores of columns for testing the correct returning int
        this.region.getColumn(1, 3).setOverlapScore(24);
        this.region.getColumn(2, 3).setOverlapScore(1);
        this.region.getColumn(2, 5).setOverlapScore(26);
        this.region.getColumn(3, 1).setOverlapScore(20);
        this.region.getColumn(3, 4).setOverlapScore(5);
        this.region.getColumn(3, 6).setOverlapScore(30);
        this.region.getColumn(4, 3).setOverlapScore(10);

        this.region.setInhitbitionRadius(3);
        this.column33.updateNeighborColumns();
        List<Column> neighborColumns33 = this.column33.getNeighborColumns();

        Exception occurred = null;
        try
        {
            this.spatialPooler.kthScoreOfColumns(neighborColumns33, 0);
        }
        catch (Exception e)
        {
            occurred = e;
        }
        assertNotNull(occurred);
        assertTrue(occurred instanceof IllegalStateException);
        assertEquals(
            "desiredLocalActivity cannot be <= 0",
            occurred.getMessage());

        assertEquals(
            30,
            this.spatialPooler.kthScoreOfColumns(neighborColumns33, 1));
        assertEquals(
            26,
            this.spatialPooler.kthScoreOfColumns(neighborColumns33, 2));
        assertEquals(
            24,
            this.spatialPooler.kthScoreOfColumns(neighborColumns33, 3));
        assertEquals(
            20,
            this.spatialPooler.kthScoreOfColumns(neighborColumns33, 4));
        assertEquals(
            10,
            this.spatialPooler.kthScoreOfColumns(neighborColumns33, 5));
        assertEquals(
            5,
            this.spatialPooler.kthScoreOfColumns(neighborColumns33, 6));
        assertEquals(
            1,
            this.spatialPooler.kthScoreOfColumns(neighborColumns33, 7));
    }


    // ----------------------------------------------------------
    /**
     * Tests the averageReceptiveFieldSize method on a Region object.
     */
    public void testAverageReceptiveFieldSizeOfRegion()
    {
        // column00's receptiveFieldSize is > then column33's receptiveFieldSize
        assertEquals(
            0.634f,
            this.spatialPooler.averageReceptiveFieldSizeOfRegion(),
            0.001f);
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     *
     * @throws IOException
     */
    public void testNextTimeStepForRegion()
        throws IOException
    {
        // 1) read a picture and store in byte[][]
        byte[][] inputDataBytes = this.getDoubleByteArrayOfFile("circle1.bmp", this.region);

        // 2) update every Abstract InputCell in this.region with new inputData
        this.spatialPooler.nextTimeStep(inputDataBytes);

        // assert InputCell states of each Synapse have changed correctly
        Segment proximalSegment00 =
            this.region.getColumn(0, 0).getProximalSegment();
        InputCell inputCell00 = proximalSegment00.getInputCell(0, 0);
        assertFalse(proximalSegment00.getSynapse(inputCell00).getActiveState());

        InputCell inputCell02 = proximalSegment00.getInputCell(0, 2);
        assertTrue(proximalSegment00.getSynapse(inputCell02).getActiveState());

        Segment proximalSegment11 =
            this.region.getColumn(1, 1).getProximalSegment();
        InputCell inputCell86 = proximalSegment11.getInputCell(8, 6);
        assertTrue(proximalSegment11.getSynapse(inputCell86).getActiveState());

        InputCell inputCell87 = proximalSegment11.getInputCell(8, 7);
        assertFalse(proximalSegment11.getSynapse(inputCell87).getActiveState());

        // 3) update every Abstract InputCell in this.region with new empty
        // inputData for testing field previousActiveState
        inputDataBytes[0][0] = 0;
        inputDataBytes[0][2] = 0;
        inputDataBytes[8][6] = 0;
        inputDataBytes[8][7] = 0;
        this.spatialPooler.nextTimeStep(inputDataBytes);

        // assert InputCell states of each Synapse have changed correctly
        assertFalse(proximalSegment00.getSynapse(inputCell00)
            .getPreviousActiveState());
        assertFalse(proximalSegment00.getSynapse(inputCell00).getActiveState());

        assertTrue(proximalSegment00.getSynapse(inputCell02)
            .getPreviousActiveState());
        assertFalse(proximalSegment00.getSynapse(inputCell02).getActiveState());

        assertTrue(proximalSegment11.getSynapse(inputCell86)
            .getPreviousActiveState());
        assertFalse(proximalSegment11.getSynapse(inputCell86).getActiveState());

        assertFalse(proximalSegment11.getSynapse(inputCell87)
            .getPreviousActiveState());
        assertFalse(proximalSegment11.getSynapse(inputCell87).getActiveState());
    }


    // ----------------------------------------------------------
    /**
     * Tests the computeOverlapScore() on a SpatialPooler object.
     *
     * @throws IOException
     */
    public void testComputeAllOverlapScoresOfRegion()
        throws IOException
    {
        // 1) read a picture and store in byte[][]
        byte[][] inputDataBytes = this.getDoubleByteArrayOfFile("circle1.bmp", this.region);

        // this.region.setMinimumOverlapScore(18.0f);
        // 2) update every Abstract InputCell in this.region correctly
        this.spatialPooler.nextTimeStep(inputDataBytes);
        this.spatialPooler.computeAllColumnOverlapScoresOfRegion();

        int[][] overlapScores = RegionConsoleViewer.getColumnOverlapScoresIntArray(this.spatialPooler);
        System.out.println("\n------testComputeOverlapScoresOfRegion()-------");
        System.out.println("\n----------Column overlapScores below-----------");
        RegionConsoleViewer.printDoubleIntArray(overlapScores);
        System.out.println("\n----------Column overlapScores above-----------");
    }


    // ----------------------------------------------------------
    /**
     * Tests the computeActiveColumns() on a SpatialPooler object.
     *
     * @throws IOException
     */
    public void testComputeActiveColumnsOfRegion()
        throws IOException
    {
        this.region.getColumn(0, 0).setOverlapScore(10);
        this.region.getColumn(0, 1).setOverlapScore(11);
        this.region.getColumn(0, 2).setOverlapScore(12);
        this.region.getColumn(0, 3).setOverlapScore(13);
        this.region.getColumn(0, 4).setOverlapScore(14);
        this.region.getColumn(1, 0).setOverlapScore(15);
        this.region.getColumn(1, 1).setOverlapScore(2);
        this.region.getColumn(1, 2).setOverlapScore(3);
        this.region.getColumn(1, 3).setOverlapScore(4);
        this.region.getColumn(1, 4).setOverlapScore(16);
        this.region.getColumn(2, 0).setOverlapScore(17);
        this.region.getColumn(2, 1).setOverlapScore(5);
        this.region.getColumn(2, 2).setOverlapScore(1);
        this.region.getColumn(2, 3).setOverlapScore(6);
        this.region.getColumn(2, 4).setOverlapScore(18);
        this.region.getColumn(3, 0).setOverlapScore(19);
        this.region.getColumn(3, 1).setOverlapScore(7);
        this.region.getColumn(3, 2).setOverlapScore(8);
        this.region.getColumn(3, 3).setOverlapScore(9);
        this.region.getColumn(3, 4).setOverlapScore(20);
        this.region.getColumn(4, 0).setOverlapScore(21);
        this.region.getColumn(4, 1).setOverlapScore(22);
        this.region.getColumn(4, 2).setOverlapScore(23);
        this.region.getColumn(4, 3).setOverlapScore(24);
        this.region.getColumn(4, 4).setOverlapScore(25);
        // all other column overlapScores are 0

        this.region.setInhitbitionRadius(2);

        this.spatialPooler.computeActiveColumnsOfRegion();

        char[][] columnActiveStatesCharacters =
            RegionConsoleViewer.getColumnActiveStatesCharArray(this.spatialPooler);
        System.out.println("\n------testComputeActiveColumnsOfRegion()-------");
        System.out.println("\n----------Column activeStates below------------");
        RegionConsoleViewer.printDoubleCharArray(columnActiveStatesCharacters);
        System.out.println("\n----------Column activeStates above------------");
    }


    // ----------------------------------------------------------
    /**
     * Tests the learn() on a SpatialPooler object.
     */
    public void testRegionLearnOneTimeStep()
    {
        Column column11 = this.region.getColumn(1, 1);
        Segment proximalSegment11 = column11.getProximalSegment();
        InputCell inputCell55 = proximalSegment11.getInputCell(5, 5);
        // An "and" truth table of column.updatePermanences()
        // X-Axis: Column activeState
        // Y-Axis: Synapse/InputCell activeState
        // -----true false
        // true .315 .295
        // false.295 .295
        column11.setActiveState(true);
        inputCell55.setActiveState(true);
        Synapse synapse55 = proximalSegment11.getSynapse(inputCell55);

        assertEquals(1.0f, this.region.getInhitbitionRadius(), 0.01f);
        this.spatialPooler.getRegion().setSpatialLearning(true);
        this.spatialPooler.regionLearnOneTimeStep();

        // test column.updatePermanences() call
        assertEquals(0.315f, synapse55.getPermanenceValue(), 0.01f);

        // test column.performBoosting() call
        assertEquals(0.995f, this.column33.getBoostValue(), 0.001f);
        this.column33.getNeighborColumns().get(0).setActiveDutyCycle(0.9f);
        this.spatialPooler.regionLearnOneTimeStep();
        assertEquals(0.990f, this.column33.getBoostValue(), 0.001f);

        // test this.getRegion().setInhibitionRadius(...) call
        assertEquals(0.634f, this.region.getInhitbitionRadius(), 0.01f);
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     *
     * @throws IOException
     */
    public void testPerformSpatialPoolingOnRegion()
        throws IOException
    {
        System.out.println("\n-----testPerformSpatialPoolingOnRegion()-------");
        byte[][] inputDataBytes = this.getDoubleByteArrayOfFile("circle1.bmp", this.region);
        System.out.println("------------circle1.bmp below------------------");
        RegionConsoleViewer.printDoubleByteArray(inputDataBytes);
        System.out.println("\n------------circle1.bmp above------------------");

        this.region.setSpatialLearning(true);
        this.spatialPooler.nextTimeStep(inputDataBytes);
        this.spatialPooler.performSpatialPoolingOnRegion();

        System.out.println("\n----------Columns with activeState------------");
        System.out.println(this.spatialPooler.getActiveColumns().toString());
    }

 // ----------------------------------------------------------
    /**
     * Returns the converted byte 2-D array of a .bmp file.
     *
     * @param fileName
     *            The name of the file without ".bmp"
     * @param region1
     * @return An inputed .bmp file as a byte 2-D array.
     * @throws IOException
     */
    private byte[][] getDoubleByteArrayOfFile(String fileName, Region region1)
        throws IOException
    {
        BufferedImage image = ImageIO.read(getClass().getResource(fileName));
        byte[][] alphaInputData =
            new byte[region1.getInputXAxisLength()][region1
                .getInputYAxisLength()];
        for (int x = 0; x < alphaInputData.length; x++)
        {
            for (int y = 0; y < alphaInputData[x].length; y++)
            {
                int color = image.getRGB(x, y);
                alphaInputData[x][y] = (byte)(color >> 23);
            }
        }
        return alphaInputData;
    }
}
